package com.estore.api.estoreapi.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;

public class ShoppingCart extends HashMap<Integer,Integer>{

    public boolean addToCart(@JsonProperty("id") int id) {
        // Assume that id is valid snack
        try {
            if (this.containsKey(id)) {
                this.put(id, this.get(id) + 1);
                return true;
            }
            else {
                this.put(id, 1);
                return true;
            }
        }
        catch (Exception e) {
            System.out.printf("Error adding SnackID: %i to cart.", id);
            return false;
        }
    }

    public boolean deleteFromCart(@JsonProperty("id") int id) {
        // Assume that id is valid snack
        try {
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
        catch (Exception e) {
            System.out.printf("Error adding SnackID: %i to cart.", id);
            return false;
        }
    }

    public HashMap<Integer,Integer> getCart() {
        return this;
    }

    public boolean clearCart() {
        try {
            this.clear();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

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
