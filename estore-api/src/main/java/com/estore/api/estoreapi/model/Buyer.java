package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Buyer class for objects containing a username and shopping cart. Represents
 * users on the estore page.
 */
public class Buyer {

    // Package private for tests
    static final String STRING_FORMAT = "Buyer [username=%s, cart=%s]";

    @JsonProperty("username") private String username;
    @JsonProperty("cart") private ShoppingCart cart;

    /**
     * Create a Buyer with the given username
     * @param username The username of the snack
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Buyer(@JsonProperty("username") String username){
        this.username = username;
        this.cart = new ShoppingCart();
    }

    /**
     * Sets the username of the Buyer
     * 
     * @param username The username of the Buyer
     */
    public void setUsername(String username) {this.username = username;}

    /**
     * Retrieves the username of the Buyer
     * 
     * @return The username of the Buyer
     */
    public String getUsername() {return username;}

    /**
     * Sets the cart of the Buyer
     * 
     * @param newCart The new ShoppingCart instance
     */
    public void setCart(ShoppingCart newCart) {
        cart = newCart;
    }

    /**
     * Retrieves the shopping cart of the Buyer
     * 
     * @return cart The ShoppingCart of the buyer
     */
    public ShoppingCart getCart() {
        return cart;
    }

    /**
     * Adds snacks to a buyer's cart
     * 
     * @param snackID
     */
    public void addToCart(@JsonProperty("id") int snackID) {
        cart.addToCart(snackID);
    }

    /**
     * Deletes snacks from a buyer's cart
     * 
     * @param snackID
     */
    public void deleteFromCart(@JsonProperty("id") int snackID) {
        cart.deleteFromCart(snackID);
    }

    /**
     * Removes all items in the current buyer's cart
     * 
     * @return Boolean value true always
     */
    public boolean clearCart() {
        return cart.clearCart();
    }

    /**
     * Detects if a particular snackID is found in the buyer's cart
     * 
     * @param snackID Snack id of snack being checked
     * @return true if snack was found, false otherwise
     */
    public boolean snackInCart(int snackID) {
        return cart.containsKey(snackID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString(){
        return String.format(STRING_FORMAT, username, cart);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        if (username == null) {
            result = prime * result + 0;
        }
        else {
            result = prime * result + username.hashCode();
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        
        Buyer other = (Buyer) obj;
        if (username == null) {
            // if (other.username != null) {
            return false;
            // }
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

}

