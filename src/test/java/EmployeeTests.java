import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тести для класу Employee.
 */
class EmployeeTest {

    @Test
    void shouldThrowExceptionWhenInvalidValueInSetter() {
        Employee obj = new Employee(1, "Employee", 1000.0);

        assertThrows(IllegalArgumentException.class, () -> {
            obj.setSalary(-1);
        });
    }

    @Test
    void shouldThrowExceptionWhenInvalidConstructorData() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee(0, "", -5);
        });
    }
}