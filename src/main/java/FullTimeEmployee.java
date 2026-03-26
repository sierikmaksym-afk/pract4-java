/**
 * Клас, що описує штатного працівника.
 */
public class FullTimeEmployee extends Employee {
    private double bonus;

    /**
     * Конструктор для створення штатного працівника.
     */
    public FullTimeEmployee(int id, String name, double salary, double bonus) {
        super(id, name, salary);
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
     * Повертає рядок для запису об'єкта у файл.
     */
    @Override
    public String toFileString() {
        return "FullTimeEmployee;" + getId() + ";" + getName() + ";" + getSalary() + ";" + bonus;
    }

    /**
     * Повертає рядкове представлення об'єкта.
     */
    @Override
    public String toString() {
        return "FullTimeEmployee{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", salary=" + getSalary() +
                ", bonus=" + bonus +
                '}';
    }
}