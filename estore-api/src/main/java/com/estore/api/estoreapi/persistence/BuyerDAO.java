package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.Buyer;

/**
 * Defines the interface for Buyer object persistence
 */
public interface BuyerDAO {

    /**
     * Checks if a {@linkplain Buyer buyer} account exists
     * 
     * @param username The username of the {@link Buyer buyer} to check
     * 
     * @return true if a {@link Buyer buyer} object with the given username exists
     * <br>
     * false otherwise
     * 
     * @throws IOException if an issue with underlying storage
     */
    Buyer login(String username) throws IOException;

    /**
     * Creates and saves a {@linkplain Buyer buyer}
     * 
     * @param username The username of the {@link Buyer buyer} to create
     * <br>
     *
     * @return new {@link Buyer buyer} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Buyer createBuyer(String username) throws IOException;

    /**
     * Deletes a {@linkplain Buyer buyer} with the given username
     * 
     * @param username The username of the {@link Buyer buyer} to be deleted
     * 
     * @return true if the {@link Buyer buyer} was deleted
     * <br>
     * false if buyer with the given username does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteBuyer(String username) throws IOException;
}
