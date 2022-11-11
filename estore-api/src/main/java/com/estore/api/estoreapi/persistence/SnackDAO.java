package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.Snack;

/**
 * Defines the interface for Snack object persistence
 */
public interface SnackDAO {
    
    /**
     * Retrieves all {@linkplain Snack snacks}
     * 
     * @return An array of {@link Snack snack} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */

    Snack[] getSnacks() throws IOException;

    /**
     * Finds all {@linkplain Snack snack} whose name contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link Snack snack} whose nemes contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Snack[] findSnacks(String containsText) throws IOException;

    /**
     * Retrieves a {@linkplain Snack snack} with the given id
     * 
     * @param id The id of the {@link Snack snack} to get
     * 
     * @return a {@link Snack snack} object with the matching id
     * <br>
     * null if no {@link Snack snack} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Snack getSnack(int id) throws IOException;

    /**
     * Creates and saves a {@linkplain Snack snack}
     * 
     * @param snack {@linkplain Snack snack} object to be created and saved
     * <br>
     * The id of the snack object is ignored and a new uniqe id is assigned
     *
     * @return new {@link Snack snack} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Snack createSnack(Snack snack) throws IOException;
    
    /**
     * Updates and saves a {@linkplain Snack snack}
     * 
     * @param {@link Snack snack} object to be updated and saved
     * 
     * @return updated {@link Snack snack} if successful, null if
     * {@link Snack snack} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    // Snack updateSnack(Snack snack, String type, String value) throws IOException;
    Snack updateSnack(Snack snack) throws IOException;

    /**
     * Deletes a {@linkplain Snack snack} with the given id
     * 
     * @param id The id of the {@link Snack snack}
     * 
     * @return true if the {@link Snack snack} was deleted
     * <br>
     * false if snack with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteSnack(int id) throws IOException;

    /**
     * Updates a {@linkplain Snack snack} with a new rating
     * 
     * @param id The id of the {@link Snack snack}
     * 
     * @return average rating if the {@link Snack snack} was updated with new rating
     * <br>
     * -1 if snack with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    double rateSnack(int id, String username, int rating) throws IOException;
}
