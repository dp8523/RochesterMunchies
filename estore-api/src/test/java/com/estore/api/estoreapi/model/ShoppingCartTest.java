package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for ShoppingCart
 */
@Tag("Model-tier")
public class ShoppingCartTest {

    @Test
    public void testAddToCart() {
        // Setup
        int snackID = 99;
        ShoppingCart cart = new ShoppingCart();

        // Invoke
        boolean response = cart.addToCart(snackID);

        // Analyze
        assertEquals(true, response);
    }

    @Test
    public void testDeleteFromCart() {
        // Setup
        int snackID = 99;
        ShoppingCart cart = new ShoppingCart();
        cart.addToCart(snackID);

        // Invoke
        boolean response = cart.deleteFromCart(snackID);

        // Analyze
        assertEquals(true, response);
    }

    @Test
    public void testDeleteFromCartNotFound() {
        // Setup
        int snackID = 99;
        ShoppingCart cart = new ShoppingCart();

        // Invoke
        boolean response = cart.deleteFromCart(snackID);

        // Analyze
        assertEquals(false, response);
    }

    @Test
    public void testGetCart() {
        // Setup
        int snackID = 99;
        ShoppingCart cart = new ShoppingCart();
        cart.addToCart(snackID);
        HashMap<Integer, Integer> expectedCart = new HashMap<Integer, Integer>();
        expectedCart.put(snackID, 1);

        // Invoke
        HashMap<Integer, Integer> response = cart.getCart();

        // Analyze
        assertEquals(expectedCart, response);
    }

    @Test
    public void testClearCart() {
        // Setup
        int snackID = 99;
        ShoppingCart cart = new ShoppingCart();
        cart.addToCart(snackID);

        // Invoke
        boolean response = cart.clearCart();

        // Analyze
        assertEquals(true, response);
    }

    @Test
    public void testToString() {
        // Setup
        int snackID = 99;
        ShoppingCart cart = new ShoppingCart();
        cart.addToCart(snackID);

        String expected_string = "{\"99\":1}";

        // Invoke
        String actual_string = cart.toString();

        // Analyze
        assertEquals(expected_string,actual_string);
    }

    // TODO: Test toString Handle Exception function, object mapper difficulty
}