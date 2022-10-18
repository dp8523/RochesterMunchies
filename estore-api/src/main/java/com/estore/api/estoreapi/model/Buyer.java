package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Buyer {

    // Package private for tests
    static final String STRING_FORMAT = "Buyer [username=%s]";

    @JsonProperty("username") private String username;
    // TODO: add shopping cart attribute and methods

    /**
     * Create a Buyer with the given username
     * @param username The username of the snack
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Buyer(@JsonProperty("username") String username){
        this.username = username;
    }

    /**
     * Sets the username of the Buyer
     * @param username The username of the Buyer
     */
    public void setName(String username) {this.username = username;}

    /**
     * Retrieves the username of the Buyer
     * @return The username of the Buyer
     */
    public String getName() {return username;}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString(){
        return String.format(STRING_FORMAT, username);
    }
}
