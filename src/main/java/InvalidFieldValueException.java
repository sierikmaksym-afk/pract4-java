/**
 * Виняток для некоректних значень полів.
 */
public class InvalidFieldValueException extends IllegalArgumentException {

    public InvalidFieldValueException(String message) {
        super(message);
    }
}