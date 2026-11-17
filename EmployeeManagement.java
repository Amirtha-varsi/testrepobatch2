import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A simple console application for managing employee records.
 * Employee data is kept in memory and is cleared when the program exits.
 */
public class EmployeeManagement {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final List<Employee> EMPLOYEES = new ArrayList<>();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Employee Management ===");
            System.out.println("1. Add employee");
            System.out.println("2. Display all employees");
            System.out.println("3. Search employee by ID");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            String choice = SCANNER.nextLine().trim();
            switch (choice) {
                case "1":
                    addEmployee();
                    break;
                case "2":
                    displayEmployees();
                    break;
                case "3":
                    searchEmployee();
                    break;
                case "4":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1, 2, 3, or 4.");
            }
        }
        SCANNER.close();
    }

    private static void addEmployee() {
        System.out.println("\n--- Add Employee ---");
        String id = readNonEmpty("Employee ID: ");
        if (findById(id) != null) {
            System.out.println("An employee with that ID already exists.");
            return;
        }

        String name = readNonEmpty("Name: ");
        String department = readNonEmpty("Department: ");
        double salary = readSalary();

        EMPLOYEES.add(new Employee(id, name, department, salary));
        System.out.println("Employee added successfully.");
    }

    private static void displayEmployees() {
        System.out.println("\n--- Employee List ---");
        if (EMPLOYEES.isEmpty()) {
            System.out.println("No employees have been added yet.");
            return;
        }

        System.out.printf("%-15s %-25s %-20s %12s%n", "Employee ID", "Name", "Department", "Salary");
        System.out.println("--------------------------------------------------------------------------");
        for (Employee employee : EMPLOYEES) {
            printEmployee(employee);
        }
    }

    private static void searchEmployee() {
        System.out.print("Enter employee ID to search: ");
        String id = SCANNER.nextLine().trim();
        Employee employee = findById(id);
        if (employee == null) {
            System.out.println("No employee found with ID " + id + ".");
            return;
        }

        System.out.println("\n--- Employee Details ---");
        System.out.printf("%-15s %-25s %-20s %12s%n", "Employee ID", "Name", "Department", "Salary");
        System.out.println("--------------------------------------------------------------------------");
        printEmployee(employee);
    }

    private static Employee findById(String id) {
        for (Employee employee : EMPLOYEES) {
            if (employee.getId().equalsIgnoreCase(id)) {
                return employee;
            }
        }
        return null;
    }

    private static String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = SCANNER.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("This field cannot be empty.");
        }
    }

    private static double readSalary() {
        while (true) {
            System.out.print("Salary: ");
            String input = SCANNER.nextLine().trim();
            try {
                double salary = Double.parseDouble(input);
                if (Double.isFinite(salary) && salary >= 0) {
                    return salary;
                }
            } catch (NumberFormatException ignored) {
                // Show the same helpful message for malformed and out-of-range values.
            }
            System.out.println("Enter a valid, non-negative salary.");
        }
    }

    private static void printEmployee(Employee employee) {
        System.out.printf("%-15s %-25s %-20s %12.2f%n",
                employee.getId(), employee.getName(), employee.getDepartment(), employee.getSalary());
    }

    private static final class Employee {
        private final String id;
        private final String name;
        private final String department;
        private final double salary;

        private Employee(String id, String name, String department, double salary) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.salary = salary;
        }

        private String getId() {
            return id;
        }

        private String getName() {
            return name;
        }

        private String getDepartment() {
            return department;
        }

        private double getSalary() {
            return salary;
        }
    }
}
