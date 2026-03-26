import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Головний клас програми для роботи з працівниками через консольне меню.
 */
public class Main {
    private static final String FILE_NAME = "input.txt";

    /**
     * Точка входу в програму.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Employee> employees = new ArrayList<Employee>();
        boolean running = true;

        loadFromFile(employees);

        while (running) {
            printMainMenu();
            int choice = readMenuChoice(scanner);

            switch (choice) {
                case 1:
                    createObjectMenu(scanner, employees);
                    break;
                case 2:
                    printAllEmployees(employees);
                    break;
                case 3:
                    saveToFile(employees);
                    System.out.println("Роботу програми завершено.");
                    running = false;
                    break;
                default:
                    System.out.println("Помилка: оберіть пункт від 1 до 3.");
            }
        }

        scanner.close();
    }

    /**
     * Виводить головне меню.
     */
    private static void printMainMenu() {
        System.out.println("\nГоловне меню:");
        System.out.println("1. Створити новий об’єкт");
        System.out.println("2. Вивести інформацію про всі об’єкти");
        System.out.println("3. Завершити роботу програми");
    }

    /**
     * Виводить підменю створення об'єктів.
     */
    private static void createObjectMenu(Scanner scanner, ArrayList<Employee> employees) {
        boolean inCreateMenu = true;

        while (inCreateMenu) {
            printCreateMenu();
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
                    createRemoteEmployee(scanner, employees);
                    break;
                case 5:
                    createManager(scanner, employees);
                    break;
                case 0:
                    inCreateMenu = false;
                    break;
                default:
                    System.out.println("Помилка: оберіть пункт від 0 до 5.");
            }
        }
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
     * Створює об'єкт класу ContractEmployee.
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
     * Створює об'єкт класу FullTimeEmployee.
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
     * Створює об'єкт класу RemoteEmployee.
     */
    private static void createRemoteEmployee(Scanner scanner, ArrayList<Employee> employees) {
        try {
            int id = readPositiveInt(scanner, "Введіть id: ");
            String name = readNonEmptyString(scanner, "Введіть ім'я: ");
            double salary = readNonNegativeDouble(scanner, "Введіть зарплату: ");
            String workCountry = readNonEmptyString(scanner, "Введіть країну дистанційної роботи: ");

            Employee employee = new RemoteEmployee(id, name, salary, workCountry);
            employees.add(employee);
            System.out.println("Об'єкт RemoteEmployee успішно додано.");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка створення об'єкта: " + e.getMessage());
        }
    }

    /**
     * Створює об'єкт класу Manager.
     */
    private static void createManager(Scanner scanner, ArrayList<Employee> employees) {
        try {
            int id = readPositiveInt(scanner, "Введіть id: ");
            String name = readNonEmptyString(scanner, "Введіть ім'я: ");
            double salary = readNonNegativeDouble(scanner, "Введіть зарплату: ");
            int teamSize = readPositiveInt(scanner, "Введіть кількість підлеглих: ");

            Employee employee = new Manager(id, name, salary, teamSize);
            employees.add(employee);
            System.out.println("Об'єкт Manager успішно додано.");
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

    /**
     * Зчитує об'єкти з файлу та додає їх до колекції.
     */
    private static void loadFromFile(ArrayList<Employee> employees) {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(FILE_NAME));
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                try {
                    Employee employee = parseEmployee(line);
                    if (employee != null) {
                        employees.add(employee);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Пропущено некоректний запис у файлі: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Файл input.txt не знайдено або недоступний. Програму буде запущено з порожнім списком.");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("Не вдалося закрити файл після зчитування.");
                }
            }
        }
    }

    /**
     * Записує всі об'єкти з колекції у файл.
     */
    private static void saveToFile(ArrayList<Employee> employees) {
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(FILE_NAME));

            for (Employee employee : employees) {
                writer.write(employee.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Помилка під час запису у файл.");
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Не вдалося закрити файл після запису.");
                }
            }
        }
    }

    /**
     * Створює об'єкт потрібного типу на основі рядка з файлу.
     */
    private static Employee parseEmployee(String line) {
        String[] parts = line.split(";");

        if (parts.length < 4) {
            throw new IllegalArgumentException("Недостатньо даних у рядку.");
        }

        String type = parts[0].trim();
        int id = Integer.parseInt(parts[1].trim());
        String name = parts[2].trim();
        double salary = Double.parseDouble(parts[3].trim());

        if ("Employee".equals(type)) {
            if (parts.length != 4) {
                throw new IllegalArgumentException("Некоректна кількість полів для Employee.");
            }
            return new Employee(id, name, salary);
        }

        if ("ContractEmployee".equals(type)) {
            if (parts.length != 5) {
                throw new IllegalArgumentException("Некоректна кількість полів для ContractEmployee.");
            }
            int contractMonths = Integer.parseInt(parts[4].trim());
            return new ContractEmployee(id, name, salary, contractMonths);
        }

        if ("FullTimeEmployee".equals(type)) {
            if (parts.length != 5) {
                throw new IllegalArgumentException("Некоректна кількість полів для FullTimeEmployee.");
            }
            double bonus = Double.parseDouble(parts[4].trim());
            return new FullTimeEmployee(id, name, salary, bonus);
        }

        if ("RemoteEmployee".equals(type)) {
            if (parts.length != 5) {
                throw new IllegalArgumentException("Некоректна кількість полів для RemoteEmployee.");
            }
            String workCountry = parts[4].trim();
            return new RemoteEmployee(id, name, salary, workCountry);
        }

        if ("Manager".equals(type)) {
            if (parts.length != 5) {
                throw new IllegalArgumentException("Некоректна кількість полів для Manager.");
            }
            int teamSize = Integer.parseInt(parts[4].trim());
            return new Manager(id, name, salary, teamSize);
        }

        throw new IllegalArgumentException("Невідомий тип об'єкта.");
    }
}