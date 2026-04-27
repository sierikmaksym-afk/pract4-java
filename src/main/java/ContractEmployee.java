/**
 * Клас, що описує контрактного працівника.
 */
public class ContractEmployee extends Employee {
    private int contractMonths;

    /**
     * Конструктор для створення контрактного працівника.
     */
    public ContractEmployee(String name, double salary, int contractMonths) {
        super(name, salary);
        setContractMonths(contractMonths);
    }

    /**
     * Повертає тривалість контракту в місяцях.
     */
    public int getContractMonths() {
        return contractMonths;
    }

    /**
     * Встановлює тривалість контракту в місяцях.
     */
    public void setContractMonths(int contractMonths) {
        if (contractMonths <= 0) {
            throw new IllegalArgumentException("Тривалість контракту повинна бути більше 0.");
        }
        this.contractMonths = contractMonths;
    }

    /**
     * Повертає рядкове представлення об'єкта.
     */
    @Override
    public String toString() {
        return "ContractEmployee{" +
                "uuid=" + getUuid() +
                ", name='" + getName() + '\'' +
                ", salary=" + getSalary() +
                ", contractMonths=" + contractMonths +
                '}';
    }
}