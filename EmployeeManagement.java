import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A console application for managing employee records.
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
            System.out.println("4. Update employee details");
            System.out.println("5. Delete employee");
            System.out.println("6. Exit");
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
                    updateEmployee();
                    break;
                case "5":
                    deleteEmployee();
                    break;
                case "6":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1, 2, 3, 4, 5, or 6.");
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

        printTableHeader();
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
        printTableHeader();
        printEmployee(employee);
    }

    private static void updateEmployee() {
        System.out.print("Enter the ID of the employee to update: ");
        String id = SCANNER.nextLine().trim();
        Employee employee = findById(id);
        if (employee == null) {
            System.out.println("No employee found with ID " + id + ".");
            return;
        }

        System.out.println("Press Enter to keep the current value.");
        employee.setName(readOptional("Name", employee.getName()));
        employee.setDepartment(readOptional("Department", employee.getDepartment()));
        employee.setSalary(readOptionalSalary(employee.getSalary()));
        System.out.println("Employee details updated successfully.");
    }

    private static void deleteEmployee() {
        System.out.print("Enter the ID of the employee to delete: ");
        String id = SCANNER.nextLine().trim();
        Employee employee = findById(id);
        if (employee == null) {
            System.out.println("No employee found with ID " + id + ".");
            return;
        }

        System.out.print("Delete employee " + employee.getName() + " (ID " + employee.getId() + ")? (y/n): ");
        String confirmation = SCANNER.nextLine().trim();
        if (confirmation.equalsIgnoreCase("y") || confirmation.equalsIgnoreCase("yes")) {
            EMPLOYEES.remove(employee);
            System.out.println("Employee deleted successfully.");
        } else {
            System.out.println("Delete cancelled.");
        }
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

    private static String readOptional(String field, String currentValue) {
        System.out.print(field + " [" + currentValue + "]: ");
        String value = SCANNER.nextLine().trim();
        return value.isEmpty() ? currentValue : value;
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

    private static double readOptionalSalary(double currentSalary) {
        while (true) {
            System.out.printf("Salary [%.2f] (press Enter to keep current): ", currentSalary);
            String input = SCANNER.nextLine().trim();
            if (input.isEmpty()) {
                return currentSalary;
            }
            try {
                double salary = Double.parseDouble(input);
                if (Double.isFinite(salary) && salary >= 0) {
                    return salary;
                }
            } catch (NumberFormatException ignored) {
                // Show the same helpful message for malformed and out-of-range values.
            }
            System.out.println("Enter a valid, non-negative salary, or press Enter to keep the current salary.");
        }
    }

    private static void printTableHeader() {
        System.out.printf("%-15s %-25s %-20s %12s%n", "Employee ID", "Name", "Department", "Salary");
        System.out.println("--------------------------------------------------------------------------");
    }

    private static void printEmployee(Employee employee) {
        System.out.printf("%-15s %-25s %-20s %12.2f%n",
                employee.getId(), employee.getName(), employee.getDepartment(), employee.getSalary());
    }

    private static final class Employee {
        private final String id;
        private String name;
        private String department;
        private double salary;

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

        private void setName(String name) {
            this.name = name;
        }

        private String getDepartment() {
            return department;
        }

        private void setDepartment(String department) {
            this.department = department;
        }

        private double getSalary() {
            return salary;
        }

        private void setSalary(double salary) {
            this.salary = salary;
        }
    }
}
