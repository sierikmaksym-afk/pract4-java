import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тести для класу Company.
 */
class CompanyTests {

    /**
     * Перевіряє, що видалення неіснуючого робітника кидає власний виняток.
     */
    @Test
    void shouldThrowEmployeeNotFoundExceptionWhenDeletingNonExistingEmployee() {
        Company company = new Company("Test Company", "Test Address");
        Employee employee = new Employee("Ivan", 1000.0);

        assertThrows(EmployeeNotFoundException.class, () -> {
            company.delete(employee);
        });
    }

    /**
     * Перевіряє, що оновлення неіснуючого робітника кидає власний виняток.
     */
    @Test
    void shouldThrowEmployeeNotFoundExceptionWhenUpdatingNonExistingEmployee() {
        Company company = new Company("Test Company", "Test Address");
        Employee existingEmployee = new Employee("Ivan", 1000.0);
        Employee newEmployee = new Employee("Petro", 1500.0);

        assertThrows(EmployeeNotFoundException.class, () -> {
            company.update(existingEmployee, newEmployee);
        });
    }
}