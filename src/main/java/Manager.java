/**
 * Клас, що описує менеджера.
 */
public class Manager extends Employee {
    private int teamSize;

    /**
     * Конструктор для створення менеджера.
     */
    public Manager(int id, String name, double salary, int teamSize) {
        super(id, name, salary);
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
            throw new IllegalArgumentException("Кількість підлеглих повинна бути більше 0.");
        }
        this.teamSize = teamSize;
    }

    /**
     * Повертає рядок для запису об'єкта у файл.
     */
    @Override
    public String toFileString() {
        return "Manager;" + getId() + ";" + getName() + ";" + getSalary() + ";" + teamSize;
    }

    /**
     * Повертає рядкове представлення об'єкта.
     */
    @Override
    public String toString() {
        return "Manager{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", salary=" + getSalary() +
                ", teamSize=" + teamSize +
                '}';
    }
}