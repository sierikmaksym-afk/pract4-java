import java.util.ArrayList;
import java.util.UUID;

/**
 * Клас, що описує компанію та зберігає колекцію робітників.
 */
public class Company {
    private String name;
    private String address;
    private ArrayList<Employee> employees;

    /**
     * Конструктор для створення об'єкта компанії.
     */
    public Company(String name, String address) {
        setName(name);
        setAddress(address);
        this.employees = new ArrayList<Employee>();
    }

    /**
     * Повертає назву компанії.
     */
    public String getName() {
        return name;
    }

    /**
     * Встановлює назву компанії.
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidFieldValueException("Назва компанії не може бути порожньою.");
        }
        this.name = name.trim();
    }

    /**
     * Повертає адресу компанії.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Встановлює адресу компанії.
     */
    public void setAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new InvalidFieldValueException("Адреса компанії не може бути порожньою.");
        }
        this.address = address.trim();
    }

    /**
     * Повертає колекцію робітників компанії.
     */
    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    /**
     * Додає нового робітника у колекцію.
     */
    public void addNewEmployee(Employee emp) {
        if (emp == null) {
            throw new InvalidFieldValueException("Робітник не може бути null.");
        }

        if (findEmployeeIndex(emp) != -1) {
            throw new InvalidFieldValueException("Такий робітник уже існує в колекції.");
        }

        employees.add(emp);
    }

    /**
     * Оновлює дані існуючого робітника.
     */
    public boolean update(Employee existingObject, Employee newObject) {
        if (existingObject == null || newObject == null) {
            throw new InvalidFieldValueException("Робітник для оновлення не може бути null.");
        }

        int index = findEmployeeIndex(existingObject);

        if (index == -1) {
            throw new EmployeeNotFoundException("Робітника для оновлення не знайдено.");
        }

        employees.set(index, newObject);
        return true;
    }

    /**
     * Видаляє існуючого робітника з колекції.
     */
    public boolean delete(Employee existingObject) {
        if (existingObject == null) {
            throw new InvalidFieldValueException("Робітник для видалення не може бути null.");
        }

        int index = findEmployeeIndex(existingObject);

        if (index == -1) {
            throw new EmployeeNotFoundException("Робітника для видалення не знайдено.");
        }

        employees.remove(index);
        return true;
    }

    /**
     * Повертає всі об'єкти, що відповідають вказаному UUID.
     */
    public ArrayList<Employee> searchByUuid(UUID uuid) {
        ArrayList<Employee> results = new ArrayList<Employee>();

        if (uuid == null) {
            throw new InvalidFieldValueException("UUID не може бути null.");
        }

        for (Employee employee : employees) {
            if (employee.getUuid().equals(uuid)) {
                results.add(employee);
            }
        }

        return results;
    }

    /**
     * Повертає всі об'єкти, ім'я яких відповідає заданому значенню.
     */
    public ArrayList<Employee> searchByName(String name) {
        ArrayList<Employee> results = new ArrayList<Employee>();

        if (name == null || name.trim().isEmpty()) {
            throw new InvalidFieldValueException("Ім'я для пошуку не може бути порожнім.");
        }

        String searchValue = name.trim().toLowerCase();

        for (Employee employee : employees) {
            if (employee.getName().toLowerCase().contains(searchValue)) {
                results.add(employee);
            }
        }

        return results;
    }

    /**
     * Повертає всі об'єкти, тип яких відповідає заданому значенню.
     */
    public ArrayList<Employee> searchByType(String type) {
        ArrayList<Employee> results = new ArrayList<Employee>();

        if (type == null || type.trim().isEmpty()) {
            throw new InvalidFieldValueException("Тип для пошуку не може бути порожнім.");
        }

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
     * Повертає індекс робітника в колекції або -1, якщо робітника не знайдено.
     */
    private int findEmployeeIndex(Employee employee) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).equals(employee)) {
                return i;
            }
        }

        return -1;
    }
}