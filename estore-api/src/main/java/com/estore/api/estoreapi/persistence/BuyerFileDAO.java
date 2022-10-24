package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Buyer;

@Component
public class BuyerFileDAO implements BuyerDAO {

    Map<String,Buyer> buyers;   // Provides a local cache of the snack objects
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

    private Buyer[] getBuyersArray() {
        return getBuyersArray(null);
    }

    private Buyer[] getBuyersArray(String containsText) { // if containsText == null, no filter
        ArrayList<Buyer> buyerArrayList = new ArrayList<>();

        for (Buyer buyer : buyers.values()) {
            if (containsText == null || buyer.retrieveUsername().contains(containsText)) {
                buyerArrayList.add(buyer);
            }
        }

        Buyer[] buyerArray = new Buyer[buyerArrayList.size()];
        buyerArrayList.toArray(buyerArray);
        return buyerArray;
    }

    /**
     * Saves the {@linkplain Buyer buyer} from the set into the file as an array of JSON objects
     * 
     * @return true if the {@link Buyer buyer} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Buyer[] buyerArray = getBuyersArray();

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
        buyers = new TreeMap<>();

        // Deserializes the JSON objects from the file into an array of buyers
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Buyer[] buyerArray = objectMapper.readValue(new File(filename),Buyer[].class);

        // Add each Buyer to the hash set
        for (Buyer buyer : buyerArray) {
            buyers.put(buyer.retrieveUsername(), buyer);
        }
        
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Buyer login(String username) throws IOException {
        synchronized(buyers) {
            if (buyers.containsKey(username)) {
                return buyers.get(username);
            } else {
                return null;
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
            buyers.put(buyer.retrieveUsername(), buyer);
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
            if (buyers.containsKey(buyer.retrieveUsername())) {
                buyers.remove(buyer.retrieveUsernameaName());
                return save();
            } else {
                return false;
            }
        }
    }
}
