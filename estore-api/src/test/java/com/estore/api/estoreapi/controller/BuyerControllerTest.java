package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.estore.api.estoreapi.persistence.BuyerDAO;
import com.estore.api.estoreapi.model.Buyer;
import com.estore.api.estoreapi.persistence.SnackDAO;
import com.estore.api.estoreapi.model.Snack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Buyer Controller class
 */
@Tag("Controller-tier")
public class BuyerControllerTest {
    private BuyerController buyerController;
    private BuyerDAO mockBuyerDAO;
    private SnackDAO mockSnackDAO;

    /**
     * Before each test, create a new BuyerController object and inject
     * a mock Buyer DAO
     */
    @BeforeEach
    public void setupBuyerController() {
        mockBuyerDAO = mock(BuyerDAO.class);
        mockSnackDAO = mock(SnackDAO.class);
        buyerController = new BuyerController(mockBuyerDAO, mockSnackDAO);
    }

    @Test
    public void testLogin() throws IOException {
        // Setup
        Buyer buyer = new Buyer("sweet");
        // When the same username is passed in, our mock Buyer DAO will return true
        when(mockBuyerDAO.login(buyer.getUsername())).thenReturn(buyer);

        // Invoke
        ResponseEntity<Buyer> response = buyerController.login(buyer.getUsername());

        // Analyze
        assertEquals(HttpStatus.FOUND,response.getStatusCode());
        assertEquals(buyer,response.getBody());
    }

    @Test
    public void testLoginNotFound() throws Exception {
        // Setup
        String username = "Sudhir";
        // When the same id is passed in, our mock Snack DAO will return null, simulating
        // no Snack found
        when(mockBuyerDAO.login(username)).thenReturn(null);

        // Invoke
        ResponseEntity<Buyer> response = buyerController.login(username);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertEquals(null,response.getBody());
    }

    @Test
    public void testLoginHandleException() throws Exception { // createSnack may throw IOException
        // Setup
        String username = "Sudhir";
        // When getSnack is called on the Mock Snack DAO, throw an IOException
        doThrow(new IOException()).when(mockBuyerDAO).login(username);

        // Invoke
        ResponseEntity<Buyer> response = buyerController.login(username);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /*****************************************************************
     * The following tests will fail until all BuyerController methods
     * are implemented.
     ****************************************************************/
    
    @Test
    public void testCreateBuyer() throws IOException {  // createSnack may throw IOException
        // Setup
        String username = "Sudhir";
        Buyer buyer = new Buyer(username);
        // when createSnack is called, return true simulating successful
        // creation and save
        when(mockBuyerDAO.createBuyer(username)).thenReturn(buyer);

        // Invoke
        ResponseEntity<Buyer> response = buyerController.createBuyer(username);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(buyer,response.getBody());
    }
    
    @Test
    public void testCreateBuyerConflict() throws IOException {  // createSnack may throw IOException
        // Setup
        String username = "Sudhir";
        Buyer dupeBuyer = new Buyer("Sudhir");
        // when createSnack is called, return false simulating failed
        // creation and save
        when(mockBuyerDAO.createBuyer(username)).thenReturn(dupeBuyer);

        // Invoke
        ResponseEntity<Buyer> response = buyerController.createBuyer(username);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    
    @Test
    public void testCreateBuyerHandleException() throws IOException {  // createSnack may throw IOException
        // Setup
        String username = "Sudhir";

        // When createSnack is called on the Mock Snack DAO, throw an IOException
        doThrow(new IOException()).when(mockBuyerDAO).createBuyer(username);

        // Invoke
        ResponseEntity<Buyer> response = buyerController.createBuyer(username);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteBuyer() throws IOException { // deleteSnack may throw IOException
        // Setup
        String username = "Sudhir";
        // when deleteSnack is called return true, simulating successful deletion
        when(mockBuyerDAO.deleteBuyer(username)).thenReturn(true);

        // Invoke
        ResponseEntity<Buyer> response = buyerController.deleteBuyer(username);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteBuyerNotFound() throws IOException { // deleteSnack may throw IOException
        // Setup
        String username = "Sudhir";
        // when deleteSnack is called return false, simulating failed deletion
        when(mockBuyerDAO.deleteBuyer(username)).thenReturn(false);

        // Invoke
        ResponseEntity<Buyer> response = buyerController.deleteBuyer(username);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteBuyerHandleException() throws IOException { // deleteSnack may throw IOException
        // Setup
        String username = "Sudhir";
        // When deleteSnack is called on the Mock Snack DAO, throw an IOException
        doThrow(new IOException()).when(mockBuyerDAO).deleteBuyer(username);

        // Invoke
        ResponseEntity<Buyer> response = buyerController.deleteBuyer(username);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}