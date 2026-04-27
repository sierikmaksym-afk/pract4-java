import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тести для класу Employee.
 */
class EmployeeTests {

    /**
     * Перевіряє, що сетер кидає власний виняток при некоректному значенні зарплати.
     */
    @Test
    void shouldThrowInvalidFieldValueExceptionWhenInvalidValueInSetter() {
        Employee obj = new Employee("Employee", 1000.0);

        assertThrows(InvalidFieldValueException.class, () -> {
            obj.setSalary(-1);
        });
    }

    /**
     * Перевіряє, що конструктор кидає власний виняток при некоректних даних.
     */
    @Test
    void shouldThrowInvalidFieldValueExceptionWhenInvalidConstructorData() {
        assertThrows(InvalidFieldValueException.class, () -> {
            new Employee("", -5);
        });
    }
}