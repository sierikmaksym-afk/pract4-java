/**
 * Клас, що описує віддаленого працівника.
 */
public class RemoteEmployee extends Employee {
    private String workCountry;

    /**
     * Конструктор для створення віддаленого працівника.
     */
    public RemoteEmployee(int id, String name, double salary, String workCountry) {
        super(id, name, salary);
        setWorkCountry(workCountry);
    }

    /**
     * Повертає країну дистанційної роботи.
     */
    public String getWorkCountry() {
        return workCountry;
    }

    /**
     * Встановлює країну дистанційної роботи.
     */
    public void setWorkCountry(String workCountry) {
        if (workCountry == null || workCountry.trim().isEmpty()) {
            throw new IllegalArgumentException("Країна дистанційної роботи не може бути порожньою.");
        }
        this.workCountry = workCountry.trim();
    }

    /**
     * Повертає рядкове представлення об'єкта.
     */
    @Override
    public String toString() {
        return "RemoteEmployee{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", salary=" + getSalary() +
                ", workCountry='" + workCountry + '\'' +
                '}';
    }
}