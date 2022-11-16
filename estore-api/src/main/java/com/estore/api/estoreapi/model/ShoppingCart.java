package com.estore.api.estoreapi.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;

/**
 * ShoppingCart class that extends HashMap<Integer, Integer> for storage of
 * Snack items within Buyer objects. Represents the shopping carts of users
 * on the estore.
 */
public class ShoppingCart extends HashMap<Integer,Integer>{

    /**
     * Adds a snack to the Hashmap representing the cart of the parent buyer.
     * Increments the value of the key representing the snack id if the id already
     * exists within the cart.
     * 
     * @param id ID representing the snack's id
     * @return true if successfully added to the cart, false otherwise
     */
    public boolean addToCart(@JsonProperty("id") int id) {
        // Assume that id is valid snack
        if (this.containsKey(id)) {
            this.put(id, this.get(id) + 1);
        }
        else {
            this.put(id, 1);
        }
        return true;
    }

    /**
     * Deletes a snack from the Hashmap representing the cart of the parent
     * buyer. Decrements the value of the key representing the snack id if the 
     * id already exists within the cart.
     * 
     * @param id ID representing the snack's id
     * @return true if successfully removed from the cart, false otherwise
     */
    public boolean deleteFromCart(@JsonProperty("id") int id) {
        // Assume that id is valid snack
        if (this.containsKey(id)) {
            if (this.get(id) == 1) {
                this.remove(id);
            }
            else {
                this.put(id, this.get(id) - 1);
            }

            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Retrieves the cart of the parent buyer
     * 
     * @return this
     */
    public HashMap<Integer,Integer> getCart() {
        return this;
    }

    /**
     * Clears the cart of the buyer
     * 
     * @return true if the cart was successfully cleared, false otherwise
     */
    public boolean clearCart() {
        this.clear();
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        String s = "";
        try {
            s = new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s;
    }
}
