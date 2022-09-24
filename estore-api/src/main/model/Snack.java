import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Snack {
    private static final Logger LOG = Logger.getLogger(Hero.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Snack [name=%s, description=%s]";

    @JsonProperty("name") private String name;
    @JsonProperty("description") String description;

    /**
     * Create a hero with the given id and name
     * @param name The name of the snack
     * @param description The description of the snack
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Snack(@JsonProperty("name") String name, @JsonProperty("description") String description){
        
        this.name = name;
        this.description = description;
    }

    /**
     * Sets the name of the snack - necessary for JSON object to Java object deserialization
     * @param name The name of the snack
     */
    public void setName(String name) {this.name = name;}

    /**
     * Retrieves the name of the snack
     * @return The name of the snack
     */
    public String getName() {return name;}

    /**
     * Sets the description of the snack - necessary for JSON object to Java object deserialization
     * @param name The description of the snack
     */
    public void setDescription(String description) {this.description = description;}

    /**
     * Retrieves the description of the snack
     * @return The description of the snack
     */
    public String getDescription() {return description;}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString(){
        return String.format(STRING_FORMAT, name, description);
    }
}
