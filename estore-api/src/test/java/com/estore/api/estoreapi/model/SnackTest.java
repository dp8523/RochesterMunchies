package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        String expected_name = "Wi-Fire";

        // Invoke
        Snack snack = new Snack(expected_id,expected_name);

        // Analyze
        assertEquals(expected_id,snack.getId());
        assertEquals(expected_name,snack.getName());
    }

    @Test
    public void testName() {
        // Setup
        int id = 99;
        String name = "Wi-Fire";
        Snack snack = new Snack(id,name);

        String expected_name = "Galactic Agent";

        // Invoke
        snack.setName(expected_name);

        // Analyze
        assertEquals(expected_name,snack.getName());
    }

    @Test
    public void testToString() {
        // Setup
        int id = 99;
        String name = "Wi-Fire";
        String expected_string = String.format(Snack.STRING_FORMAT,id,name);
        Snack snack = new Snack(id,name);

        // Invoke
        String actual_string = snack.toString();

        // Analyze
        assertEquals(expected_string,actual_string);
    }
}