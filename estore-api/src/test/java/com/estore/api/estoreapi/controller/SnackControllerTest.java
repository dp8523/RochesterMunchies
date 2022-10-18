package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.estore.api.estoreapi.persistence.SnackDAO;
import com.estore.api.estoreapi.model.Snack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Snack Controller class
 * 
 * @author SWEN Faculty
 */
@Tag("Controller-tier")
public class SnackControllerTest {
    private EstoreController snackController;
    private SnackDAO mockSnackDAO;

    /**
     * Before each test, create a new SnackController object and inject
     * a mock Snack DAO
     */
    @BeforeEach
    public void setupSnackController() {
        mockSnackDAO = mock(SnackDAO.class);
        snackController = new EstoreController(mockSnackDAO);
    }

    @Test
    public void testGetSnack() throws IOException {  // getSnack may throw IOException
        // Setup
        Snack Snack = new Snack(99,"Camel Balls", "Extra Sour Bubble Gum Jawbreaker", 5, 9.99);
        // When the same id is passed in, our mock Snack DAO will return the Snack object
        when(mockSnackDAO.getSnack(Snack.getId())).thenReturn(Snack);

        // Invoke
        ResponseEntity<Snack> response = snackController.getSnack(Snack.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(Snack,response.getBody());
    }

    @Test
    public void testGetSnackNotFound() throws Exception { // createSnack may throw IOException
        // Setup
        int SnackId = 99;
        // When the same id is passed in, our mock Snack DAO will return null, simulating
        // no Snack found
        when(mockSnackDAO.getSnack(SnackId)).thenReturn(null);

        // Invoke
        ResponseEntity<Snack> response = snackController.getSnack(SnackId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetSnackHandleException() throws Exception { // createSnack may throw IOException
        // Setup
        int SnackId = 99;
        // When getSnack is called on the Mock Snack DAO, throw an IOException
        doThrow(new IOException()).when(mockSnackDAO).getSnack(SnackId);

        // Invoke
        ResponseEntity<Snack> response = snackController.getSnack(SnackId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /*****************************************************************
     * The following tests will fail until all SnackController methods
     * are implemented.
     ****************************************************************/
    
    @Test
    public void testCreateSnack() throws IOException {  // createSnack may throw IOException
        // Setup
        Snack Snack = new Snack(99,"Haribo Coca-Cola Gummies", "Coca-cola flavored gummies in coca-cola bottle shapes", 10, 4.99);

        // Invoke
        ResponseEntity<Snack> response = snackController.createSnack(Snack);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(Snack,response.getBody());
    }
    
    @Test
    public void testCreateSnackFailed() throws IOException {  // createSnack may throw IOException
        // Setup
        Snack Snack = new Snack(99,"Oreos", "Chocolate creme sandwich cookies", 20,  2.99);
        // when createSnack is called, return false simulating failed
        // creation and save
        when(mockSnackDAO.createSnack(Snack)).thenReturn(null);

        // Invoke
        ResponseEntity<Snack> response = snackController.createSnack(Snack);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    
    @Test
    public void testCreateSnackHandleException() throws IOException {  // createSnack may throw IOException
        // Setup
        Snack Snack = new Snack(99,"Animal Crackers", "Crackers shaped like animals", 12, 8.99);

        // When createSnack is called on the Mock Snack DAO, throw an IOException
        doThrow(new IOException()).when(mockSnackDAO).createSnack(Snack);

        // Invoke
        ResponseEntity<Snack> response = snackController.createSnack(Snack);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateSnack() throws IOException { // updateSnack may throw IOException
        // Setup
        Snack Snack = new Snack(99,"Haribo Coca-Cola Gummies", "Coca-cola flavored gummies in coca-cola bottle shapes", 10, 4.99);
        // when updateSnack is called, return true simulating successful
        // update and save
        when(mockSnackDAO.updateSnack(Snack)).thenReturn(Snack);
        ResponseEntity<Snack> response = snackController.updateSnack(Snack);
        Snack.setName("Oreos");

        response = snackController.updateSnack(Snack);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(Snack,response.getBody());
    }

    @Test
    public void testUpdateSnackFailed() throws IOException {         
        /**
        * updateSnack may throw IOException
        * tests the updateSnack method with snack that doesn't exist
        */
        Snack Snack = new Snack(99,"Camel Balls", "Extra Sour Bubble Gum Jawbreaker", 5, 9.99);
        // when updateSnack is called, return true simulating successful
        // update and save
        when(mockSnackDAO.updateSnack(Snack)).thenReturn(null);

        ResponseEntity<Snack> response = snackController.updateSnack(Snack);

        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateSnackHandleException() throws IOException { 
        /**
         * tests the updateSnack method throw IOException
         */
        Snack Snack = new Snack(99,"Camel Balls", "Extra Sour Bubble Gum Jawbreaker", 5, 9.99);

        doThrow(new IOException()).when(mockSnackDAO).updateSnack(Snack);

        ResponseEntity<Snack> response = snackController.updateSnack(Snack);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}