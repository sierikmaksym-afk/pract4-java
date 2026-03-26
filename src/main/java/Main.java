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
        Company company = loadCompanyFromFile();
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
                    saveToFile(company);
                    System.out.println("Роботу програми завершено.");
                    running = false;
                    break;
                default:
                    System.out.println("Помилка: оберіть пункт від 1 до 4.");
            }
        }

        scanner.close();
    }


    private static void printMainMenu() {
        System.out.println("\nГоловне меню:");
        System.out.println("1. Створити новий об’єкт");
        System.out.println("2. Вивести інформацію про всі об’єкти");
        System.out.println("3. Пошук об’єкта");
        System.out.println("4. Завершити роботу програми");
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

    private static void printSearchMenu() {
        System.out.println("\nПошук об'єкта:");
        System.out.println("1. Пошук за id");
        System.out.println("2. Пошук за ім'ям");
        System.out.println("3. Пошук за типом об'єкта");
        System.out.println("0. Повернутися до головного меню");
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
                    int id = readPositiveInt(scanner, "Введіть id для пошуку: ");
                    ArrayList<Employee> results = company.searchById(id);
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
     * Зчитує дані компанії та працівників із файлу.
     */
    private static Company loadCompanyFromFile() {
        BufferedReader reader = null;
        Company company = null;

        try {
            reader = new BufferedReader(new FileReader(FILE_NAME));
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                try {
                    if (line.startsWith("Company;")) {
                        company = parseCompany(line);
                    } else {
                        if (company == null) {
                            System.out.println("Спочатку у файлі має бути запис про компанію.");
                            continue;
                        }

                        parseAndAddEmployee(line, company);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Пропущено некоректний запис у файлі: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Файл input.txt не знайдено або недоступний. Буде створено порожню компанію.");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("Не вдалося закрити файл після зчитування.");
                }
            }
        }

        if (company == null) {
            company = new Company("Default Company", "Default Address");
        }

        return company;
    }

    /**
     * Записує дані компанії та всі об'єкти з колекції у файл.
     */
    private static void saveToFile(Company company) {
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(FILE_NAME));
            writer.write("Company;" + company.getName() + ";" + company.getAddress());
            writer.newLine();

            for (int i = 0; i < company.getEmployees().size(); i++) {
                Employee employee = company.getEmployees().get(i);
                int quantity = company.getQuantityByIndex(i);
                writer.write(convertEmployeeToFileString(employee, quantity));
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
     * Створює працівника на основі рядка з файлу та додає його до компанії.
     */
    private static void parseAndAddEmployee(String line, Company company) {
        String[] parts = line.split(";");

        if (parts.length < 5) {
            throw new IllegalArgumentException("Недостатньо даних у рядку.");
        }

        String type = parts[0].trim();
        int quantity = Integer.parseInt(parts[1].trim());
        int id = Integer.parseInt(parts[2].trim());
        String name = parts[3].trim();
        double salary = Double.parseDouble(parts[4].trim());

        if ("Employee".equals(type)) {
            if (parts.length != 5) {
                throw new IllegalArgumentException("Некоректна кількість полів для Employee.");
            }
            company.addNewEmployee(new Employee(id, name, salary), quantity);
            return;
        }

        if ("ContractEmployee".equals(type)) {
            if (parts.length != 6) {
                throw new IllegalArgumentException("Некоректна кількість полів для ContractEmployee.");
            }
            int contractMonths = Integer.parseInt(parts[5].trim());
            company.addNewEmployee(new ContractEmployee(id, name, salary, contractMonths), quantity);
            return;
        }

        if ("FullTimeEmployee".equals(type)) {
            if (parts.length != 6) {
                throw new IllegalArgumentException("Некоректна кількість полів для FullTimeEmployee.");
            }
            double bonus = Double.parseDouble(parts[5].trim());
            company.addNewEmployee(new FullTimeEmployee(id, name, salary, bonus), quantity);
            return;
        }

        if ("RemoteEmployee".equals(type)) {
            if (parts.length != 6) {
                throw new IllegalArgumentException("Некоректна кількість полів для RemoteEmployee.");
            }
            String workCountry = parts[5].trim();
            company.addNewEmployee(new RemoteEmployee(id, name, salary, workCountry), quantity);
            return;
        }

        if ("Manager".equals(type)) {
            if (parts.length != 6) {
                throw new IllegalArgumentException("Некоректна кількість полів для Manager.");
            }
            int teamSize = Integer.parseInt(parts[5].trim());
            company.addNewEmployee(new Manager(id, name, salary, teamSize), quantity);
            return;
        }

        throw new IllegalArgumentException("Невідомий тип об'єкта.");
    }

    /**
     * Перетворює об'єкт працівника у рядок для запису у файл.
     */
    private static String convertEmployeeToFileString(Employee employee, int quantity) {
        if (employee instanceof ContractEmployee) {
            ContractEmployee contractEmployee = (ContractEmployee) employee;
            return "ContractEmployee;" + quantity + ";" + contractEmployee.getId() + ";" +
                    contractEmployee.getName() + ";" + contractEmployee.getSalary() + ";" +
                    contractEmployee.getContractMonths();
        }

        if (employee instanceof FullTimeEmployee) {
            FullTimeEmployee fullTimeEmployee = (FullTimeEmployee) employee;
            return "FullTimeEmployee;" + quantity + ";" + fullTimeEmployee.getId() + ";" +
                    fullTimeEmployee.getName() + ";" + fullTimeEmployee.getSalary() + ";" +
                    fullTimeEmployee.getBonus();
        }

        if (employee instanceof RemoteEmployee) {
            RemoteEmployee remoteEmployee = (RemoteEmployee) employee;
            return "RemoteEmployee;" + quantity + ";" + remoteEmployee.getId() + ";" +
                    remoteEmployee.getName() + ";" + remoteEmployee.getSalary() + ";" +
                    remoteEmployee.getWorkCountry();
        }

        if (employee instanceof Manager) {
            Manager manager = (Manager) employee;
            return "Manager;" + quantity + ";" + manager.getId() + ";" +
                    manager.getName() + ";" + manager.getSalary() + ";" +
                    manager.getTeamSize();
        }

        return "Employee;" + quantity + ";" + employee.getId() + ";" +
                employee.getName() + ";" + employee.getSalary();
    }

    /**
     * Пошук за ідентифікатором.
     */
    private static ArrayList<Employee> searchById(ArrayList<Employee> employees, int id) {
        ArrayList<Employee> results = new ArrayList<Employee>();

        for (Employee employee : employees) {
            if (employee.getId() == id) {
                results.add(employee);
            }
        }

        return results;
    }

    /**
     * Пошук за ім'ям.
     */
    private static ArrayList<Employee> searchByName(ArrayList<Employee> employees, String name) {
        ArrayList<Employee> results = new ArrayList<Employee>();
        String searchValue = name.trim().toLowerCase();

        for (Employee employee : employees) {
            if (employee.getName().toLowerCase().contains(searchValue)) {
                results.add(employee);
            }
        }

        return results;
    }

    /**
     * Пошук за типом.
     */
    private static ArrayList<Employee> searchByType(ArrayList<Employee> employees, String type) {
        ArrayList<Employee> results = new ArrayList<Employee>();
        String searchValue = type.trim().toLowerCase();

        for (Employee employee : employees) {
            String className = employee.getClass().getSimpleName().toLowerCase();
            if (className.equals(searchValue)) {
                results.add(employee);
            }
        }

        return results;
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
    private static void createEmployee(Scanner scanner, Company company) {
        try {
            int id = readPositiveInt(scanner, "Введіть id: ");
            String name = readNonEmptyString(scanner, "Введіть ім'я: ");
            double salary = readNonNegativeDouble(scanner, "Введіть зарплату: ");
            int quantity = readPositiveInt(scanner, "Введіть кількість: ");

            Employee employee = new Employee(id, name, salary);
            company.addNewEmployee(employee, quantity);
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
            int id = readPositiveInt(scanner, "Введіть id: ");
            String name = readNonEmptyString(scanner, "Введіть ім'я: ");
            double salary = readNonNegativeDouble(scanner, "Введіть зарплату: ");
            int contractMonths = readPositiveInt(scanner, "Введіть тривалість контракту в місяцях: ");
            int quantity = readPositiveInt(scanner, "Введіть кількість: ");

            Employee employee = new ContractEmployee(id, name, salary, contractMonths);
            company.addNewEmployee(employee, quantity);
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
            int id = readPositiveInt(scanner, "Введіть id: ");
            String name = readNonEmptyString(scanner, "Введіть ім'я: ");
            double salary = readNonNegativeDouble(scanner, "Введіть зарплату: ");
            double bonus = readNonNegativeDouble(scanner, "Введіть бонус: ");
            int quantity = readPositiveInt(scanner, "Введіть кількість: ");

            Employee employee = new FullTimeEmployee(id, name, salary, bonus);
            company.addNewEmployee(employee, quantity);
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
            int id = readPositiveInt(scanner, "Введіть id: ");
            String name = readNonEmptyString(scanner, "Введіть ім'я: ");
            double salary = readNonNegativeDouble(scanner, "Введіть зарплату: ");
            String workCountry = readNonEmptyString(scanner, "Введіть країну дистанційної роботи: ");
            int quantity = readPositiveInt(scanner, "Введіть кількість: ");

            Employee employee = new RemoteEmployee(id, name, salary, workCountry);
            company.addNewEmployee(employee, quantity);
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
            int id = readPositiveInt(scanner, "Введіть id: ");
            String name = readNonEmptyString(scanner, "Введіть ім'я: ");
            double salary = readNonNegativeDouble(scanner, "Введіть зарплату: ");
            int teamSize = readPositiveInt(scanner, "Введіть кількість підлеглих: ");
            int quantity = readPositiveInt(scanner, "Введіть кількість: ");

            Employee employee = new Manager(id, name, salary, teamSize);
            company.addNewEmployee(employee, quantity);
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

        for (int i = 0; i < company.getEmployees().size(); i++) {
            Employee employee = company.getEmployees().get(i);
            int quantity = company.getQuantityByIndex(i);
            System.out.println(employee + ", quantity=" + quantity);
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
     * Створює об'єкт компанії на основі рядка з файлу.
     */
    private static Company parseCompany(String line) {
        String[] parts = line.split(";");

        if (parts.length != 3) {
            throw new IllegalArgumentException("Некоректна кількість полів для Company.");
        }

        String name = parts[1].trim();
        String address = parts[2].trim();

        return new Company(name, address);
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