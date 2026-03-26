import java.util.ArrayList;
import java.util.Scanner;

/**
 * Головний клас програми для роботи з працівниками через консольне меню.
 */
public class Main {

    /**
     * Точка входу в програму.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Employee> employees = new ArrayList<Employee>();
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readMenuChoice(scanner);

            switch (choice) {
                case 1:
                    createEmployee(scanner, employees);
                    break;
                case 2:
                    createContractEmployee(scanner, employees);
                    break;
                case 3:
                    createFullTimeEmployee(scanner, employees);
                    break;
                case 4:
                    printAllEmployees(employees);
                    break;
                case 5:
                    System.out.println("Роботу програми завершено.");
                    running = false;
                    break;
                default:
                    System.out.println("Помилка: оберіть пункт від 1 до 5.");
            }
        }

        scanner.close();
    }

    /**
     * Виводить консольне меню.
     */
    private static void printMenu() {
        System.out.println("\nМеню:");
        System.out.println("1. Створити Employee");
        System.out.println("2. Створити ContractEmployee");
        System.out.println("3. Створити FullTimeEmployee");
        System.out.println("4. Вивести інформацію про всі об'єкти");
        System.out.println("5. Завершити роботу");
    }

    /**
     * Зчитує номер пункту меню.
     */
    private static int readMenuChoice(Scanner scanner) {
        while (true) {
            System.out.print("Оберіть пункт меню: ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Помилка: рядок не може бути порожнім.");
                continue;
            }

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Помилка: введіть ціле число.");
            }
        }
    }

    /**
     * Створює об'єкт базового класу Employee.
     */
    private static void createEmployee(Scanner scanner, ArrayList<Employee> employees) {
        try {
            int id = readPositiveInt(scanner, "Введіть id: ");
            String name = readNonEmptyString(scanner, "Введіть ім'я: ");
            double salary = readNonNegativeDouble(scanner, "Введіть зарплату: ");

            Employee employee = new Employee(id, name, salary);
            employees.add(employee);
            System.out.println("Об'єкт Employee успішно додано.");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка створення об'єкта: " + e.getMessage());
        }
    }

    /**
     * Створює об'єкт похідного класу ContractEmployee.
     */
    private static void createContractEmployee(Scanner scanner, ArrayList<Employee> employees) {
        try {
            int id = readPositiveInt(scanner, "Введіть id: ");
            String name = readNonEmptyString(scanner, "Введіть ім'я: ");
            double salary = readNonNegativeDouble(scanner, "Введіть зарплату: ");
            int contractMonths = readPositiveInt(scanner, "Введіть тривалість контракту в місяцях: ");

            Employee employee = new ContractEmployee(id, name, salary, contractMonths);
            employees.add(employee);
            System.out.println("Об'єкт ContractEmployee успішно додано.");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка створення об'єкта: " + e.getMessage());
        }
    }

    /**
     * Створює об'єкт похідного класу FullTimeEmployee.
     */
    private static void createFullTimeEmployee(Scanner scanner, ArrayList<Employee> employees) {
        try {
            int id = readPositiveInt(scanner, "Введіть id: ");
            String name = readNonEmptyString(scanner, "Введіть ім'я: ");
            double salary = readNonNegativeDouble(scanner, "Введіть зарплату: ");
            double bonus = readNonNegativeDouble(scanner, "Введіть бонус: ");

            Employee employee = new FullTimeEmployee(id, name, salary, bonus);
            employees.add(employee);
            System.out.println("Об'єкт FullTimeEmployee успішно додано.");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка створення об'єкта: " + e.getMessage());
        }
    }

    /**
     * Виводить інформацію про всі створені об'єкти.
     */
    private static void printAllEmployees(ArrayList<Employee> employees) {
        if (employees.isEmpty()) {
            System.out.println("Список об'єктів порожній.");
            return;
        }

        System.out.println("\nІнформація про всі об'єкти:");
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    /**
     * Зчитує додатне ціле число з клавіатури.
     */
    private static int readPositiveInt(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Помилка: рядок не може бути порожнім.");
                continue;
            }

            try {
                int value = Integer.parseInt(input);
                if (value <= 0) {
                    System.out.println("Помилка: число повинно бути більше 0.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Помилка: введіть коректне ціле число.");
            }
        }
    }

    /**
     * Зчитує непорожній рядок з клавіатури.
     */
    private static String readNonEmptyString(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Помилка: рядок не може бути порожнім.");
                continue;
            }

            return input;
        }
    }

    /**
     * Зчитує невід'ємне дійсне число з клавіатури.
     */
    private static double readNonNegativeDouble(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Помилка: рядок не може бути порожнім.");
                continue;
            }

            try {
                double value = Double.parseDouble(input);
                if (value < 0) {
                    System.out.println("Помилка: значення не може бути від'ємним.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Помилка: введіть коректне число.");
            }
        }
    }
}