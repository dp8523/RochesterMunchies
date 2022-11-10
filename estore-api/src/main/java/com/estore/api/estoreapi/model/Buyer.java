package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

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
     * @param username The username of the Buyer
     */
    public void setUsername(String username) {this.username = username;}

    /**
     * Retrieves the username of the Buyer
     * @return The username of the Buyer
     */
    public String getUsername() {return username;}

    public void setCart(ShoppingCart newCart) {
        cart = newCart;
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public void addToCart(@JsonProperty("id") int snackID) {
        cart.addToCart(snackID);
    }

    public void deleteFromCart(@JsonProperty("id") int snackID) {
        cart.deleteFromCart(snackID);
    }

    public boolean clearCart() {
        return cart.clearCart();
    }

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
