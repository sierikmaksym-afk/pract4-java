/**
 * Клас, що описує штатного працівника.
 */
public class FullTimeEmployee extends Employee {
    private double bonus;

    /**
     * Конструктор для створення штатного працівника.
     */
    public FullTimeEmployee(String name, double salary, double bonus) {
        super(name, salary);
        setBonus(bonus);
    }

    /**
     * Повертає бонус працівника.
     */
    public double getBonus() {
        return bonus;
    }

    /**
     * Встановлює бонус працівника.
     */
    public void setBonus(double bonus) {
        if (bonus < 0) {
            throw new IllegalArgumentException("Бонус не може бути від'ємним.");
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