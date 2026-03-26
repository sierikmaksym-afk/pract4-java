import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;
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
        if (args.length == 0) {
            System.out.println("Помилка: потрібно передати шлях до файлу db.properties.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        Repository repository;

        try {
            repository = new Repository(args[0]);
        } catch (SQLException e) {
            System.out.println("Помилка підключення до бази даних: " + e.getMessage());
            scanner.close();
            return;
        }

        while (running) {
            printMainMenu();
            int choice = readMenuChoice(scanner);

            switch (choice) {
                case 1:
                    createObjectMenu(scanner, repository);
                    break;
                case 2:
                    System.out.println("Роботу програми завершено.");
                    running = false;
                    break;
                default:
                    System.out.println("Помилка: оберіть пункт від 1 до 2.");
            }
        }

        try {
            repository.close();
        } catch (SQLException e) {
            System.out.println("Помилка закриття з'єднання з базою даних: " + e.getMessage());
        }

        scanner.close();
    }

    /**
     * Виводить головне меню програми.
     */
    private static void printMainMenu() {
        System.out.println("\nГоловне меню:");
        System.out.println("1. Додати працівника");
        System.out.println("2. Завершити роботу");
    }

    /**
     * Виводить підменю створення об'єктів.
     */
    private static void createObjectMenu(Scanner scanner, Repository repository) {
        boolean inCreateMenu = true;

        while (inCreateMenu) {
            printCreateMenu();
            int choice = readMenuChoice(scanner);

            switch (choice) {
                case 1:
                    createEmployee(scanner, repository);
                    break;
                case 2:
                    createContractEmployee(scanner, repository);
                    break;
                case 3:
                    createFullTimeEmployee(scanner, repository);
                    break;
                case 4:
                    createRemoteEmployee(scanner, repository);
                    break;
                case 5:
                    createManager(scanner, repository);
                    break;
                case 0:
                    inCreateMenu = false;
                    break;
                default:
                    System.out.println("Помилка: оберіть пункт від 0 до 5.");
            }
        }
    }


    private static void printSearchMenu() {
        System.out.println("\nПошук об'єкта:");
        System.out.println("1. Пошук за id");
        System.out.println("2. Пошук за ім'ям");
        System.out.println("3. Пошук за типом об'єкта");
        System.out.println("0. Повернутися до головного меню");
    }

    /**
     * Виводить меню вибору типу об'єкта.
     */
    private static void printCreateMenu() {
        System.out.println("\nОберіть тип об'єкта для створення:");
        System.out.println("1. Employee");
        System.out.println("2. ContractEmployee");
        System.out.println("3. FullTimeEmployee");
        System.out.println("4. RemoteEmployee");
        System.out.println("5. Manager");
        System.out.println("0. Повернутися до головного меню");
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
    private static void createEmployee(Scanner scanner, Repository repository) {
        try {
            int id = readPositiveInt(scanner, "Введіть id: ");
            String name = readNonEmptyString(scanner, "Введіть ім'я: ");
            double salary = readNonNegativeDouble(scanner, "Введіть зарплату: ");

            Employee employee = new Employee(id, name, salary);
            repository.insertEmployee(employee);
            System.out.println("Об'єкт Employee успішно додано.");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка створення об'єкта: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Помилка під час запису в базу даних: " + e.getMessage());
        }
    }

    /**
     * Створює об'єкт класу ContractEmployee.
     */
    private static void createContractEmployee(Scanner scanner, Repository repository) {
        try {
            int id = readPositiveInt(scanner, "Введіть id: ");
            String name = readNonEmptyString(scanner, "Введіть ім'я: ");
            double salary = readNonNegativeDouble(scanner, "Введіть зарплату: ");
            int contractMonths = readPositiveInt(scanner, "Введіть тривалість контракту в місяцях: ");

            Employee employee = new ContractEmployee(id, name, salary, contractMonths);
            repository.insertEmployee(employee);
            System.out.println("Об'єкт ContractEmployee успішно додано.");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка створення об'єкта: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Помилка під час запису в базу даних: " + e.getMessage());
        }
    }

    /**
     * Створює об'єкт класу FullTimeEmployee.
     */
    private static void createFullTimeEmployee(Scanner scanner, Repository repository) {
        try {
            int id = readPositiveInt(scanner, "Введіть id: ");
            String name = readNonEmptyString(scanner, "Введіть ім'я: ");
            double salary = readNonNegativeDouble(scanner, "Введіть зарплату: ");
            double bonus = readNonNegativeDouble(scanner, "Введіть бонус: ");

            Employee employee = new FullTimeEmployee(id, name, salary, bonus);
            repository.insertEmployee(employee);
            System.out.println("Об'єкт FullTimeEmployee успішно додано.");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка створення об'єкта: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Помилка під час запису в базу даних: " + e.getMessage());
        }
    }

    /**
     * Створює об'єкт класу RemoteEmployee.
     */
    private static void createRemoteEmployee(Scanner scanner, Repository repository) {
        try {
            int id = readPositiveInt(scanner, "Введіть id: ");
            String name = readNonEmptyString(scanner, "Введіть ім'я: ");
            double salary = readNonNegativeDouble(scanner, "Введіть зарплату: ");
            String workCountry = readNonEmptyString(scanner, "Введіть країну дистанційної роботи: ");

            Employee employee = new RemoteEmployee(id, name, salary, workCountry);
            repository.insertEmployee(employee);
            System.out.println("Об'єкт RemoteEmployee успішно додано.");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка створення об'єкта: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Помилка під час запису в базу даних: " + e.getMessage());
        }
    }

    /**
     * Створює об'єкт класу Manager.
     */
    private static void createManager(Scanner scanner, Repository repository) {
        try {
            int id = readPositiveInt(scanner, "Введіть id: ");
            String name = readNonEmptyString(scanner, "Введіть ім'я: ");
            double salary = readNonNegativeDouble(scanner, "Введіть зарплату: ");
            int teamSize = readPositiveInt(scanner, "Введіть кількість підлеглих: ");

            Employee employee = new Manager(id, name, salary, teamSize);
            repository.insertEmployee(employee);
            System.out.println("Об'єкт Manager успішно додано.");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка створення об'єкта: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Помилка під час запису в базу даних: " + e.getMessage());
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