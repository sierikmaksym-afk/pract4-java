import java.util.ArrayList;
import java.util.UUID;

/**
 * Клас, що описує компанію та зберігає колекцію працівників.
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
            throw new IllegalArgumentException("Назва компанії не може бути порожньою.");
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
            throw new IllegalArgumentException("Адреса компанії не може бути порожньою.");
        }
        this.address = address.trim();
    }

    /**
     * Повертає колекцію працівників компанії.
     */
    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    /**
     * Додає нового працівника у колекцію.
     */
    public void addNewEmployee(Employee emp) {
        if (emp == null) {
            throw new IllegalArgumentException("Працівник не може бути null.");
        }

        if (findEmployeeIndex(emp) == -1) {
            employees.add(emp);
        }
    }

    /**
     * Повертає всі об'єкти, що відповідають вказаному UUID.
     */
    public ArrayList<Employee> searchByUuid(UUID uuid) {
        ArrayList<Employee> results = new ArrayList<Employee>();

        if (uuid == null) {
            return results;
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
        String searchValue = name.trim().toLowerCase();

        for (Employee employee : employees) {
            if (employee.getName().toLowerCase().contains(searchValue)) {
                results.add(employee);
            }
        }

        return results;
    }

    /**
     * Оновлює дані існуючого працівника.
     */
    public boolean update(Employee existingObject, Employee newObject) {
        if (existingObject == null || newObject == null) {
            return false;
        }

        int index = findEmployeeIndex(existingObject);

        if (index == -1) {
            return false;
        }

        employees.set(index, newObject);
        return true;
    }

    /**
     * Видаляє існуючого працівника з колекції.
     */
    public boolean delete(Employee existingObject) {
        if (existingObject == null) {
            return false;
        }

        int index = findEmployeeIndex(existingObject);

        if (index == -1) {
            return false;
        }

        employees.remove(index);
        return true;
    }


    /**
     * Повертає всі об'єкти, тип яких відповідає заданому значенню.
     */
    public ArrayList<Employee> searchByType(String type) {
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
     * Повертає індекс працівника в колекції або -1, якщо працівника не знайдено.
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