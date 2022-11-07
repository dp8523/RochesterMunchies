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
     * a mock BuyerDAO and SnackDAO
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
        String username = "Adam";
        Buyer buyer = new Buyer(username);
        // When the same username is passed in, our mock BuyerDAO will return the corresponding Buyer
        when(mockBuyerDAO.login(username)).thenReturn(buyer);

        // Invoke
        ResponseEntity<Buyer> response = buyerController.login(username);

        // Analyze
        assertEquals(HttpStatus.FOUND,response.getStatusCode());
        assertEquals(buyer,response.getBody());
    }

    @Test
    public void testLoginNotFound() throws Exception {
        // Setup
        String username = "Sudhir";
        // When the same username is passed in, our mock BuyerDAO will return null, simulating
        // no Buyer found
        when(mockBuyerDAO.login(username)).thenReturn(null);

        // Invoke
        ResponseEntity<Buyer> response = buyerController.login(username);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertEquals(null,response.getBody());
    }

    @Test
    public void testLoginHandleException() throws Exception { // createBuyer may throw IOException
        // Setup
        String username = "Sudhir";
        // When getBuyer is called on the Mock BuyerDAO, throw an IOException
        doThrow(new IOException()).when(mockBuyerDAO).login(username);

        // Invoke
        ResponseEntity<Buyer> response = buyerController.login(username);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
    
    @Test
    public void testCreateBuyer() throws IOException {  // createBuyer may throw IOException
        // Setup
        String username = "Sudhir";
        Buyer buyer = new Buyer(username);

        when(mockBuyerDAO.createBuyer(username)).thenReturn(buyer);

        // Invoke
        ResponseEntity<Buyer> response = buyerController.createBuyer(username);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(buyer,response.getBody());
    }
    
    @Test
    public void testCreateBuyerConflict() throws IOException {  // createBuyer may throw IOException
        // Setup
        String username = "Sudhir";
        Buyer buyer = new Buyer(username);

        when(mockBuyerDAO.login(username)).thenReturn(buyer);

        // Invoke
        ResponseEntity<Buyer> response = buyerController.createBuyer(username);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    
    @Test
    public void testCreateBuyerHandleException() throws IOException {  // createBuyer may throw IOException
        // Setup
        String username = "Sudhir";

        // When createBuyer is called on the Mock BuyerDAO, throw an IOException
        doThrow(new IOException()).when(mockBuyerDAO).createBuyer(username);

        // Invoke
        ResponseEntity<Buyer> response = buyerController.createBuyer(username);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteBuyer() throws IOException { // deleteBuyer may throw IOException
        // Setup
        String username = "Sudhir";
        // when deleteBuyer is called return true, simulating successful deletion
        when(mockBuyerDAO.deleteBuyer(username)).thenReturn(true);

        // Invoke
        ResponseEntity<Buyer> response = buyerController.deleteBuyer(username);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteBuyerNotFound() throws IOException { // deleteBuyer may throw IOException
        // Setup
        String username = "Sudhir";
        // when deleteBuyer is called return false, simulating failed deletion
        when(mockBuyerDAO.deleteBuyer(username)).thenReturn(false);

        // Invoke
        ResponseEntity<Buyer> response = buyerController.deleteBuyer(username);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteBuyerHandleException() throws IOException { // deleteBuyer may throw IOException
        // Setup
        String username = "Sudhir";
        // When deleteBuyer is called on the Mock BuyerDAO, throw an IOException
        doThrow(new IOException()).when(mockBuyerDAO).deleteBuyer(username);

        // Invoke
        ResponseEntity<Buyer> response = buyerController.deleteBuyer(username);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testAddToCartSnackNotFound() throws IOException { // addToCart may throw IOException
        // Setup
        String username = "Sudhir";
        int snackID = 99;
        Buyer buyer = new Buyer(username);

        // When snack does not exist
        when(mockSnackDAO.getSnack(snackID)).thenReturn(null);
        // When buyer exists in mock
        when(mockBuyerDAO.addToCart(username, snackID)).thenReturn(buyer);

        ResponseEntity<Buyer> response = buyerController.addToCart(username, snackID);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testAddToCartBuyerNotFound() throws IOException { // addToCart may throw IOException
        // Setup
        String username = "Sudhir";
        int snackID = 99;
        Snack snack = new Snack(99, "Haribo Coca-Cola Gummies", "Coca-cola flavored gummies in coca-cola bottle shapes", 5, 4.99);

        // When snack does exist
        when(mockSnackDAO.getSnack(snackID)).thenReturn(snack);
        // When buyer does not exist
        when(mockBuyerDAO.addToCart(username, snackID)).thenReturn(null);

        ResponseEntity<Buyer> response = buyerController.addToCart(username, snackID);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testAddToCart() throws IOException { // addToCart may throw IOException
        // Setup
        int snackID = 99;
        String username = "Sudhir";
        Buyer buyer = new Buyer(username);
        Snack snack = new Snack(snackID, "Haribo Coca-Cola Gummies", "Coca-cola flavored gummies in coca-cola bottle shapes", 5, 4.99);

        // When snack does exist
        when(mockSnackDAO.getSnack(snackID)).thenReturn(snack);
        // When buyer does exist
        when(mockBuyerDAO.addToCart(username, snackID)).thenReturn(buyer);

        // Invoke
        ResponseEntity<Buyer> response = buyerController.addToCart(username, snackID);

        // Analyze
        assertEquals(buyer, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testAddToCartHandleException() throws IOException { // addToCart may throw IOException
        // Setup
        String username = "Sudhir";
        int snackID = 99;
    
        // When addToCart is called on the Mock Buyer DAO, throw an IOException
        doThrow(new IOException()).when(mockBuyerDAO).addToCart(username, snackID);

        // Invoke
        ResponseEntity<Buyer> response = buyerController.addToCart(username, snackID);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    // @Test
    // public void testGetTotalCartCost() throws IOException { // addToCart may throw IOException
    //     // Setup
    //     Snack Snack = new Snack(99,"Camel Balls", "Extra Sour Bubble Gum Jawbreaker", 5, 9.99);
    //     // When the same id is passed in, our mock Snack DAO will return the Snack object
    //     when(mockBuyerDAO.getTotalCartCost(Snack.getId())).thenReturn(Snack);

    //     // Invoke
    //     ResponseEntity<Snack> response = snackController.getSnack(Snack.getId());

    //     // Analyze
    //     assertEquals(HttpStatus.OK,response.getStatusCode());
    //     assertEquals(Snack,response.getBody());
    // }

    @Test
    public void testDeleteFromCartSnackNotFound() throws IOException { // deleteFromCart may throw IOException
        // Setup
        String username = "Sudhir";
        int snackID = 99;
        Buyer buyer = new Buyer(username);

        // When snack does not exist
        when(mockSnackDAO.getSnack(snackID)).thenReturn(null);
        // When buyer exists in mock
        when(mockBuyerDAO.deleteFromCart(username, snackID)).thenReturn(null);

        ResponseEntity<Buyer> response = buyerController.deleteFromCart(username, snackID);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteFromCartBuyerNotFound() throws IOException { // deleteFromCart may throw IOException
        // Setup
        String username = "Sudhir";
        int snackID = 99;
        Snack snack = new Snack(99, "Haribo Coca-Cola Gummies", "Coca-cola flavored gummies in coca-cola bottle shapes", 5, 4.99);

        // When snack does exist
        when(mockSnackDAO.getSnack(snackID)).thenReturn(snack);
        // When buyer does not exist
        when(mockBuyerDAO.deleteFromCart(username, snackID)).thenReturn(null);

        ResponseEntity<Buyer> response = buyerController.deleteFromCart(username, snackID);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteFromCart() throws IOException { // deleteFromCart may throw IOException
        // Setup
        int snackID = 99;
        String username = "Sudhir";
        Buyer buyer = new Buyer(username);
        Snack snack = new Snack(snackID, "Haribo Coca-Cola Gummies", "Coca-cola flavored gummies in coca-cola bottle shapes", 5, 4.99);
        buyer.addToCart(snackID);

        // When snack does exist
        when(mockSnackDAO.getSnack(snackID)).thenReturn(snack);

        // When buyer does exist
        when(mockBuyerDAO.deleteFromCart(username, snackID)).thenReturn(buyer);

        // Invoke
        ResponseEntity<Buyer> response = buyerController.deleteFromCart(username, snackID);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(buyer, response.getBody());
    }

    @Test
    public void testDeleteFromCartHandleException() throws IOException { // deleteFromCart may throw IOException
        // Setup
        String username = "Sudhir";
        int snackID = 99;
    
        // When deleteFromCart is called on the Mock Buyer DAO, throw an IOException
        doThrow(new IOException()).when(mockBuyerDAO).deleteFromCart(username, snackID);

        // Invoke
        ResponseEntity<Buyer> response = buyerController.deleteFromCart(username, snackID);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}