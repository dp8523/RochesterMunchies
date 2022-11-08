package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.Mockito.description;

import java.util.Map;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for Buyer
 * 
 * @author Dara Prak
 */
@Tag("Model-tier")
public class BuyerTest {
    @Test
    public void testCtor() {
        // Setup
        String expectedUsername = "SnackLover";

        // Invoke
        Buyer buyer = new Buyer(expectedUsername);

        // Analyze
        assertEquals(expectedUsername,buyer.getUsername());
    }

    @Test
    public void testSetGetName() {
        // Setup
        String username = "SnackLover";
        Buyer buyer = new Buyer(username);

        String expectedUsername = "SnackEnjoyer";

        // Invoke
        buyer.setUsername(expectedUsername);

        // Analyze
        assertEquals(expectedUsername,buyer.getUsername());
    }

    @Test
    public void testSetGetCart() {
        // Setup
        String username = "SnackLover";
        Buyer buyer = new Buyer(username);

        ShoppingCart expectedCart = new ShoppingCart();
        expectedCart.addToCart(1);

        // Invoke
        buyer.setCart(expectedCart);

        // Analyze
        assertEquals(expectedCart,buyer.getCart());
    }

    @Test
    public void testAddToCart() {
        // Setup
        String username = "SnackLover";
        Buyer buyer = new Buyer(username);

        ShoppingCart expectedCart = new ShoppingCart();
        expectedCart.addToCart(1);
        expectedCart.addToCart(1);
        expectedCart.addToCart(2);

        // Invoke
        buyer.addToCart(1);
        buyer.addToCart(1);
        buyer.addToCart(2);

        // Analyze
        assertEquals(expectedCart,buyer.getCart());

    }

    @Test
    public void testDeleteFromCart() {
        // Setup
        String username = "SnackLover";
        Buyer buyer = new Buyer(username);

        buyer.addToCart(1);
        buyer.addToCart(1);
        buyer.addToCart(2);

        ShoppingCart expectedCart = new ShoppingCart();
        expectedCart.addToCart(1);
        expectedCart.addToCart(2);

        // Invoke
        buyer.deleteFromCart(1);

        // Analyze
        assertEquals(expectedCart,buyer.getCart());

    }

    @Test
    public void testSnackInCart() {
        // Setup

        // Invoke

        // Analyze

    }

    @Test
    public void testToString() {
        // Setup
        String username = "SnackLover";

        Buyer buyer = new Buyer(username);
        String expectedString = String.format(Buyer.STRING_FORMAT,username,buyer.getCart());

        // Invoke
        String actualString = buyer.toString();

        // Analyze
        assertEquals(expectedString,actualString);
    }
}