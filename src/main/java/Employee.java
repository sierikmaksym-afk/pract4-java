/**
 * Базовий клас, що описує працівника компанії.
 */
public class Employee {
    private int id;
    private String name;
    private double salary;

    /**
     * Конструктор для створення об'єкта працівника.
     */
    public Employee(int id, String name, double salary) {
        setId(id);
        setName(name);
        setSalary(salary);
    }

    /**
     * Конструктор копіювання.
     */
    public Employee(Employee other) {
        if (other == null) {
            throw new IllegalArgumentException("Об'єкт для копіювання не може бути null.");
        }

        this.id = other.id;
        this.name = other.name;
        this.salary = other.salary;
    }

    /**
     * Повертає ідентифікатор працівника.
     */
    public int getId() {
        return id;
    }

    /**
     * Встановлює ідентифікатор працівника.
     */
    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID повинен бути більше 0.");
        }
        this.id = id;
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
     * Повертає рядок для запису об'єкта у файл.
     */
    public String toFileString() {
        return "Employee;" + id + ";" + name + ";" + salary;
    }

    /**
     * Повертає рядкове представлення об'єкта.
     */
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
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

        return id == employee.id &&
                Double.compare(salary, employee.salary) == 0 &&
                name.equals(employee.name);
    }
}