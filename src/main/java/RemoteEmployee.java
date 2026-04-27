/**
 * Клас, що описує віддаленого робітника.
 */
public class RemoteEmployee extends Employee {
    private String workCountry;

    /**
     * Конструктор для створення віддаленого робітника.
     */
    public RemoteEmployee(String name, double salary, String workCountry) {
        super(name, salary);
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
            throw new InvalidFieldValueException("Країна дистанційної роботи не може бути порожньою.");
        }
        this.workCountry = workCountry.trim();
    }

    /**
     * Повертає рядкове представлення об'єкта.
     */
    @Override
    public String toString() {
        return "RemoteEmployee{" +
                "uuid=" + getUuid() +
                ", name='" + getName() + '\'' +
                ", salary=" + getSalary() +
                ", workCountry='" + workCountry + '\'' +
                '}';
    }
}