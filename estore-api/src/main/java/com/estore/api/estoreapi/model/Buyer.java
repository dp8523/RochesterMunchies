package com.estore.api.estoreapi.model;

import java.util.HashMap;

import com.estore.api.estoreapi.persistence.SnackDAO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Buyer {

    // Package private for tests
    static final String STRING_FORMAT = "Buyer [username=%s, cart=%s]";

    @JsonProperty("username") private String username;
    @JsonProperty("cart") private ShoppingCart cart;
    @JsonProperty("snackDAO") private SnackDAO snackDAO;

    /**
     * Create a Buyer with the given username
     * @param username The username of the snack
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Buyer(@JsonProperty("username") String username, @JsonProperty("snackDAO") SnackDAO snackDAO){
        this.username = username;
        this.cart = new ShoppingCart();
        this.snackDAO = snackDAO;
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

    public ShoppingCart getUserCart() {
        return cart;
    }

    public void addToCart(@JsonProperty("id") int snackID) {
        cart.addItemToCart(snackID);
    }

    public boolean setCart(ShoppingCart newCart) {
        try {
            cart = newCart;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkoutCart() {
        return cart.clearCart();
    }

    public double getCartCost() {
        double cartTotal = 0;
        for(int snackId : cart.keySet()) {
            double snackPrice = snackId;
            int quantity = cart.get(snackId);
            double snackTotal = snackPrice * quantity;
            cartTotal += snackTotal;
        }
        return cartTotal;
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
        result = prime * result + ((username == null) ? 0 : username.hashCode());
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
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }
}
