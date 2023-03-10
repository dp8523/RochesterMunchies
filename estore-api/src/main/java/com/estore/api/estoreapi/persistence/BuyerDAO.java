package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.Buyer;

/**
 * Defines the interface for Buyer object persistence
 */
public interface BuyerDAO {

    /**
     * Checks if a {@linkplain Buyer buyer} account exists and returns the buyer if so
     * 
     * @param username The username of the {@link Buyer buyer} to check
     * 
     * @return the {@link Buyer buyer} object with the given username if it exists
     * <br>
     * null otherwise
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
     * false if buyer with the given username does not exist or if attempting to delete admin
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteBuyer(String username) throws IOException;

    /**
     * Adds a {@linkplain Snack snack} with the given snackID to the cart of a {@linkplain Buyer buyer}
     * 
     * @param username The username of the {@link Buyer buyer}
     * 
     * @param snackID The snackID of the {@link Snack snack}
     * 
     * @return the {@link Buyer buyer} with the updated cart
     * <br>
     * false if buyer with the given username does not exist or if attempting to delete admin
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Buyer addToCart(String username, int snackID) throws IOException;
    
    /**
     * Deletes a {@linkplain Snack snack} with the given snackID from the cart of a {@linkplain Buyer buyer}
     * 
     * @param username The username of the {@link Buyer buyer}
     * 
     * @param snackID The snackID of the {@link Snack snack}
     * 
     * @return the {@link Buyer buyer} with the updated cart
     * <br>
     * false if buyer with the given username does not exist or if attempting to delete admin
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Buyer deleteFromCart(String username, int snackID) throws IOException;

    /**
     * Empties the {@linkplain ShoppingCart cart} of the {@linkplain Buyer buyer} with the given username
     * 
     * @param username The username of the {@link Buyer buyer}
     * 
     * @return the new {@link Buyer buyer} after the shopping cart was emptied
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    public Buyer clearCart(String username) throws IOException;
}
