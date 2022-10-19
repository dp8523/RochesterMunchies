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
     * Responds to the GET request for a {@linkplain Snack snack} for the given id
     * 
     * @param id The id used to locate the {@link Snack snack}
     * 
     * @return ResponseEntity with {@link Snack snack} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Snack> getSnack(@PathVariable int id) {
        LOG.info("GET /snacks/" + id);
        try {
            Snack snack = snackDao.getSnack(id);
            if (snack != null)
                return new ResponseEntity<Snack>(snack,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Hero heroes}
     * 
     * @return ResponseEntity with array of {@link Hero hero} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Snack[]> getSnacks() {
        LOG.info("GET /snacks");
        try {
            Snack[] snackArray = snackDao.getSnacks();
            return new ResponseEntity<Snack[]>(snackArray, HttpStatus.OK);
        }
        catch(IOException e) {
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
     * Example: Find all snacks that contain the text "ma"
     * GET http://localhost:8080/snacks/?name=ma
     */
    @GetMapping("/")
    public ResponseEntity<Snack[]> searchSnacks(@RequestParam String name) {
        LOG.info("GET /snacks/?name="+name);

        try {
            Snack[] snackArray = snackDao.findSnacks(name);
            return new ResponseEntity<Snack[]>(snackArray, HttpStatus.OK);
        }
        catch ( IOException e ) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
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
            Snack newSnack = snackDao.createSnack(snack);

            if (newSnack != null) {
                return new ResponseEntity<Snack>(newSnack, HttpStatus.CREATED);
            }
            else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }   
    }

    /**
     * Updates the {@linkplain Snack snack} with the provided {@linkplain Snack snack} object, if it exists
     * 
     * @param snack The {@link Snack snack} to update
     * @param type The {@link String type} to update
     * @param value The {@link String value} to update
     * 
     * @return ResponseEntity with updated {@link Snack snack} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    // @PutMapping("")
    // public ResponseEntity<Snack> updateSnack(@RequestBody Snack snack, @RequestBody String type, @RequestBody String value) {
    //     LOG.info("PUT /snacks " + snack);

    //     try {
    //         if (snackDao.getSnack(snack.getId()) != null) {
    //             Snack theSnack = snackDao.updateSnack(snack, type, value);
    //             return new ResponseEntity<Snack>(theSnack,HttpStatus.OK);
    //         } else {
    //             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //         }
    //     }
    //     catch(IOException e) {
    //         LOG.log(Level.SEVERE,e.getLocalizedMessage());
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }
    @PutMapping("")
    public ResponseEntity<Snack> updateSnack(@RequestBody Snack snack) {
        LOG.info("PUT /snacks " + snack);

        try {
            Snack newSnack = snackDao.updateSnack(snack);

            if (newSnack == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else {
                return new ResponseEntity<Snack>(newSnack, HttpStatus.OK);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Snack snack} with the given id
     * 
     * @param id The id of the {@link Snack snack} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Snack> deleteSnack(@PathVariable int id) {
        LOG.info("DELETE /snacks/" + id);
        try {
            if (snackDao.deleteSnack(id)) {
                return new ResponseEntity<Snack>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
