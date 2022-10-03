package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.Mockito.description;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Hero class
 * 
 * @author SWEN Faculty
 */
@Tag("Model-tier")
public class SnackTest {
    @Test
    public void testCtor() {
        // Setup
        int expected_id = 99;
        String expected_name = "Camel Balls";
        String expected_description = "Extra Sour Bubble Gum Jawbreaker";
        int expected_quantity = 5;
        double expected_price = 9.99;

        // Invoke
        Snack snack = new Snack(expected_id,expected_name,expected_description,expected_quantity,expected_price);

        // Analyze
        assertEquals(expected_id,snack.getId());
        assertEquals(expected_name,snack.getName());
    }

    @Test
    public void testName() {
        // Setup
        int id = 99;
        String name = "Camel Balls";
        String description = "Extra Sour Bubble Gum Jawbreaker";
        int quantity = 5;
        double price = 9.99;
        Snack snack = new Snack(id,name,description,quantity,price);

        String expected_name = "Animal Crackers";

        // Invoke
        snack.setName(expected_name);

        // Analyze
        assertEquals(expected_name,snack.getName());
    }

    @Test
    public void testToString() {
        // Setup
        int id = 99;
        String name = "Camel Balls";
        String description = "Extra Sour Bubble Gum Jawbreaker";
        int quantity = 5;
        double price = 9.99;
        String expected_string = String.format(Snack.STRING_FORMAT,id,name);
        Snack snack = new Snack(id,name,description,quantity,price);

        // Invoke
        String actual_string = snack.toString();

        // Analyze
        assertEquals(expected_string,actual_string);
    }
}