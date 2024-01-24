package test;

import model.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Tests for the Company Class
public class CompanyTest {

    private Company testCompany;

    @BeforeEach
    void runBefore() {
        testCompany = new Company("Google", "Technology", "Medium");
    }

    @Test
    void testConstructor(){
        assertEquals("Google", testCompany.getCompanyName());
        assertEquals("Technology", testCompany.getIndustry());
        assertEquals("Medium", testCompany.getRiskRating());
    }

    @Test
    void testSetName(){
        assertEquals("Google", testCompany.getCompanyName());
        assertEquals("Technology", testCompany.getIndustry());
        assertEquals("Medium", testCompany.getRiskRating());
        testCompany.setCompanyName("Apple");
        assertEquals("Apple", testCompany.getCompanyName());
        assertEquals("Technology", testCompany.getIndustry());
        assertEquals("Medium", testCompany.getRiskRating());
    }

    @Test
    void testSetIndustry(){
        assertEquals("Google", testCompany.getCompanyName());
        assertEquals("Technology", testCompany.getIndustry());
        assertEquals("Medium", testCompany.getRiskRating());
        testCompany.setCompanyIndustry("Fashion");
        assertEquals("Google", testCompany.getCompanyName());
        assertEquals("Fashion", testCompany.getIndustry());
        assertEquals("Medium", testCompany.getRiskRating());
    }

    @Test
    void testSetRiskRating(){
        assertEquals("Google", testCompany.getCompanyName());
        assertEquals("Technology", testCompany.getIndustry());
        assertEquals("Medium", testCompany.getRiskRating());
        testCompany.setRiskRating("low");
        assertEquals("Google", testCompany.getCompanyName());
        assertEquals("Technology", testCompany.getIndustry());
        assertEquals("low", testCompany.getRiskRating());
    }
}
