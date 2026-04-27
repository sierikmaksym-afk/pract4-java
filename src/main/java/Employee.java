import java.util.UUID;

/**
 * Базовий клас, що описує робітника компанії.
 */
public class Employee implements Identifiable {
    private UUID uuid;
    private String name;
    private double salary;

    /**
     * Конструктор для створення об'єкта робітника.
     */
    public Employee(String name, double salary) {
        this.uuid = UUID.randomUUID();
        setName(name);
        setSalary(salary);
    }

    /**
     * Повертає UUID робітника.
     */
    @Override
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Повертає ім'я робітника.
     */
    public String getName() {
        return name;
    }

    /**
     * Встановлює ім'я робітника.
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidFieldValueException("Ім'я робітника не може бути порожнім.");
        }
        this.name = name.trim();
    }

    /**
     * Повертає зарплату робітника.
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Встановлює зарплату робітника.
     */
    public void setSalary(double salary) {
        if (salary < 0) {
            throw new InvalidFieldValueException("Зарплата робітника не може бути від'ємною.");
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

    /**
     * Повертає хеш-код об'єкта.
     */
    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}