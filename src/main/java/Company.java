import java.util.ArrayList;

/**
 * Клас, що описує компанію та зберігає колекцію працівників.
 */
public class Company {
    private String name;
    private String address;
    private ArrayList<Employee> employees;
    private ArrayList<Integer> quantities;

    /**
     * Конструктор для створення об'єкта компанії.
     */
    public Company(String name, String address) {
        setName(name);
        setAddress(address);
        this.employees = new ArrayList<Employee>();
        this.quantities = new ArrayList<Integer>();
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
     * Повертає колекцію кількостей для працівників.
     */
    public ArrayList<Integer> getQuantities() {
        return quantities;
    }

    /**
     * Додає нового працівника у колекцію або збільшує кількість, якщо він уже існує.
     */
    public void addNewEmployee(Employee emp, int quantity) {
        if (emp == null) {
            throw new IllegalArgumentException("Працівник не може бути null.");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Кількість повинна бути більше 0.");
        }

        int index = findEmployeeIndex(emp);

        if (index >= 0) {
            int currentQuantity = quantities.get(index);
            quantities.set(index, currentQuantity + quantity);
        } else {
            employees.add(emp);
            quantities.add(quantity);
        }
    }

    /**
     * Повертає всі об'єкти, що відповідають вказаному id.
     */
    public ArrayList<Employee> searchById(int id) {
        ArrayList<Employee> results = new ArrayList<Employee>();

        for (Employee employee : employees) {
            if (employee.getId() == id) {
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
     * Повертає кількість для працівника за його індексом у колекції.
     */
    public int getQuantityByIndex(int index) {
        return quantities.get(index);
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