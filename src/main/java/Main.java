import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.UUID;

/**
 * Головний клас програми для роботи з працівниками через консольне меню.
 */
public class Main {

    /**
     * Точка входу в програму.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Company company = new Company("Default Company", "Default Address");
        boolean running = true;

        while (running) {
            printMainMenu();
            int choice = readMenuChoice(scanner);

            switch (choice) {
                case 1:
                    createObjectMenu(scanner, company);
                    break;
                case 2:
                    printAllEmployees(company);
                    break;
                case 3:
                    searchObjectMenu(scanner, company);
                    break;
                case 4:
                    sortEmployeesMenu(scanner, company);
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
     * Виводить головне меню програми.
     */
    private static void printMainMenu() {
        System.out.println("\nГоловне меню:");
        System.out.println("1. Створити новий об’єкт");
        System.out.println("2. Вивести інформацію про всі об’єкти");
        System.out.println("3. Пошук об’єкта");
        System.out.println("4. Вивести відсортовану інформацію про всі об'єкти");
        System.out.println("5. Завершити роботу програми");
    }

    /**
     * Виводить підменю створення об'єктів.
     */
    private static void createObjectMenu(Scanner scanner, Company company) {
        boolean inCreateMenu = true;

        while (inCreateMenu) {
            printCreateMenu();
            int choice = readMenuChoice(scanner);

            switch (choice) {
                case 1:
                    createEmployee(scanner, company);
                    break;
                case 2:
                    createContractEmployee(scanner, company);
                    break;
                case 3:
                    createFullTimeEmployee(scanner, company);
                    break;
                case 4:
                    createRemoteEmployee(scanner, company);
                    break;
                case 5:
                    createManager(scanner, company);
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
     * Виводить підменю пошуку.
     */
    private static void printSearchMenu() {
        System.out.println("\nПошук об'єкта:");
        System.out.println("1. Пошук за UUID");
        System.out.println("2. Пошук за ім'ям");
        System.out.println("3. Пошук за типом об'єкта");
        System.out.println("0. Повернутися до головного меню");
    }

    /**
     * Виводить підменю сортування.
     */
    private static void printSortMenu() {
        System.out.println("\nОберіть критерій сортування:");
        System.out.println("1. Сортувати за ім'ям");
        System.out.println("2. Сортувати за зарплатою");
        System.out.println("3. Сортувати за UUID");
        System.out.println("0. Повернутися в головне меню");
    }

    /**
     * Виводить підменю пошуку та виконує пошук за вибраним критерієм.
     */
    private static void searchObjectMenu(Scanner scanner, Company company) {
        boolean inSearchMenu = true;

        while (inSearchMenu) {
            printSearchMenu();
            int choice = readMenuChoice(scanner);

            switch (choice) {
                case 1: {
                    UUID uuid = readUuid(scanner, "Введіть UUID для пошуку: ");
                    ArrayList<Employee> results = company.searchByUuid(uuid);
                    printSearchResults(results);
                    break;
                }
                case 2: {
                    String name = readNonEmptyString(scanner, "Введіть ім'я для пошуку: ");
                    ArrayList<Employee> results = company.searchByName(name);
                    printSearchResults(results);
                    break;
                }
                case 3: {
                    String type = readNonEmptyString(scanner, "Введіть тип об'єкта для пошуку: ");
                    ArrayList<Employee> results = company.searchByType(type);
                    printSearchResults(results);
                    break;
                }
                case 0:
                    inSearchMenu = false;
                    break;
                default:
                    System.out.println("Помилка: оберіть пункт від 0 до 3.");
            }
        }
    }

    /**
     * Виводить підменю сортування та виконує сортування за вибраним критерієм.
     */
    private static void sortEmployeesMenu(Scanner scanner, Company company) {
        boolean inSortMenu = true;

        while (inSortMenu) {
            printSortMenu();
            int choice = readMenuChoice(scanner);

            switch (choice) {
                case 1:
                    sortByName(company);
                    break;
                case 2:
                    sortBySalary(company);
                    break;
                case 3:
                    sortByUuid(company);
                    break;
                case 0:
                    inSortMenu = false;
                    break;
                default:
                    System.out.println("Помилка: оберіть пункт від 0 до 3.");
            }
        }
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
    private static void createEmployee(Scanner scanner, Company company) {
        try {
            String name = readNonEmptyString(scanner, "Введіть ім'я: ");
            double salary = readNonNegativeDouble(scanner, "Введіть зарплату: ");

            Employee employee = new Employee(name, salary);
            company.addNewEmployee(employee);
            System.out.println("Об'єкт Employee успішно додано.");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка створення об'єкта: " + e.getMessage());
        }
    }

    /**
     * Створює об'єкт класу ContractEmployee.
     */
    private static void createContractEmployee(Scanner scanner, Company company) {
        try {
            String name = readNonEmptyString(scanner, "Введіть ім'я: ");
            double salary = readNonNegativeDouble(scanner, "Введіть зарплату: ");
            int contractMonths = readPositiveInt(scanner, "Введіть тривалість контракту в місяцях: ");

            Employee employee = new ContractEmployee(name, salary, contractMonths);
            company.addNewEmployee(employee);
            System.out.println("Об'єкт ContractEmployee успішно додано.");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка створення об'єкта: " + e.getMessage());
        }
    }

    /**
     * Створює об'єкт класу FullTimeEmployee.
     */
    private static void createFullTimeEmployee(Scanner scanner, Company company) {
        try {
            String name = readNonEmptyString(scanner, "Введіть ім'я: ");
            double salary = readNonNegativeDouble(scanner, "Введіть зарплату: ");
            double bonus = readNonNegativeDouble(scanner, "Введіть бонус: ");

            Employee employee = new FullTimeEmployee(name, salary, bonus);
            company.addNewEmployee(employee);
            System.out.println("Об'єкт FullTimeEmployee успішно додано.");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка створення об'єкта: " + e.getMessage());
        }
    }

    /**
     * Створює об'єкт класу RemoteEmployee.
     */
    private static void createRemoteEmployee(Scanner scanner, Company company) {
        try {
            String name = readNonEmptyString(scanner, "Введіть ім'я: ");
            double salary = readNonNegativeDouble(scanner, "Введіть зарплату: ");
            String workCountry = readNonEmptyString(scanner, "Введіть країну дистанційної роботи: ");

            Employee employee = new RemoteEmployee(name, salary, workCountry);
            company.addNewEmployee(employee);
            System.out.println("Об'єкт RemoteEmployee успішно додано.");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка створення об'єкта: " + e.getMessage());
        }
    }

    /**
     * Створює об'єкт класу Manager.
     */
    private static void createManager(Scanner scanner, Company company) {
        try {
            String name = readNonEmptyString(scanner, "Введіть ім'я: ");
            double salary = readNonNegativeDouble(scanner, "Введіть зарплату: ");
            int teamSize = readPositiveInt(scanner, "Введіть кількість підлеглих: ");

            Employee employee = new Manager(name, salary, teamSize);
            company.addNewEmployee(employee);
            System.out.println("Об'єкт Manager успішно додано.");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка створення об'єкта: " + e.getMessage());
        }
    }

    /**
     * Виводить інформацію про всі створені об'єкти.
     */
    private static void printAllEmployees(Company company) {
        if (company.getEmployees().isEmpty()) {
            System.out.println("Список об'єктів порожній.");
            return;
        }

        System.out.println("\nКомпанія: " + company.getName());
        System.out.println("Адреса: " + company.getAddress());
        System.out.println("Інформація про всі об'єкти:");

        for (Employee employee : company.getEmployees()) {
            System.out.println(employee);
        }
    }

    /**
     * Виводить результат пошуку.
     */
    private static void printSearchResults(ArrayList<Employee> results) {
        if (results.isEmpty()) {
            System.out.println("Жоден об'єкт не відповідає умовам пошуку.");
            return;
        }

        System.out.println("\nРезультати пошуку:");
        for (Employee employee : results) {
            System.out.println(employee);
        }
    }

    /**
     * Сортує список працівників за ім'ям та виводить результат.
     */
    private static void sortByName(Company company) {
        if (company.getEmployees().isEmpty()) {
            System.out.println("Список об'єктів порожній.");
            return;
        }

        ArrayList<Employee> sortedEmployees = new ArrayList<Employee>(company.getEmployees());

        Comparator<Employee> comparator = new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                int result = o1.getName().compareToIgnoreCase(o2.getName());

                if (result != 0) {
                    return result;
                }

                return o1.getUuid().toString().compareTo(o2.getUuid().toString());
            }
        };

        Collections.sort(sortedEmployees, comparator);

        System.out.println("\nВідсортована інформація за ім'ям:");
        for (Employee employee : sortedEmployees) {
            System.out.println(employee);
        }
    }

    /**
     * Сортує список працівників за зарплатою та виводить результат.
     */
    private static void sortBySalary(Company company) {
        if (company.getEmployees().isEmpty()) {
            System.out.println("Список об'єктів порожній.");
            return;
        }

        ArrayList<Employee> sortedEmployees = new ArrayList<Employee>(company.getEmployees());

        Comparator<Employee> comparator = new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                int result = Double.compare(o1.getSalary(), o2.getSalary());

                if (result != 0) {
                    return result;
                }

                result = o1.getName().compareToIgnoreCase(o2.getName());

                if (result != 0) {
                    return result;
                }

                return o1.getUuid().toString().compareTo(o2.getUuid().toString());
            }
        };

        Collections.sort(sortedEmployees, comparator);

        System.out.println("\nВідсортована інформація за зарплатою:");
        for (Employee employee : sortedEmployees) {
            System.out.println(employee);
        }
    }

    /**
     * Сортує список працівників за UUID та виводить результат.
     */
    private static void sortByUuid(Company company) {
        if (company.getEmployees().isEmpty()) {
            System.out.println("Список об'єктів порожній.");
            return;
        }

        ArrayList<Employee> sortedEmployees = new ArrayList<Employee>(company.getEmployees());

        Comparator<Employee> comparator = new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                return o1.getUuid().toString().compareTo(o2.getUuid().toString());
            }
        };

        Collections.sort(sortedEmployees, comparator);

        System.out.println("\nВідсортована інформація за UUID:");
        for (Employee employee : sortedEmployees) {
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
     * Зчитує коректний UUID з клавіатури.
     */
    private static UUID readUuid(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Помилка: рядок не може бути порожнім.");
                continue;
            }

            try {
                return UUID.fromString(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка: введіть коректний UUID.");
            }
        }
    }
}