import java.util.UUID;

/**
 * Інтерфейс, що задає контракт отримання UUID.
 */
public interface Identifiable {

    /**
     * Повертає UUID об'єкта.
     */
    UUID getUuid();
}