/**
 * Клас, що описує менеджера.
 */
public class Manager extends Employee {
    private int teamSize;

    /**
     * Конструктор для створення менеджера.
     */
    public Manager(String name, double salary, int teamSize) {
        super(name, salary);
        setTeamSize(teamSize);
    }

    /**
     * Повертає кількість підлеглих.
     */
    public int getTeamSize() {
        return teamSize;
    }

    /**
     * Встановлює кількість підлеглих.
     */
    public void setTeamSize(int teamSize) {
        if (teamSize <= 0) {
            throw new InvalidFieldValueException("Кількість підлеглих повинна бути більшою за 0.");
        }
        this.teamSize = teamSize;
    }

    /**
     * Повертає рядкове представлення об'єкта.
     */
    @Override
    public String toString() {
        return "Manager{" +
                "uuid=" + getUuid() +
                ", name='" + getName() + '\'' +
                ", salary=" + getSalary() +
                ", teamSize=" + teamSize +
                '}';
    }
}