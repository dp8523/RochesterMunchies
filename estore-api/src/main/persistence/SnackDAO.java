import java.io.IOException;

/**
 * Defines the interface for Hero object persistence
 */
public interface SnackDAO {
    
    /**
     * Retrieves all {@linkplain Hero heroes}
     * 
     * @return An array of {@link Hero hero} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */

    Snack[] getSnacks() throws IOException;

    Snack[] findSnacks(String containsText) throws IOException;

    Snack getSnack(String name) throws IOException;

    Snack createSnack(Snack snack) throws IOException;
    
    Snack updateSnack(Snack snack) throws IOException;

    boolean deleteSnack(String name) throws IOException;
}
