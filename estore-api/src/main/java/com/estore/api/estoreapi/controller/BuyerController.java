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
import java.util.Map;
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
     * @return ResponseEntity with the found buyer instance and HTTP status of OK if the buyer exists
     * ResponseEntity with HTTP status of NOT_FOUND if the buyer does not exist
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
                return new ResponseEntity<Buyer>(buyer, HttpStatus.OK);
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
            Buyer buyer = buyerDao.createBuyer(username);
            if (buyer != null) {
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

    /**
     * Adds a {@linkplain Snack snack} to a Buyer's shoppingcart provided the buyer's username and the snack's
     * snack identifier
     * 
     * @param username - The username of the {@link Buyer buyer} to add to cart
     * @param snackID - The snackID of the {@link Snack snack} to add to the buyer's cart
     * 
     * @return ResponseEntity with {@link Buyer buyer} object and HTTP status of OK if the item was added
     * ResponseEntity with HTTP status of NOT FOUND if the item does not exist in the snack database
     * ResponseEntity with HTTP status of NOT FOUND if the buyer does not exist
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
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

    /**
     * Deletes a {@linkplain Snack snack} from a Buyer's shoppingcart provided the buyer's username and the snack's
     * snack identifier
     * 
     * @param username - The username of the {@link Buyer buyer} to delete item from cart
     * @param snackID - The snackID of the {@link Snack snack} to delete from the buyer's cart
     * 
     * @return ResponseEntity with {@link Buyer buyer} object and HTTP status of OK if the item was deleted
     * ResponseEntity with HTTP status of NOT FOUND if the item does not exist in the buyer's cart
     * ResponseEntity with HTTP status of NOT FOUND if the item does not exist in the snack database
     * ResponseEntity with HTTP status of NOT FOUND if the buyer does not exist
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
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
                    // if (!newBuyer.snackInCart(snackID)) {
                    //     return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    // }
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

    /**
     * Gets the total cost of a {@linkplain Buyer buyer} cart provided the buyer's username
     * 
     * @param username - The username of the {@link Buyer buyer} to retrieve cost of cart
     * 
     * @return ResponseEntity with HTTP status OK and a double value of the total cost
     * ResponseEntity with HTTP status of NOT_FOUND if the buyer does not exist
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
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

    /**
     * 
     */
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

    /**
     * Updates the stock within the snack database whenever a Buyer checks out their cart
     * 
     * @param cart - ShoppingCart instance with hashmap of items being checked out
     * 
     * @return ResponseEntity with HTTP status OK when there is insufficient stock 
     * ResponseEntity with HTTP status NOT_FOUND when the snack is not found
     */
    private ResponseEntity<Buyer> updateStock(ShoppingCart cart) throws IOException {
        // first loop ensures there is enough of every snack in the inventory. if not, cancel checkout
        for (Map.Entry<Integer, Integer> snack : cart.entrySet()) {
            Snack currentSnack = snackDao.getSnack(snack.getKey());

            // checks if the snack exists in the inventory
            if (currentSnack != null) {
                int amountToBuy = snack.getValue();

                int oldStock = currentSnack.getQuantity();
                int newStock = oldStock - amountToBuy;

                // there is insufficient stock for this snack
                if(newStock < 0) {
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        // there is enough of each snack in the inventory, so this loop subtracts from the inventory accordingly
        for (Map.Entry<Integer, Integer> snack : cart.entrySet()) {
            // we know currentSnack exists in the inventory from the previous loop
            Snack currentSnack = snackDao.getSnack(snack.getKey());
            int amountToBuy = snack.getValue();

            int oldStock = currentSnack.getQuantity();
            int newStock = oldStock - amountToBuy;

            // updates inventory with new stock amount for this snack
            currentSnack.setQuantity(newStock);
            snackDao.updateSnack(currentSnack);
        }
        return null;
    }

    /**
     * Checkouts the {@linkplain Buyer buyer} cart when provided the buyer's username
     * 
     * @param username - The username of the {@link Buyer buyer} to delete item from cart
     * 
     * @return ResponseEntity with {@link Buyer buyer} object and HTTP status of OK if cart was checked out
     * ResponseEntity with HTTP status NOT_FOUND if buyer with username is not found
     * ResponseEntity with INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("/{username}/checkout")
    public ResponseEntity<Buyer> checkoutCart(@PathVariable String username) {
        LOG.info("Post / " + username);
        try {
            Buyer buyer = buyerDao.login(username);

            // ensures that the buyer has an existing account
            if (buyer != null) {
                ShoppingCart cart = buyer.getCart();

                ResponseEntity<Buyer> result = updateStock(cart);
                // result is null if there were no issues
                if(result != null) {
                    return result;
                }

                buyer = buyerDao.clearCart(username);
                return new ResponseEntity<Buyer>(buyer, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
