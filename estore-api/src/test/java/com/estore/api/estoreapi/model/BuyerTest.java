package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.Mockito.description;

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
    public void testName() {
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
    public void testToString() {
        // Setup
        String username = "SnackLover";

        String expectedString = String.format(Buyer.STRING_FORMAT,username);
        Buyer buyer = new Buyer(username);

        // Invoke
        String actualString = buyer.toString();

        // Analyze
        assertEquals(expectedString,actualString);
    }
}