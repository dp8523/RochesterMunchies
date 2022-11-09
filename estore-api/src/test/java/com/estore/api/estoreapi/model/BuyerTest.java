package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    public void testClearCart() {
        // Setup
        String username = "SnackLover";
        Buyer buyer = new Buyer(username);

        buyer.addToCart(1);
        buyer.addToCart(1);
        buyer.addToCart(2);

        ShoppingCart expectedCart = new ShoppingCart();

        // Invoke
        buyer.clearCart();

        // Analyze
        assertEquals(expectedCart,buyer.getCart());
    }

    @Test
    public void testSnackInCart() {
        // Setup
        String username = "SnackLover";
        Buyer buyer = new Buyer(username);

        buyer.addToCart(1);

        // Invoke
        boolean result = buyer.snackInCart(1);

        // Analyze
        assertEquals(true,result);
    }

    @Test
    public void testSnackNotInCart() {
        // Setup
        String username = "SnackLover";
        Buyer buyer = new Buyer(username);

        // Invoke
        boolean result = buyer.snackInCart(1);

        // Analyze
        assertEquals(false,result);
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

    @Test
    public void testHashCode() {
        // Setup
        String username = "SnackLover";

        Buyer buyer1 = new Buyer(username);
        Buyer buyer2 = new Buyer(username);

        // Invoke
        int result1 = buyer1.hashCode();
        int result2 = buyer2.hashCode();

        // Analyze
        assertEquals(result1,result2);
    }

    @Test
    public void testEqualsIsNull() {
        // Setup
        String username = "SnackLover";

        Buyer buyer1 = new Buyer(username);

        // Invoke
        boolean result = buyer1.equals(null);

        // Analyze
        assertEquals(false,result);
    }

    @Test
    public void testEqualsSameClass() {
        // Setup
        String username = "SnackLover";
        String username2 = "SnackHater";

        Buyer buyer1 = new Buyer(username);
        Buyer buyer2 = new Buyer(username2);

        // Invoke
        boolean result = buyer1.getClass().equals(buyer2.getClass());

        // Analyze
        assertEquals(true,result);
    }

    @Test
    public void testEqualsSameUsername() {
        // Setup
        String username = "SnackLover";

        Buyer buyer1 = new Buyer(username);
        Buyer buyer2 = new Buyer(username);

        // Invoke
        boolean result = buyer1.getUsername().equals(buyer2.getUsername());

        // Analyze
        assertEquals(true,result);
    }

    @Test
    public void testEquals() {
        // Setup
        String username = "SnackLover";

        Buyer buyer1 = new Buyer(username);
        Buyer buyer2 = new Buyer(username);

        // Invoke
        boolean result = buyer1.equals(buyer2);

        // Analyze
        assertEquals(true,result);
    }

    @Test
    public void testEqualsNotSameUsername() {
        // Setup
        String username = "SnackLover";
        String username2 = "SnackHater";

        Buyer buyer1 = new Buyer(username);
        Buyer buyer2 = new Buyer(username2);

        // Invoke
        boolean result = buyer1.equals(buyer2);

        // Analyze
        assertEquals(false,result);
    }
}