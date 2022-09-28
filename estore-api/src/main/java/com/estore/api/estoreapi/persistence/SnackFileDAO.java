package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Snack;

@Component
public class SnackFileDAO implements SnackDAO {

    private static final Logger LOG = Logger.getLogger(SnackFileDAO.class.getName());
    Map<Integer,Snack> snacks;   // Provides a local cache of the snack objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Hero
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new snack
    private String filename;    // Filename to read from and write to

    /**
     * Creates a Snack File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public SnackFileDAO(@Value("${snacks.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the snacks from the file
    }

    /**
     * Generates the next id for a new {@linkplain Snack snack}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Snack snacks} from the tree map
     * 
     * @return  The array of {@link Snack snacks}, may be empty
     */
    private Snack[] getSnacksArray() {
        return getSnacksArray(null);
    }

    /**
     * Generates an array of {@linkplain Snack snacks} from the tree map for any
     * {@linkplain Snack snacks} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Snack snacks}
     * in the tree map
     * 
     * @return  The array of {@link Snack snacks}, may be empty
     */
    private Snack[] getSnacksArray(String containsText) { // if containsText == null, no filter
        ArrayList<Snack> snackArrayList = new ArrayList<>();

        for (Snack snack : snacks.values()) {
            if (containsText == null || snack.getName().contains(containsText)) {
                snackArrayList.add(snack);
            }
        }

        Snack[] snackArray = new Snack[snackArrayList.size()];
        snackArrayList.toArray(snackArray);
        return snackArray;
    }


    /**
     * Saves the {@linkplain Snack snack} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Snack snack} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Snack[] snackArray = getSnacksArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),snackArray);
        return true;
    }

    /**
     * Loads {@linkplain Hero heroes} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        snacks = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of heroes
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Snack[] snackArray = objectMapper.readValue(new File(filename),Snack[].class);

        // Add each hero to the tree map and keep track of the greatest id
        for (Snack snack : snackArray) {
            snacks.put(snack.getId(),snack);
            if (snack.getId() > nextId)
                nextId = snack.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Snack[] getSnacks() {
        synchronized(snacks) {
            return getSnacksArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Snack[] findSnacks(String containsText) {
        synchronized(snacks) {
            return getSnacksArray(containsText);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Snack getSnack(int id) {
        synchronized(snacks) {
            if (snacks.containsKey(id))
                return snacks.get(id);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Snack createSnack(Snack snack) throws IOException {
        synchronized(snacks) {
            // We create a new hero object because the id field is immutable
            // and we need to assign the next unique id
            Snack newSnack = new Snack(nextId(),snack.getName(), null);
            snacks.put(newSnack.getId(),newSnack);
            save(); // may throw an IOException
            return newSnack;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Snack updateSnack(Snack snack) throws IOException {
        synchronized(snacks) {
            if (snacks.containsKey(snack.getId()) == false)
                return null;  // snack does not exist

            snacks.put(snack.getId(),snack);
            save(); // may throw an IOException
            return snack;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteSnack(int id) throws IOException {
        synchronized(snacks) {
            if (snacks.containsKey(id)) {
                snacks.remove(id);
                return save();
            }
            else
                return false;
        }
    }
}
