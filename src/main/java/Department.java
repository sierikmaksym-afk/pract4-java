import java.util.ArrayList;
import java.util.List;

/**
 * Клас, що демонструє агрегацію працівників.
 */
public class Department {
    private String departmentName;
    private List<Employee> employees;

    /**
     * Конструктор класу Department.
     */
    public Department(String departmentName) {
        if (departmentName == null || departmentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Назва відділу не може бути порожньою.");
        }

        this.departmentName = departmentName.trim();
        this.employees = new ArrayList<Employee>();
    }

    /**
     * Додає працівника до відділу.
     */
    public void addEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Працівник не може бути null.");
        }

        employees.add(employee);
    }

    /**
     * Повертає назву відділу.
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * Повертає список працівників відділу.
     */
    public List<Employee> getEmployees() {
        return employees;
    }
}