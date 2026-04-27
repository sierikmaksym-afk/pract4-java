/**
 * Виняток для ситуацій, коли робітника не знайдено.
 */
public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(String message) {
        super(message);
    }
}