package test;

import model.CompanyManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistance.JsonReader;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Represents a test class for the JsonReader class
// Code influenced by the JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReaderTest {

    CompanyManager companyManager;

    @BeforeEach
    void testConstructor() {
        companyManager = new CompanyManager();
    }

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("noSuchFile");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderJsonExtension() {
        JsonReader reader = new JsonReader("testReaderGeneralCompanies.json");
        try {
            reader.read();
            assertEquals(2, reader.getCompanies().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderEmptyFile() {
        JsonReader reader = new JsonReader("testReaderEmptyFile");
        try {
            reader.read();
            assertTrue(reader.getCompanies().isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


    @Test
    void testReaderGeneralCompanies() {
        JsonReader reader = new JsonReader("testReaderGeneralCompanies");
        try {
            reader.read();
            assertEquals(2, reader.getCompanies().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
