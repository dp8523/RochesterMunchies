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
    public void testUpdateSnack() throws IOException { 
        /**
         * updateSnack may throw IOException
         * tests the updateSnack method with snack that exists
         */
        Snack Snack = new Snack(99,"Haribo Coca-Cola Gummies", "Coca-cola flavored gummies in coca-cola bottle shapes", 10, 4.99);

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