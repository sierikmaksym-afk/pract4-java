/**
 * Клас, що описує контрактного робітника.
 */
public class ContractEmployee extends Employee {
    private int contractMonths;

    /**
     * Конструктор для створення контрактного робітника.
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
            throw new InvalidFieldValueException("Тривалість контракту повинна бути більшою за 0.");
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