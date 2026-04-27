import java.util.UUID;

/**
 * Базовий клас, що описує працівника компанії.
 */
public class Employee implements Identifiable {
    private UUID uuid;
    private String name;
    private double salary;

    /**
     * Конструктор для створення об'єкта працівника.
     */
    public Employee(String name, double salary) {
        this.uuid = UUID.randomUUID();
        setName(name);
        setSalary(salary);
    }

    /**
     * Повертає UUID працівника.
     */
    @Override
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Повертає ім'я працівника.
     */
    public String getName() {
        return name;
    }

    /**
     * Встановлює ім'я працівника.
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Ім'я не може бути порожнім.");
        }
        this.name = name.trim();
    }

    /**
     * Повертає зарплату працівника.
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Встановлює зарплату працівника.
     */
    public void setSalary(double salary) {
        if (salary < 0) {
            throw new IllegalArgumentException("Зарплата не може бути від'ємною.");
        }
        this.salary = salary;
    }

    /**
     * Повертає рядкове представлення об'єкта.
     */
    @Override
    public String toString() {
        return "Employee{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }

    /**
     * Порівнює поточний об'єкт з іншим об'єктом.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Employee employee = (Employee) obj;

        return uuid.equals(employee.uuid);
    }
}