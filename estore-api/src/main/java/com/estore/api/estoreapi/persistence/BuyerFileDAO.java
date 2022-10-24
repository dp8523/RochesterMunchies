package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.HashSet;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Buyer;

@Component
public class BuyerFileDAO implements BuyerDAO {

    Set<Buyer> buyers;   // Provides a local cache of the Buyer objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Buyer
                                        // objects and JSON text format written
                                        // to the file

    private String filename;    // Filename to read from and write to

    /**
     * Creates a Buyer File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public BuyerFileDAO(@Value("${buyers.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the buyers from the file
    }

    /**
     * Saves the {@linkplain Buyer buyer} from the set into the file as an array of JSON objects
     * 
     * @return true if the {@link Buyer buyer} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Buyer[] buyerArray = buyers.toArray(new Buyer[0]);

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), buyerArray);
        return true;
    }

    /**
     * Loads {@linkplain Buyer buyers} from the JSON file into the set
     * <br>
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        buyers = new HashSet<>();

        // Deserializes the JSON objects from the file into an array of buyers
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Buyer[] buyerArray = objectMapper.readValue(new File(filename),Buyer[].class);

        // Add each Buyer to the hash set
        for (Buyer buyer : buyerArray) {
            buyers.add(buyer);
        }
        
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public String login(String username) throws IOException {
        synchronized(buyers) {
            Buyer buyer = new Buyer(username);
            if (buyer.getName().equals("admin")) {
                return "admin";
            } else if (buyers.contains(buyer)) {
                return buyer.getName();
            } else {
                return "invalid";
            }
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Buyer createBuyer(String username) throws IOException {
        synchronized(buyers) {
            Buyer buyer = new Buyer(username);
            buyers.add(buyer);
            save(); // may throw an IOException
            return buyer;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteBuyer(String username) throws IOException {
        synchronized(buyers) {
            Buyer buyer = new Buyer(username);
            if (buyers.contains(buyer)) {
                buyers.remove(buyer);
                return save();
            } else {
                return false;
            }
        }
    }
}
