package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.Buyer;
import com.estore.api.estoreapi.model.ShoppingCart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the BuyerFileDAO class
 */
@Tag("Persistence-tier")
public class BuyerFileDAOTest {
    BuyerFileDAO buyerFileDAO;
    Buyer[] testBuyers;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupBuyerFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testBuyers = new Buyer[2];
        testBuyers[0] = new Buyer("Adam");
        testBuyers[1] = new Buyer("Dara");

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the hero array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Buyer[].class))
                .thenReturn(testBuyers);
        buyerFileDAO = new BuyerFileDAO("doesnt_matter.txt", mockObjectMapper);
    }

    @Test
    public void testLogin() {
        // Invoke
        Buyer buyer = buyerFileDAO.login("Adam");

        // Analzye
        assertEquals(buyer,testBuyers[0]);
    }

    @Test
    public void testLoginNotFound() {
        // Invoke
        Buyer buyer = buyerFileDAO.login("Robert");

        // Analyze
        assertEquals(buyer,null);
    }

    @Test
    public void testCreateBuyerAlreadyExists() {
        // Setup
        String username = "Adam";

        // Invoke
        Buyer result = assertDoesNotThrow(() -> buyerFileDAO.createBuyer(username),
                                "Unexpected exception thrown");

        // Analyze
        assertEquals(null, result);
    }

    @Test
    public void testCreateBuyer() {
        // Setup
        String username = "Robert";

        // Invoke
        Buyer result = assertDoesNotThrow(() -> buyerFileDAO.createBuyer(username),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Buyer actual = new Buyer("Robert");
        assertEquals(actual,result);
    }

    @Test
    public void testDeleteBuyer() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> buyerFileDAO.deleteBuyer("Dara"),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(true,result);
        // We check the internal hash set size against the length
        // of the test buyers array - 1 (because of the delete)
        // Because buyers attribute of BuyerFileDAO is package private
        // we can access it directly
        assertEquals(buyerFileDAO.buyers.size(),testBuyers.length-1);
    }

    @Test
    public void testDeleteBuyerNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> buyerFileDAO.deleteBuyer("Robert"),
                            "Unexpected exception thrown");

        // Analyze
        assertEquals(false,result);
        assertEquals(buyerFileDAO.buyers.size(),testBuyers.length);
    }

    @Test
    public void testDeleteBuyerAdmin() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> buyerFileDAO.deleteBuyer("admin"),
                            "Unexpected exception thrown");

        // Analyze
        assertEquals(false,result);
        assertEquals(buyerFileDAO.buyers.size(),testBuyers.length);
    }

    @Test
    public void testAddToCart() {
        // Setup
        int snackID = 1;
        ShoppingCart cart = new ShoppingCart();
        cart.addToCart(snackID);

        // Invoke
        Buyer buyer = assertDoesNotThrow(() -> buyerFileDAO.addToCart("Adam", snackID),
                                "Unexpected exception thrown");

        // Analyze
        assertEquals(cart,buyer.getCart());
        assertEquals(cart,testBuyers[0].getCart());
    }

    @Test
    public void testAddToCartBuyerNotFound() {
        // Setup
        int snackID = 1;

        // Invoke
        Buyer buyer = assertDoesNotThrow(() -> buyerFileDAO.addToCart("Robert", snackID),
                                "Unexpected exception thrown");

        // Analyze
        assertNull(buyer);
    }

    @Test
    public void testDeleteFromCart() {
        // Setup
        int snackID = 1;
        testBuyers[0].addToCart(snackID);

        ShoppingCart emptyCart = new ShoppingCart();

        // Invoke
        Buyer buyer = assertDoesNotThrow(() -> buyerFileDAO.deleteFromCart("Adam", snackID),
                                "Unexpected exception thrown");

        // Analyze
        assertEquals(emptyCart,buyer.getCart());
        assertEquals(emptyCart,testBuyers[0].getCart());
    }

    @Test
    public void testDeleteFromCartBuyerNotFound() {
        // Setup
        int snackID = 1;

        // Invoke
        Buyer buyer = assertDoesNotThrow(() -> buyerFileDAO.deleteFromCart("Robert", snackID),
                                "Unexpected exception thrown");

        // Analyze
        assertNull(buyer);
    }

    @Test
    public void testClearCart() {
        // Setup
        int snackID = 1;
        testBuyers[0].addToCart(snackID);
        String username = "Adam";

        ShoppingCart emptyCart = new ShoppingCart();

        // Invoke
        Buyer buyer = assertDoesNotThrow(() -> buyerFileDAO.clearCart(username),
                                "Unexpected exception thrown");

        // Analyze
        assertEquals(emptyCart,buyer.getCart());
        assertEquals(emptyCart,testBuyers[0].getCart());
    }

    @Test
    public void testClearCartBuyerNotFound() {
        // Setup
        String username = "Robert";

        // Invoke
        Buyer buyer = assertDoesNotThrow(() -> buyerFileDAO.clearCart(username),
                                "Unexpected exception thrown");

        // Analyze
        assertNull(buyer);
    }

    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Buyer[].class));

        assertThrows(IOException.class, () -> buyerFileDAO.createBuyer("djsiaod"), "IOException not thrown");
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the BuyerFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Buyer[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new BuyerFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}
