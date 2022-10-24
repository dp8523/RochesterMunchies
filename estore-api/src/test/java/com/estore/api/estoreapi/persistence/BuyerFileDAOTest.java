package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.Buyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


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
        testBuyers[0] = new Buyer("sweet");
        testBuyers[1] = new Buyer("salty");

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the hero array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Buyer[].class))
                .thenReturn(testBuyers);
        buyerFileDAO = new BuyerFileDAO("doesnt_matter.txt", mockObjectMapper);
    }

    @Test
    public void testLoginAdmin() {
        // Invoke
        String result = assertDoesNotThrow(() -> buyerFileDAO.login("admin"),
                                    "Unexpected exception thrown");

        // Analyze
        assertEquals("admin",result);
    }

    @Test
    public void testLoginBuyer() {
        // Invoke
        String result = assertDoesNotThrow(() -> buyerFileDAO.login("sweet"),
        "Unexpected exception thrown");

        // Analyze
        assertEquals(false,result);
    }

    @Test
    public void testCreateBuyer() {
        // Setup
        String username = "spicy";

        // Invoke
        Buyer result = assertDoesNotThrow(() -> buyerFileDAO.createBuyer(username),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Buyer actual = new Buyer("spicy");
        assertEquals(actual,result);
    }

    @Test
    public void testDeleteBuyer() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> buyerFileDAO.deleteBuyer("salty"),
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
    public void testDeleteSnackNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> buyerFileDAO.deleteBuyer("bitter"),
                            "Unexpected exception thrown");

        // Analyze
        assertEquals(false,result);
        assertEquals(buyerFileDAO.buyers.size(),testBuyers.length);
    }

    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Buyer[].class));

        assertThrows(IOException.class,
                        () -> buyerFileDAO.createBuyer("spicy"),
                        "IOException not thrown");
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
