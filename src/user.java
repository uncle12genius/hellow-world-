import java.util.*;
import java.util.concurrent.*;

// User authentication class
class UserAuthenticator {
    private static final String VALID_USERNAME = "evan";
    private static final String VALID_PASSWORD = "evan2003";

    public boolean authenticate(String username, String password) {
        return VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password);
    }
}

// Abstract Employee class
abstract class Employee {
    protected String name;
    protected String id;
    protected String department;
    protected double salary;

    public Employee(String name, String id, String department, double salary) {
        this.name = name;
        this.id = id;
        this.department = department;
        this.salary = salary;
    }

    // Abstract method for bonus calculation
    public abstract double calculateBonus();

    // Getters
    public String getName() { return name; }
    public String getId() { return id; }
    public double getSalary() { return salary; }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Dept: " + department +
                ", Salary: $" + String.format("%.2f", salary) +
                ", Bonus: $" + String.format("%.2f", calculateBonus());
    }
}

// Concrete employee classes with overridden bonus calculation
class Manager extends Employee {
    public Manager(String name, String id, String department, double salary) {
        super(name, id, department, salary);
    }

    @Override
    public double calculateBonus() {
        return salary * 0.20; // 20% bonus for managers
    }
}

class Developer extends Employee {
    public Developer(String name, String id, String department, double salary) {
        super(name, id, department, salary);
    }

    @Override
    public double calculateBonus() {
        return salary * 0.15; // 15% bonus for developers
    }
}

// Employee Management System
class EmployeeManagementSystem {
    private List<Employee> employees;
    private UserAuthenticator authenticator;
    private Scanner scanner;

    public EmployeeManagementSystem() {
        employees = new ArrayList<>();
        authenticator = new UserAuthenticator();
        scanner = new Scanner(System.in);
    }

    public boolean login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        return authenticator.authenticate(username, password);
    }

    public void addEmployee() {
        try {
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            System.out.print("Enter ID: ");
            String id = scanner.nextLine();
            System.out.print("Enter department: ");
            String dept = scanner.nextLine();
            System.out.print("Enter salary: ");
            double salary = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter role (Manager/Developer): ");
            String role = scanner.nextLine();

            Employee emp = role.equalsIgnoreCase("Manager") ?
                    new Manager(name, id, dept, salary) :
                    new Developer(name, id, dept, salary);
            employees.add(emp);
            System.out.println("Employee added successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid salary input!");
        }
    }

    // Multithreaded display and bonus calculation
    public void displayAllEmployees() {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Thread for displaying employees
        executor.submit(() -> {
            System.out.println("\nEmployee List:");
            employees.forEach(System.out::println);
        });

        // Thread for calculating total bonuses
        executor.submit(() -> {
            double totalBonus = employees.stream()
                    .mapToDouble(Employee::calculateBonus)
                    .sum();
            System.out.println("\nTotal Bonus Amount: $" + String.format("%.2f", totalBonus));
        });

        executor.shutdown();
        try {
            executor.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("Error in thread execution");
        }
    }

    public void sortEmployees() {
        System.out.print("Sort by (1) Salary or (2) Name: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 1) {
                employees.sort(Comparator.comparingDouble(Employee::getSalary));
            } else if (choice == 2) {
                employees.sort(Comparator.comparing(Employee::getName));
            }
            displayAllEmployees();
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice!");
        }
    }

    public void searchEmployee() {
        System.out.print("Search by (1) ID or (2) Name: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter search term: ");
            String term = scanner.nextLine();

            employees.stream()
                    .filter(e -> (choice == 1 && e.getId().equals(term)) ||
                            (choice == 2 && e.getName().equalsIgnoreCase(term)))
                    .forEach(System.out::println);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
        }
    }

    public void run() {
        if (!login()) {
            System.out.println("Authentication failed!");
            return;
        }

        while (true) {
            System.out.println("\n1. Add Employee\n2. Display All\n3. Sort Employees\n4. Search Employee\n5. Exit");
            System.out.print("Choice: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1: addEmployee(); break;
                    case 2: displayAllEmployees(); break;
                    case 3: sortEmployees(); break;
                    case 4: searchEmployee(); break;
                    case 5: return;
                    default: System.out.println("Invalid choice!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input!");
            }
        }
    }
}

public class user {
    public static void main(String[] args) {
        EmployeeManagementSystem ems = new EmployeeManagementSystem();
        ems.run();
    }
}