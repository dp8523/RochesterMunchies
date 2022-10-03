package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.Snack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Hero File DAO class
 * 
 * @author SWEN Faculty
 */
@Tag("Persistence-tier")
public class SnackFileDAOTest {
    SnackFileDAO snackFileDAO;
    Snack[] testSnacks;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupSnackFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testSnacks = new Snack[3];
        testSnacks[0] = new Snack(99,"Wi-Fire");
        testSnacks[1] = new Snack(100,"Galactic Agent");
        testSnacks[2] = new Snack(101,"Ice Gladiator");

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the hero array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Snack[].class))
                .thenReturn(testSnacks);
        snackFileDAO = new SnackFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetSnacks() {
        // Invoke
        Snack[] heroes = snackFileDAO.getSnacks();

        // Analyze
        assertEquals(heroes.length,testSnacks.length);
        for (int i = 0; i < testSnacks.length;++i)
            assertEquals(heroes[i],testSnacks[i]);
    }

    @Test
    public void testFindSnacks() {
        // Invoke
        Snack[] heroes = snackFileDAO.findSnacks("la");

        // Analyze
        assertEquals(heroes.length,2);
        assertEquals(heroes[0],testSnacks[1]);
        assertEquals(heroes[1],testSnacks[2]);
    }

    @Test
    public void testGetSnack() {
        // Invoke
        Snack hero = snackFileDAO.getSnack(99);

        // Analzye
        assertEquals(hero,testSnacks[0]);
    }

    @Test
    public void testDeleteSnack() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> snackFileDAO.deleteSnack(99),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test heroes array - 1 (because of the delete)
        // Because heroes attribute of HeroFileDAO is package private
        // we can access it directly
        assertEquals(snackFileDAO.snacks.size(),testSnacks.length-1);
    }

    @Test
    public void testCreateSnack() {
        // Setup
        Snack snack = new Snack(102,"Wonder-Person");

        // Invoke
        Snack result = assertDoesNotThrow(() -> snackFileDAO.createSnack(snack),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Snack actual = snackFileDAO.getSnack(snack.getId());
        assertEquals(actual.getId(),snack.getId());
        assertEquals(actual.getName(),snack.getName());
    }

    @Test
    public void testUpdateSnack() {
        // Setup
        Snack snack = new Snack(99,"Galactic Agent");

        // Invoke
        Snack result = assertDoesNotThrow(() -> snackFileDAO.updateSnack(snack),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Snack actual = snackFileDAO.getSnack(snack.getId());
        assertEquals(actual,snack);
    }

    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Snack[].class));

        Snack hero = new Snack(102,"Wi-Fire");

        assertThrows(IOException.class,
                        () -> snackFileDAO.createSnack(hero),
                        "IOException not thrown");
    }

    @Test
    public void testGetSnackNotFound() {
        // Invoke
        Snack snack = snackFileDAO.getSnack(98);

        // Analyze
        assertEquals(snack,null);
    }

    @Test
    public void testDeleteSnackNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> snackFileDAO.deleteSnack(98),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(snackFileDAO.snacks.size(),testSnacks.length);
    }

    @Test
    public void testUpdateSnackNotFound() {
        // Setup
        Snack snack = new Snack(98,"Bolt");

        // Invoke
        Snack result = assertDoesNotThrow(() -> snackFileDAO.updateSnack(snack),
                                                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the HeroFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Snack[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new SnackFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}
