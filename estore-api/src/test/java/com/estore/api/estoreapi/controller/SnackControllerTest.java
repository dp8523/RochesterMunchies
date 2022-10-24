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
 */
@Tag("Controller-tier")
public class SnackControllerTest {
    private SnackController snackController;
    private SnackDAO mockSnackDAO;

    /**
     * Before each test, create a new SnackController object and inject
     * a mock Snack DAO
     */
    @BeforeEach
    public void setupSnackController() {
        mockSnackDAO = mock(SnackDAO.class);
        snackController = new SnackController(mockSnackDAO);
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
        // when createSnack is called, return true simulating successful
        // creation and save
        when(mockSnackDAO.createSnack(Snack)).thenReturn(Snack);

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
        Snack Snack = new Snack(12,"Haribo Coca-Cola Gummies", "Coca-cola flavored gummies in coca-cola bottle shapes", 10, 4.99);
        // when updateSnack is called, return true simulating successful
        // update and save
        when(mockSnackDAO.updateSnack(Snack)).thenReturn(Snack);
        ResponseEntity<Snack> response = snackController.updateSnack(Snack);
        Snack.setName("Oreos");

        // Invoke
        response = snackController.updateSnack(Snack);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(Snack,response.getBody());
    }

    @Test
    public void testUpdateSnackFailed() throws IOException { // updateSnack may throw IOException
        // Setup
        Snack Snack = new Snack(99,"Camel Balls", "Extra Sour Bubble Gum Jawbreaker", 5, 9.99);
        // when updateSnack is called, return true simulating successful
        // update and save
        when(mockSnackDAO.updateSnack(Snack)).thenReturn(null);

        // Invoke
        ResponseEntity<Snack> response = snackController.updateSnack(Snack);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateSnackHandleException() throws IOException { // updateSnack may throw IOException
        // Setup
        Snack Snack = new Snack(99,"Camel Balls", "Extra Sour Bubble Gum Jawbreaker", 5, 9.99);
        // When updateSnack is called on the Mock Snack DAO, throw an IOException
        doThrow(new IOException()).when(mockSnackDAO).updateSnack(Snack);

        // Invoke
        ResponseEntity<Snack> response = snackController.updateSnack(Snack);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetSnacks() throws IOException { // getSnacks may throw IOException
        // Setup
        Snack[] snacks = new Snack[2];
        snacks[0] = new Snack(99,"Oreos", "Chocolate creme sandwich cookies", 20,  2.99);
        snacks[1] = new Snack(100,"Twix","Chocolate covered, caramel filled, cookie sticks",50,2.99);
        // When getSnacks is called return the snacks created above
        when(mockSnackDAO.getSnacks()).thenReturn(snacks);

        // Invoke
        ResponseEntity<Snack[]> response = snackController.getSnacks();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(snacks,response.getBody());
    }

    @Test
    public void testGetSnacksHandleException() throws IOException { // getSnacks may throw IOException
        // Setup
        // When getSnacks is called on the Mock Snack DAO, throw an IOException
        doThrow(new IOException()).when(mockSnackDAO).getSnacks();

        // Invoke
        ResponseEntity<Snack[]> response = snackController.getSnacks();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchSnacks() throws IOException { // findSnacks may throw IOException
        // Setup
        String searchString = "la";
        Snack[] snacks = new Snack[2];
        snacks[0] = new Snack(99,"Camel Balls", "Extra Sour Bubble Gum Jawbreaker", 5, 9.99);
        snacks[1] = new Snack(100,"Animal Crackers", "Crackers shaped like animals", 12, 8.99);
        // When findSnacks is called with the search string, return the two
        /// snacks above
        when(mockSnackDAO.findSnacks(searchString)).thenReturn(snacks);

        // Invoke
        ResponseEntity<Snack[]> response = snackController.searchSnacks(searchString);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(snacks,response.getBody());
    }

    @Test
    public void testSearchSnacksHandleException() throws IOException { // findSnacks may throw IOException
        // Setup
        String searchString = "an";
        // When createSnack is called on the Mock Snack DAO, throw an IOException
        doThrow(new IOException()).when(mockSnackDAO).findSnacks(searchString);

        // Invoke
        ResponseEntity<Snack[]> response = snackController.searchSnacks(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteSnack() throws IOException { // deleteSnack may throw IOException
        // Setup
        int SnackId = 99;
        // when deleteSnack is called return true, simulating successful deletion
        when(mockSnackDAO.deleteSnack(SnackId)).thenReturn(true);

        // Invoke
        ResponseEntity<Snack> response = snackController.deleteSnack(SnackId);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteSnackNotFound() throws IOException { // deleteSnack may throw IOException
        // Setup
        int SnackId = 99;
        // when deleteSnack is called return false, simulating failed deletion
        when(mockSnackDAO.deleteSnack(SnackId)).thenReturn(false);

        // Invoke
        ResponseEntity<Snack> response = snackController.deleteSnack(SnackId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteSnackHandleException() throws IOException { // deleteSnack may throw IOException
        // Setup
        int SnackId = 99;
        // When deleteSnack is called on the Mock Snack DAO, throw an IOException
        doThrow(new IOException()).when(mockSnackDAO).deleteSnack(SnackId);

        // Invoke
        ResponseEntity<Snack> response = snackController.deleteSnack(SnackId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}