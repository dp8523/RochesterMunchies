package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.persistence.SnackDAO;
import com.estore.api.estoreapi.model.Snack;

/**
 * Handles the REST API requests for the Snack resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 */

@RestController
@RequestMapping("snacks")
public class EstoreController {
    private static final Logger LOG = Logger.getLogger(EstoreController.class.getName());
    private SnackDAO snackDao;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param snackDao The {@link SnackDao Snack Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public EstoreController(SnackDAO snackDao) {
        this.snackDao = snackDao;
    }

    /**
     * Creates a {@linkplain Snack snack} with the provided snack object
     * 
     * @param snack - The {@link Snack snack} to create
     * 
     * @return ResponseEntity with created {@link Snack snack} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Snack snack} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Snack> createSnack(@RequestBody Snack snack) {
        LOG.info("POST /snacks " + snack);
        try{
            Snack s = snackDao.createSnack(snack);
            return new ResponseEntity<Snack>(s, HttpStatus.OK);
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }   
    }

    /**
     * Responds to the GET request for all {@linkplain Snack snacks} whose name contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the {@link Snack snacks}
     * 
     * @return ResponseEntity with array of {@link Snack snack} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * Example: Find all heroes that contain the text "ma"
     * GET http://localhost:8080/heroes/?name=ma
     */
    @GetMapping("/")
    public ResponseEntity<Snack[]> searchSnacks(@RequestParam String name) {
        LOG.info("GET /snacks/?name="+name);
        try {
            Snack snack[] = snackDao.findSnacks( name );
            return new ResponseEntity<Snack[]>(snack, HttpStatus.OK);
        }
        catch ( IOException e ) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
