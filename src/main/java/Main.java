import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.UUID;

/**
 * Головний клас програми для роботи з робітниками через консольне меню.
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
                    createEmployeeMenu(scanner, company);
                    break;
                case 2:
                    printAllEmployees(company);
                    break;
                case 3:
                    updateEmployeeMenu(scanner, company);
                    break;
                case 4:
                    deleteEmployeeMenu(scanner, company);
                    break;
                case 5:
                    searchEmployeeMenu(scanner, company);
                    break;
                case 6:
                    sortEmployeesMenu(scanner, company);
                    break;
                case 7:
                    System.out.println("Роботу програми завершено.");
                    running = false;
                    break;
                default:
                    System.out.println("Помилка: оберіть пункт від 1 до 7.");
            }
        }

        scanner.close();
    }

    /**
     * Виводить головне меню програми.
     */
    private static void printMainMenu() {
        System.out.println("\nГоловне меню:");
        System.out.println("1. Створити нового робітника");
        System.out.println("2. Вивести інформацію про всіх робітників");
        System.out.println("3. Модифікувати робітника");
        System.out.println("4. Видалити робітника");
        System.out.println("5. Пошук робітника");
        System.out.println("6. Вивести відсортовану інформацію про всіх робітників");
        System.out.println("7. Завершити роботу програми");
    }

    /**
     * Виводить підменю створення робітників.
     */
    private static void createEmployeeMenu(Scanner scanner, Company company) {
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
     * Виводить меню вибору типу робітника.
     */
    private static void printCreateMenu() {
        System.out.println("\nОберіть тип робітника для створення:");
        System.out.println("1. Employee");
        System.out.println("2. ContractEmployee");
        System.out.println("3. FullTimeEmployee");
        System.out.println("4. RemoteEmployee");
        System.out.println("5. Manager");
        System.out.println("0. Повернутися до головного меню");
    }

    /**
     * Створює робітника базового класу Employee.
     */
    private static void createEmployee(Scanner scanner, Company company) {
        try {
            String name = readNonEmptyString(scanner, "Введіть ім'я: ");
            double salary = readNonNegativeDouble(scanner, "Введіть зарплату: ");

            Employee employee = new Employee(name, salary);
            company.addNewEmployee(employee);
            System.out.println("Робітника Employee успішно додано.");
        } catch (InvalidFieldValueException e) {
            System.out.println("Помилка створення робітника: " + e.getMessage());
        }
    }

    /**
     * Створює робітника класу ContractEmployee.
     */
    private static void createContractEmployee(Scanner scanner, Company company) {
        try {
            String name = readNonEmptyString(scanner, "Введіть ім'я: ");
            double salary = readNonNegativeDouble(scanner, "Введіть зарплату: ");
            int contractMonths = readPositiveInt(scanner, "Введіть тривалість контракту в місяцях: ");

            Employee employee = new ContractEmployee(name, salary, contractMonths);
            company.addNewEmployee(employee);
            System.out.println("Робітника ContractEmployee успішно додано.");
        } catch (InvalidFieldValueException e) {
            System.out.println("Помилка створення робітника: " + e.getMessage());
        }
    }

    /**
     * Створює робітника класу FullTimeEmployee.
     */
    private static void createFullTimeEmployee(Scanner scanner, Company company) {
        try {
            String name = readNonEmptyString(scanner, "Введіть ім'я: ");
            double salary = readNonNegativeDouble(scanner, "Введіть зарплату: ");
            double bonus = readNonNegativeDouble(scanner, "Введіть бонус: ");

            Employee employee = new FullTimeEmployee(name, salary, bonus);
            company.addNewEmployee(employee);
            System.out.println("Робітника FullTimeEmployee успішно додано.");
        } catch (InvalidFieldValueException e) {
            System.out.println("Помилка створення робітника: " + e.getMessage());
        }
    }

    /**
     * Створює робітника класу RemoteEmployee.
     */
    private static void createRemoteEmployee(Scanner scanner, Company company) {
        try {
            String name = readNonEmptyString(scanner, "Введіть ім'я: ");
            double salary = readNonNegativeDouble(scanner, "Введіть зарплату: ");
            String workCountry = readNonEmptyString(scanner, "Введіть країну дистанційної роботи: ");

            Employee employee = new RemoteEmployee(name, salary, workCountry);
            company.addNewEmployee(employee);
            System.out.println("Робітника RemoteEmployee успішно додано.");
        } catch (InvalidFieldValueException e) {
            System.out.println("Помилка створення робітника: " + e.getMessage());
        }
    }

    /**
     * Створює робітника класу Manager.
     */
    private static void createManager(Scanner scanner, Company company) {
        try {
            String name = readNonEmptyString(scanner, "Введіть ім'я: ");
            double salary = readNonNegativeDouble(scanner, "Введіть зарплату: ");
            int teamSize = readPositiveInt(scanner, "Введіть кількість підлеглих: ");

            Employee employee = new Manager(name, salary, teamSize);
            company.addNewEmployee(employee);
            System.out.println("Робітника Manager успішно додано.");
        } catch (InvalidFieldValueException e) {
            System.out.println("Помилка створення робітника: " + e.getMessage());
        }
    }

    /**
     * Виводить інформацію про всіх створених робітників.
     */
    private static void printAllEmployees(Company company) {
        if (company.getEmployees().isEmpty()) {
            System.out.println("Список робітників порожній.");
            return;
        }

        System.out.println("\nКомпанія: " + company.getName());
        System.out.println("Адреса: " + company.getAddress());
        System.out.println("Інформація про всіх робітників:");

        for (Employee employee : company.getEmployees()) {
            System.out.println(employee);
        }
    }

    /**
     * Виводить меню модифікації робітника.
     */
    private static void updateEmployeeMenu(Scanner scanner, Company company) {
        if (company.getEmployees().isEmpty()) {
            System.out.println("Список робітників порожній.");
            return;
        }

        printAllEmployees(company);

        UUID uuid = readUuid(scanner, "Введіть UUID робітника, якого потрібно змінити: ");

        try {
            ArrayList<Employee> found = company.searchByUuid(uuid);

            if (found.isEmpty()) {
                throw new EmployeeNotFoundException("Робітника не знайдено.");
            }

            Employee existingEmployee = found.get(0);
            Employee updatedEmployee = createUpdatedEmployee(scanner, existingEmployee);

            if (updatedEmployee == null) {
                System.out.println("Модифікацію скасовано.");
                return;
            }

            company.update(existingEmployee, updatedEmployee);
            System.out.println("Робітника успішно оновлено.");
        } catch (InvalidFieldValueException | EmployeeNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Створює нового робітника на основі існуючого з одним зміненим атрибутом.
     */
    private static Employee createUpdatedEmployee(Scanner scanner, Employee employee) {
        if (employee instanceof ContractEmployee) {
            return updateContractEmployee(scanner, (ContractEmployee) employee);
        }

        if (employee instanceof FullTimeEmployee) {
            return updateFullTimeEmployee(scanner, (FullTimeEmployee) employee);
        }

        if (employee instanceof RemoteEmployee) {
            return updateRemoteEmployee(scanner, (RemoteEmployee) employee);
        }

        if (employee instanceof Manager) {
            return updateManager(scanner, (Manager) employee);
        }

        return updateBaseEmployee(scanner, employee);
    }

    /**
     * Модифікує робітника базового класу Employee.
     */
    private static Employee updateBaseEmployee(Scanner scanner, Employee employee) {
        System.out.println("\nОберіть атрибут для зміни:");
        System.out.println("1. Ім'я");
        System.out.println("2. Зарплата");
        System.out.println("0. Скасувати");

        int choice = readMenuChoice(scanner);

        if (choice == 0) {
            return null;
        }

        String newName = employee.getName();
        double newSalary = employee.getSalary();

        switch (choice) {
            case 1:
                newName = readNonEmptyString(scanner, "Введіть нове ім'я: ");
                break;
            case 2:
                newSalary = readNonNegativeDouble(scanner, "Введіть нову зарплату: ");
                break;
            default:
                System.out.println("Помилка: оберіть пункт від 0 до 2.");
                return null;
        }

        return new Employee(newName, newSalary);
    }

    /**
     * Модифікує робітника класу ContractEmployee.
     */
    private static Employee updateContractEmployee(Scanner scanner, ContractEmployee employee) {
        System.out.println("\nОберіть атрибут для зміни:");
        System.out.println("1. Ім'я");
        System.out.println("2. Зарплата");
        System.out.println("3. Тривалість контракту");
        System.out.println("0. Скасувати");

        int choice = readMenuChoice(scanner);

        if (choice == 0) {
            return null;
        }

        String newName = employee.getName();
        double newSalary = employee.getSalary();
        int newContractMonths = employee.getContractMonths();

        switch (choice) {
            case 1:
                newName = readNonEmptyString(scanner, "Введіть нове ім'я: ");
                break;
            case 2:
                newSalary = readNonNegativeDouble(scanner, "Введіть нову зарплату: ");
                break;
            case 3:
                newContractMonths = readPositiveInt(scanner, "Введіть нову тривалість контракту: ");
                break;
            default:
                System.out.println("Помилка: оберіть пункт від 0 до 3.");
                return null;
        }

        return new ContractEmployee(newName, newSalary, newContractMonths);
    }

    /**
     * Модифікує робітника класу FullTimeEmployee.
     */
    private static Employee updateFullTimeEmployee(Scanner scanner, FullTimeEmployee employee) {
        System.out.println("\nОберіть атрибут для зміни:");
        System.out.println("1. Ім'я");
        System.out.println("2. Зарплата");
        System.out.println("3. Бонус");
        System.out.println("0. Скасувати");

        int choice = readMenuChoice(scanner);

        if (choice == 0) {
            return null;
        }

        String newName = employee.getName();
        double newSalary = employee.getSalary();
        double newBonus = employee.getBonus();

        switch (choice) {
            case 1:
                newName = readNonEmptyString(scanner, "Введіть нове ім'я: ");
                break;
            case 2:
                newSalary = readNonNegativeDouble(scanner, "Введіть нову зарплату: ");
                break;
            case 3:
                newBonus = readNonNegativeDouble(scanner, "Введіть новий бонус: ");
                break;
            default:
                System.out.println("Помилка: оберіть пункт від 0 до 3.");
                return null;
        }

        return new FullTimeEmployee(newName, newSalary, newBonus);
    }

    /**
     * Модифікує робітника класу RemoteEmployee.
     */
    private static Employee updateRemoteEmployee(Scanner scanner, RemoteEmployee employee) {
        System.out.println("\nОберіть атрибут для зміни:");
        System.out.println("1. Ім'я");
        System.out.println("2. Зарплата");
        System.out.println("3. Країна дистанційної роботи");
        System.out.println("0. Скасувати");

        int choice = readMenuChoice(scanner);

        if (choice == 0) {
            return null;
        }

        String newName = employee.getName();
        double newSalary = employee.getSalary();
        String newWorkCountry = employee.getWorkCountry();

        switch (choice) {
            case 1:
                newName = readNonEmptyString(scanner, "Введіть нове ім'я: ");
                break;
            case 2:
                newSalary = readNonNegativeDouble(scanner, "Введіть нову зарплату: ");
                break;
            case 3:
                newWorkCountry = readNonEmptyString(scanner, "Введіть нову країну дистанційної роботи: ");
                break;
            default:
                System.out.println("Помилка: оберіть пункт від 0 до 3.");
                return null;
        }

        return new RemoteEmployee(newName, newSalary, newWorkCountry);
    }

    /**
     * Модифікує робітника класу Manager.
     */
    private static Employee updateManager(Scanner scanner, Manager employee) {
        System.out.println("\nОберіть атрибут для зміни:");
        System.out.println("1. Ім'я");
        System.out.println("2. Зарплата");
        System.out.println("3. Кількість підлеглих");
        System.out.println("0. Скасувати");

        int choice = readMenuChoice(scanner);

        if (choice == 0) {
            return null;
        }

        String newName = employee.getName();
        double newSalary = employee.getSalary();
        int newTeamSize = employee.getTeamSize();

        switch (choice) {
            case 1:
                newName = readNonEmptyString(scanner, "Введіть нове ім'я: ");
                break;
            case 2:
                newSalary = readNonNegativeDouble(scanner, "Введіть нову зарплату: ");
                break;
            case 3:
                newTeamSize = readPositiveInt(scanner, "Введіть нову кількість підлеглих: ");
                break;
            default:
                System.out.println("Помилка: оберіть пункт від 0 до 3.");
                return null;
        }

        return new Manager(newName, newSalary, newTeamSize);
    }

    /**
     * Виводить меню видалення робітника.
     */
    private static void deleteEmployeeMenu(Scanner scanner, Company company) {
        if (company.getEmployees().isEmpty()) {
            System.out.println("Список робітників порожній.");
            return;
        }

        printAllEmployees(company);

        UUID uuid = readUuid(scanner, "Введіть UUID робітника, якого потрібно видалити: ");

        try {
            ArrayList<Employee> found = company.searchByUuid(uuid);

            if (found.isEmpty()) {
                throw new EmployeeNotFoundException("Робітника не знайдено.");
            }

            company.delete(found.get(0));
            System.out.println("Робітника успішно видалено.");
        } catch (InvalidFieldValueException | EmployeeNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Виводить підменю пошуку робітника.
     */
    private static void printSearchMenu() {
        System.out.println("\nПошук робітника:");
        System.out.println("1. Пошук за UUID");
        System.out.println("2. Пошук за ім'ям");
        System.out.println("3. Пошук за типом робітника");
        System.out.println("0. Повернутися до головного меню");
    }

    /**
     * Виводить підменю пошуку та виконує пошук робітника за вибраним критерієм.
     */
    private static void searchEmployeeMenu(Scanner scanner, Company company) {
        boolean inSearchMenu = true;

        while (inSearchMenu) {
            printSearchMenu();
            int choice = readMenuChoice(scanner);

            try {
                switch (choice) {
                    case 1: {
                        UUID uuid = readUuid(scanner, "Введіть UUID для пошуку робітника: ");
                        ArrayList<Employee> results = company.searchByUuid(uuid);
                        printSearchResults(results);
                        break;
                    }
                    case 2: {
                        String name = readNonEmptyString(scanner, "Введіть ім'я для пошуку робітника: ");
                        ArrayList<Employee> results = company.searchByName(name);
                        printSearchResults(results);
                        break;
                    }
                    case 3: {
                        String type = readNonEmptyString(scanner, "Введіть тип робітника для пошуку: ");
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
            } catch (InvalidFieldValueException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Виводить результат пошуку робітника.
     */
    private static void printSearchResults(ArrayList<Employee> results) {
        if (results.isEmpty()) {
            System.out.println("Жодного робітника не знайдено.");
            return;
        }

        System.out.println("\nРезультати пошуку:");
        for (Employee employee : results) {
            System.out.println(employee);
        }
    }

    /**
     * Виводить підменю сортування робітників.
     */
    private static void printSortMenu() {
        System.out.println("\nОберіть критерій сортування:");
        System.out.println("1. Сортувати за ім'ям");
        System.out.println("2. Сортувати за зарплатою");
        System.out.println("3. Сортувати за UUID");
        System.out.println("0. Повернутися в головне меню");
    }

    /**
     * Виводить підменю сортування та виконує сортування робітників за вибраним критерієм.
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
     * Сортує список робітників за ім'ям та виводить результат.
     */
    private static void sortByName(Company company) {
        if (company.getEmployees().isEmpty()) {
            System.out.println("Список робітників порожній.");
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
     * Сортує список робітників за зарплатою та виводить результат.
     */
    private static void sortBySalary(Company company) {
        if (company.getEmployees().isEmpty()) {
            System.out.println("Список робітників порожній.");
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
     * Сортує список робітників за UUID та виводить результат.
     */
    private static void sortByUuid(Company company) {
        if (company.getEmployees().isEmpty()) {
            System.out.println("Список робітників порожній.");
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