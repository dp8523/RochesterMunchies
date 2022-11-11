package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.persistence.BuyerDAO;
import com.estore.api.estoreapi.persistence.SnackDAO;
import com.estore.api.estoreapi.model.Buyer;
import com.estore.api.estoreapi.model.ShoppingCart;
import com.estore.api.estoreapi.model.Snack;

/**
 * Handles the REST API requests for the Buyer resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 */

@RestController
@RequestMapping("buyers")
public class BuyerController {
    private static final Logger LOG = Logger.getLogger(BuyerController.class.getName());
    private BuyerDAO buyerDao;
    private SnackDAO snackDao;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param buyerDao The {@link BuyerDao Buyer Data Access Object} to perform operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public BuyerController(BuyerDAO buyerDao, SnackDAO snackDao) {
        this.buyerDao = buyerDao;
        this.snackDao = snackDao;
    }

    /**
     * Determines whether a {@linkplain Buyer buyer} exists with the given username and logs in if so
     * 
     * @param username the username of the {@link Buyer buyer}
     * @return ResponseEntity with true and HTTP status of FOUND if the buyer exists
     * ResponseEntity with false and HTTP status of NOT_FOUND if the buyer does not exist
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{username}")
    public ResponseEntity<Buyer> login(@PathVariable String username) {
        LOG.info("GET buyers/" + username);

        try{
            Buyer buyer = buyerDao.login(username);
            if (buyer == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<Buyer>(buyer, HttpStatus.FOUND);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }  
    }

    /**
     * Creates a {@linkplain Buyer buyer} with the provided username
     * 
     * @param username - The username of the {@link Buyer buyer} to create
     * 
     * @return ResponseEntity with created {@link Buyer buyer} object and HTTP status of CREATED if the it is unique
     * ResponseEntity with HTTP status of CONFLICT if {@link Buyer buyer} object already exists
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("/{username}")
    public ResponseEntity<Buyer> createBuyer(@PathVariable String username) {
        LOG.info("POST /buyers " + username);

        try{
            Buyer result = buyerDao.login(username);
            
            if (result == null) {
                Buyer buyer = buyerDao.createBuyer(username);
                return new ResponseEntity<Buyer>(buyer, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<Buyer>(HttpStatus.CONFLICT);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }   
    }

    /**
     * Deletes a {@linkplain Buyer buyer} with the given username
     * 
     * @param username The username of the {@link Buyer buyer} to be deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted
     * ResponseEntity with HTTP status of NOT_FOUND if not found or if attempting to delete admin
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{username}")
    public ResponseEntity<Buyer> deleteBuyer(@PathVariable String username) {
        LOG.info("DELETE /buyers/" + username);
        try {
            if (buyerDao.deleteBuyer(username)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/a/{username}/{snackID}")
    public ResponseEntity<Buyer> addToCart(@PathVariable String username, @PathVariable int snackID) {
        LOG.info("PUT / " + username + "/" + snackID);

        try {
            Snack snack = snackDao.getSnack(snackID);

            if (snack != null) {
                // Check if Buyer exists
                Buyer newBuyer = buyerDao.addToCart(username, snackID);

                if (newBuyer == null) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                else {
                    return new ResponseEntity<Buyer>(newBuyer, HttpStatus.OK);
                }
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/d/{username}/{snackID}")
    public ResponseEntity<Buyer> deleteFromCart(@PathVariable String username, @PathVariable int snackID) {
        LOG.info("DELETE / " + username + "/" + snackID);

        try {
            Snack snack = snackDao.getSnack(snackID);

            if (snack != null) {
                // Check if Buyer exists
                Buyer newBuyer = buyerDao.deleteFromCart(username, snackID);

                if (newBuyer == null) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                else {
                    if (!newBuyer.snackInCart(snackID)) {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }
                    return new ResponseEntity<Buyer>(newBuyer, HttpStatus.OK);
                }
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{username}/cartTotal")
    public ResponseEntity<Double> getTotalCartCost(@PathVariable String username) {
        LOG.info("GET / " + username + "/cartTotal");

        try {
            Buyer buyer = buyerDao.login(username);

            if (buyer != null) {
                double cartTotal = 0;
                ShoppingCart cart = buyer.getCart();

                for(int snackId : cart.keySet()) {
                    Snack snack = snackDao.getSnack(snackId);
                    double snackPrice = snack.getPrice();
                    int quantity = cart.get(snackId);
                    double snackTotal = snackPrice * quantity;
                    cartTotal += snackTotal;
                }
                
                return new ResponseEntity<Double>(cartTotal, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/r/{username}/{snackID}/{rating}")
    public ResponseEntity<Double> rateSnack(@PathVariable String username, @PathVariable int snackID, @PathVariable int rating) {
        LOG.info("PUT /r/" + username + "/" + snackID + "/" + rating);

        try {
            Snack snack = snackDao.getSnack(snackID);

            if (snack != null) {
                // Check if Buyer exists
                Buyer buyer = buyerDao.login(username);

                if (buyer != null) {
                    double averageRating = snackDao.rateSnack(snackID, username, rating);
                    return new ResponseEntity<Double>(averageRating, HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
