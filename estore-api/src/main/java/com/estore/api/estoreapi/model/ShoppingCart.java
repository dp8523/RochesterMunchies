package com.estore.api.estoreapi.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;

public class ShoppingCart {
    @JsonProperty("cart") private HashMap<Integer,Integer> cart;

    public ShoppingCart() {
        cart = new HashMap<Integer,Integer>();
    }

    public boolean addItemToCart(@JsonProperty("id") int id) {
        // Assume that id is valid snack
        try {
            if (cart.containsKey(id)) {
                cart.put(id, cart.get(id) + 1);
                return true;
            }
            else {
                cart.put(id, 1);
                return true;
            }
        }
        catch (Exception e) {
            System.out.printf("Error adding SnackID: %i to cart.", id);
            return false;
        }
    }

    public HashMap<Integer,Integer> getCart() {
        return cart;
    }

    // public boolean setCart(HashMap<Integer,Integer> newCart) {
    //     try {
    //         cart = newCart;
    //         return true;
    //     } catch (Exception e) {
    //         return false;
    //     }
    // }

    @Override
    public String toString() {
        String s = "";
        try {
            s = new ObjectMapper().writeValueAsString(cart);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s;
    }
}
