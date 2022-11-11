package com.estore.api.estoreapi.model;

import java.util.HashMap;

// import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Snack {
    // private static final Logger LOG = Logger.getLogger(Snack.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Snack [id=%d, name=%s, description=%s, quantity=%d, price=%f]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("description") private String description;
    @JsonProperty("quantity") private int quantity;
    @JsonProperty("price") private double price;
    @JsonProperty("averageRating") private double averageRating;
    @JsonProperty("ratings") private HashMap<String,Integer> ratings;
    // @JsonProperty("ratingCount") private int ratingCount;


    /**
     * Create a snack with the given id and name
     * @param id The id of the snack
     * @param name The name of the snack
     * @param description The description of the snack
     * @param quantity The quantity of the snack
     * @param price The price of the snack
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Snack(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("description") String description, 
                @JsonProperty("quantity") int quantity, @JsonProperty("price") double price){
        
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.ratings = new HashMap<String,Integer>();
    }

    /**
     * Retrieves the id of the snack
     * @return The id of the snack
     */
    public int getId() {return id;}

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
     * Sets the description of the snack - necessary for JSON object to Java object deserialization
     * @param name The description of the snack
     */
    public void setQuantity(int quantity) {this.quantity = quantity;}

    /**
     * Retrieves the quantity of the snack
     * @return The quantity of the snack
     */
    public int getQuantity() {return quantity;}

    /**
     * Sets the price of the snack - necessary for JSON object to Java object deserialization
     * @param price The desired price of the snack
     */
    public void setPrice(double price) {this.price = price;}

    /**
     * Retrieves the price of the snack
     * @return The price of the snack
     */
    public double getPrice() {return price;}

    /**
     * Sets the average rating of the snack - necessary for JSON object to Java object deserialization
     * @param price The desired rating of the snack
     */
    public void setRatings(HashMap<String,Integer> ratings) {this.ratings = ratings;}

    /**
     * Retrieves the average rating of the snack
     * @return The rating of the snack
     */
    public HashMap<String,Integer> getRatings() {return ratings;}

    public void addRating(String username, int rating) {
        ratings.put(username, rating);
        averageRating = calculateAverageRating();
    }

    public double getAverageRating() {return averageRating;}

    public double calculateAverageRating() {
        int ratingCount = ratings.size();
        int totalPoints = 0;
        for(int rating : ratings.values()) {
            totalPoints += rating;
        }
        return totalPoints / ratingCount;
    }

    // /**
    //  * Sets the number of ratings for the snack - necessary for JSON object to Java object deserialization
    //  * @param price The desired rating count for the snack
    //  */
    // public void setRatingCount(int ratingCount) {this.ratingCount = ratingCount;}

    // /**
    //  * Retrieves the number of ratings for the snack
    //  * @return The rating count for the snack
    //  */
    // public int getRatingCount() {return ratingCount;}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString(){
        return String.format(STRING_FORMAT, id, name, description, quantity, price);
    }
}
