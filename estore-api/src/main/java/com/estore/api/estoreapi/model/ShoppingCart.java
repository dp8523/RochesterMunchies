package com.estore.api.estoreapi.model;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ShoppingCart extends HashMap<Integer,Integer>{
    // @JsonProperty("cart") private HashMap<Integer,Integer> cart;

    // public ShoppingCart() {
    //     cart = new HashMap<Integer,Integer>();
    // }

    public boolean addItemToCart(@JsonProperty("id") int id) {
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
