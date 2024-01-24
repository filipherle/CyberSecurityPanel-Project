package test;

//TODO

import exceptions.CompanyAlreadyExists;
import exceptions.RiskRatingNotCorrect;
import model.CompanyManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistance.JsonReader;
import persistance.JsonWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Represents a test class for the JsonWriter class
// Code influenced by the JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonWriterTest {

    CompanyManager companyManager;

    @BeforeEach
    void testConstructor() {
        companyManager = new CompanyManager();
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("hello_I_do_not_exist\0-\\//.json");
            writer.open();
            fail("FileNotFoundException was expected");
        } catch (FileNotFoundException e) {
            // pass
        }
    }

    @Test
    void testWriterWithJsonExtension() {
        try {
            JsonWriter writer = new JsonWriter("testing.json");
            writer.open();
        } catch (FileNotFoundException e) {
            fail("FileNotFoundException was expected");
        }
    }

    @Test
    void testWriterEmpty() {
        try {
            JsonWriter writer = new JsonWriter("testWriterEmptyWorkroom");
            writer.open();
            writer.write(companyManager.getAllCompanies());
            writer.close();

            JsonReader reader = new JsonReader("testWriterEmptyWorkroom");
            reader.read();
            assertEquals(0, reader.getCompanies().size());
            assertEquals(0, companyManager.getAllCompanies().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            companyManager.addCompany("Apple", "Tech", "high");
            companyManager.addCompany("Adidas", "Fashion", "low");
            JsonWriter writer = new JsonWriter("testWriterGeneralCompanies");
            writer.open();
            writer.write(companyManager.getAllCompanies());
            writer.close();

            JsonReader reader = new JsonReader("testWriterGeneralCompanies");
            reader.read();
            assertEquals(2, reader.getCompanies().size());
            assertEquals(2, companyManager.getAllCompanies().size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (CompanyAlreadyExists | RiskRatingNotCorrect e) {
            fail();
        }
    }
}
