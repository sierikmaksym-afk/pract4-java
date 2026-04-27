/**
 * Клас, що описує штатного робітника.
 */
public class FullTimeEmployee extends Employee {
    private double bonus;

    /**
     * Конструктор для створення штатного робітника.
     */
    public FullTimeEmployee(String name, double salary, double bonus) {
        super(name, salary);
        setBonus(bonus);
    }

    /**
     * Повертає бонус робітника.
     */
    public double getBonus() {
        return bonus;
    }

    /**
     * Встановлює бонус робітника.
     */
    public void setBonus(double bonus) {
        if (bonus < 0) {
            throw new InvalidFieldValueException("Бонус робітника не може бути від'ємним.");
        }
        this.bonus = bonus;
    }

    /**
     * Повертає рядкове представлення об'єкта.
     */
    @Override
    public String toString() {
        return "FullTimeEmployee{" +
                "uuid=" + getUuid() +
                ", name='" + getName() + '\'' +
                ", salary=" + getSalary() +
                ", bonus=" + bonus +
                '}';
    }
}