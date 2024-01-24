package test;

import exceptions.CompanyAlreadyExists;
import exceptions.CompanyNotInList;
import exceptions.RiskRatingNotCorrect;
import model.CompanyManager;
import model.EventLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Tests for the CompanyManager Class
class CompanyManagerTest {

    private CompanyManager testCompanyManager;

    @BeforeEach
    void runBefore() {
        testCompanyManager = new CompanyManager();
    }

    // tests that the constructor was set up correctly
    @Test
    void testConstructor(){
        assertEquals(0, testCompanyManager.getAllCompanies().size());
    }

    // tests the addition of 1 company
    @Test
    void testAddCompany() throws CompanyAlreadyExists, RiskRatingNotCorrect {
        testCompanyManager.addCompany("Amazon", "technology", "high");
        assertEquals(1, testCompanyManager.getAllCompanies().size());
        assertEquals("Amazon", testCompanyManager.getAllCompanies().get(0).getCompanyName());
        assertEquals("technology", testCompanyManager.getAllCompanies().get(0).getIndustry());
        assertEquals("high", testCompanyManager.getAllCompanies().get(0).getRiskRating());
    }

    // tests the addition of 1 company and the viewAllCompanies method
    @Test
    void testAddCompanyViewAllCompanies() throws CompanyAlreadyExists, RiskRatingNotCorrect {
        testCompanyManager.addCompany("Amazon", "technology", "high");
        assertEquals(1, testCompanyManager.viewAllCompanies().size());
        assertEquals("Amazon", testCompanyManager.viewAllCompanies().get(0).getCompanyName());
        assertEquals("technology", testCompanyManager.viewAllCompanies().get(0).getIndustry());
        assertEquals("high", testCompanyManager.viewAllCompanies().get(0).getRiskRating());
        assertTrue(EventLog.getInstance().iterator().hasNext());
    }

    // tests the addCompany method when the company already exists
    @Test
    void testAddCompanyThatExists() throws CompanyAlreadyExists, RiskRatingNotCorrect {
        testCompanyManager.addCompany("Amazon", "tech", "low");
        try {
            testCompanyManager.addCompany("Amazon", "tech", "low");
            fail();
        } catch (CompanyAlreadyExists e) {
            // pass
        }
    }

    // tests the changeCompanyName method when it tries changing a companies name to one that already exists
    @Test
    void testChangeCompanyThatExists() throws CompanyAlreadyExists, CompanyNotInList, RiskRatingNotCorrect {
        testCompanyManager.addCompany("Amazon", "tech", "low");
        testCompanyManager.addCompany("Apple", "tech", "low");
        try {
            testCompanyManager.changeCompanyName("Apple", "Amazon");
            fail();
        } catch (CompanyAlreadyExists e) {
            // pass
        }
    }

    // tests that the exceptions are caught when the addCompany method tries adding a company with a
    // risk rating that is not allowed and a company name that already exists
    @Test
    void testAddCompanyThatExistsBadRiskRating() throws CompanyAlreadyExists, RiskRatingNotCorrect {
        testCompanyManager.addCompany("Amazon", "tech", "low");
        try {
            testCompanyManager.addCompany("Amazon", "tech", "bad_rating");
            fail();
        } catch (CompanyAlreadyExists | RiskRatingNotCorrect e) {
            // pass
        }
    }

    // tests that the RiskRatingNotCorrect exception is caught when calling getAllCompaniesRiskRating
    @Test
    void testViewCompanyRiskRatingBad() throws CompanyAlreadyExists, RiskRatingNotCorrect {
        testCompanyManager.addCompany("Amazon", "tech", "low");
        try {
            testCompanyManager.getAllCompaniesRiskRating("test");
            fail();
        } catch (RiskRatingNotCorrect e) {
            // pass
        }
    }

    // tests that when adding a company with a risk rating that is not valid, an exception is thrown
    @Test
    void testAddCompanyBadRiskRating() throws CompanyAlreadyExists {
        try {
            testCompanyManager.addCompany("Amazon", "tech", "middle");
            fail();
        } catch (RiskRatingNotCorrect e) {
            // pass
        }
    }

    // tests that when changing a company with a risk rating that is not valid, an exception is thrown
    @Test
    void testSetCompanyBadRiskRating() throws CompanyNotInList, RiskRatingNotCorrect, CompanyAlreadyExists {
        testCompanyManager.addCompany("Amazon", "tech", "low");
        try {
            testCompanyManager.changeCompanyRiskRating("Amazon", "middle");
            fail();
        } catch (RiskRatingNotCorrect e) {
            // pass
        }
    }

    // tests that when viewing companies with an invalid risk rating, an exception is thrown
    @Test
    void testGetAllCompaniesBadRiskRating() throws RiskRatingNotCorrect, CompanyAlreadyExists {
        testCompanyManager.addCompany("Amazon", "tech", "low");
        try {
            testCompanyManager.getAllCompaniesRiskRating("middle");
            fail();
        } catch (RiskRatingNotCorrect e) {
            // pass
        }
    }

    // tests that when trying to remove a company that doesn't exist, an exception is thrown
    @Test
    void testRemoveCompanyNotInList() throws RiskRatingNotCorrect, CompanyAlreadyExists {
        testCompanyManager.addCompany("Amazon", "tech", "low");
        try {
            testCompanyManager.removeCompany("Apple");
            fail();
        } catch (CompanyNotInList e) {
            // pass!
        }
    }

    // tests the addition of multiple companies
    @Test
    void testAddMultipleCompanies() throws CompanyAlreadyExists, RiskRatingNotCorrect {
        assertEquals(0, testCompanyManager.getAllCompanies().size());
        testCompanyManager.addCompany("Amazon", "technology", "high");
        testCompanyManager.addCompany("Nike", "Fashion", "low");
        assertEquals(2, testCompanyManager.getAllCompanies().size());
        testCompanyManager.addCompany("Apple", "tech", "medium");
        assertEquals(3, testCompanyManager.getAllCompanies().size());
        assertEquals("Apple", testCompanyManager.getAllCompanies().get(2).getCompanyName());
        // ensure that items that are added to the list are in the correct order
        assertEquals("Amazon", testCompanyManager.getAllCompanies().get(0).getCompanyName());
        assertEquals("technology", testCompanyManager.getAllCompanies().get(0).getIndustry());
        assertEquals("high", testCompanyManager.getAllCompanies().get(0).getRiskRating());
        assertEquals("Nike", testCompanyManager.getAllCompanies().get(1).getCompanyName());
        assertEquals("low", testCompanyManager.getAllCompanies().get(1).getRiskRating());
        assertEquals("Apple", testCompanyManager.getAllCompanies().get(2).getCompanyName());
        assertEquals("tech", testCompanyManager.getAllCompanies().get(2).getIndustry());

    }

    // tests the removal of 1 company
    @Test
    void testRemoveCompany() throws CompanyNotInList, CompanyAlreadyExists, RiskRatingNotCorrect {
        testCompanyManager.addCompany("Amazon", "technology", "high");
        assertEquals(1, testCompanyManager.getAllCompanies().size());
        assertEquals("Amazon", testCompanyManager.getAllCompanies().get(0).getCompanyName());
        assertEquals("technology", testCompanyManager.getAllCompanies().get(0).getIndustry());
        assertEquals("high", testCompanyManager.getAllCompanies().get(0).getRiskRating());
        // now tests the removal of the company
        testCompanyManager.removeCompany("Amazon");
        assertEquals(0, testCompanyManager.getAllCompanies().size());
    }

    // tests the removal of multiple companies
    @Test
    void testRemoveMultipleCompanies() throws CompanyNotInList, CompanyAlreadyExists, RiskRatingNotCorrect {
        testCompanyManager.addCompany("Amazon", "technology", "high");
        testCompanyManager.addCompany("Nike", "Fashion", "low");
        testCompanyManager.addCompany("Apple", "tech", "medium");
        assertEquals(3, testCompanyManager.getAllCompanies().size());
        // check that the correct item has been removed
        testCompanyManager.removeCompany("Nike");
        assertEquals(2, testCompanyManager.getAllCompanies().size());
        assertEquals("Amazon", testCompanyManager.getAllCompanies().get(0).getCompanyName());
        assertEquals("Apple", testCompanyManager.getAllCompanies().get(1).getCompanyName());
        testCompanyManager.removeCompany("Amazon");
        assertEquals(1, testCompanyManager.getAllCompanies().size());
        assertEquals("Apple", testCompanyManager.getAllCompanies().get(0).getCompanyName());
    }

    // tests that an exception is thrown when the company doesn't exist when removing it
    @Test
    void testCompanyRemoveNone() throws CompanyAlreadyExists, RiskRatingNotCorrect {
        testCompanyManager.addCompany("Amazon", "tech", "low");
        try {
            testCompanyManager.removeCompany("Apple");
            fail();
        } catch (CompanyNotInList e) {
            // pass
        }
    }

    // tests that an exception is thrown when the company does not exist when trying to get info
    @Test
    void testGetCompanyInfoNone() throws CompanyAlreadyExists, RiskRatingNotCorrect {
        testCompanyManager.addCompany("Amazon", "tech", "low");
        try {
            testCompanyManager.getCompanyInfo("Apple");
            fail();
        } catch (CompanyNotInList e) {
            // pass
        }
    }


    // tests that the correct object is added/found when calling the getCompanyInfo method
    @Test
    void testGetCompanyInfo() throws CompanyNotInList, CompanyAlreadyExists, RiskRatingNotCorrect {
        testCompanyManager.addCompany("Amazon", "tech", "low");
        assertEquals("Amazon", testCompanyManager.getCompanyInfo("Amazon").getCompanyName());
        assertEquals("tech", testCompanyManager.getCompanyInfo("Amazon").getIndustry());
        assertEquals("low", testCompanyManager.getCompanyInfo("Amazon").getRiskRating());

    }

    // tests the getCompaniesRiskRating method when there are no companies in the list
    @Test
    void testGetNoCompaniesRiskRating() throws RiskRatingNotCorrect {
        assertEquals(0, testCompanyManager.getAllCompaniesRiskRating("low").size());
        assertEquals(0, testCompanyManager.getAllCompaniesRiskRating("medium").size());
        assertEquals(0, testCompanyManager.getAllCompaniesRiskRating("high").size());

    }

    // tests the getCompaniesRiskRating method when only 1 company is in the list
    @Test
    void testGetOneCompaniesRiskRating() throws CompanyAlreadyExists, RiskRatingNotCorrect {
        testCompanyManager.addCompany("Amazon", "tech", "low");
        assertEquals(1, testCompanyManager.getAllCompaniesRiskRating("low").size());
        assertEquals(0, testCompanyManager.getAllCompaniesRiskRating("medium").size());
        assertEquals(0, testCompanyManager.getAllCompaniesRiskRating("high").size());
        assertEquals("Amazon", testCompanyManager.getAllCompaniesRiskRating("low").get(0).getCompanyName());

    }

    // tests the getCompaniesRiskRating method when multiple companies are in the list
    @Test
    void testGetCompaniesRiskRating() throws CompanyAlreadyExists, RiskRatingNotCorrect {
        testCompanyManager.addCompany("Amazon", "tech", "low");
        testCompanyManager.addCompany("Nike", "fashion", "medium");
        testCompanyManager.addCompany("RGH", "healthcare", "low");
        testCompanyManager.addCompany("Shoppers", "pharma", "high");
        assertEquals(2, testCompanyManager.getAllCompaniesRiskRating("low").size());
        assertEquals("Amazon", testCompanyManager.getAllCompaniesRiskRating("low").get(0).getCompanyName());
        assertEquals("RGH", testCompanyManager.getAllCompaniesRiskRating("low").get(1).getCompanyName());
        assertEquals(1, testCompanyManager.getAllCompaniesRiskRating("medium").size());
        assertEquals("Nike", testCompanyManager.getAllCompaniesRiskRating("medium").get(0).getCompanyName());
        assertEquals(1, testCompanyManager.getAllCompaniesRiskRating("high").size());
        assertEquals("Shoppers", testCompanyManager.getAllCompaniesRiskRating("high").get(0).getCompanyName());

    }

    // tests the getAllCompanies method when there are no companies in the list
    @Test
    void testGetAllCompaniesNone() {
        assertEquals(0, testCompanyManager.getAllCompanies().size());
    }

    // tests the getAllCompanies method when there are multiple companies in the list
    @Test
    void testGetAllCompanies() throws CompanyAlreadyExists, RiskRatingNotCorrect {
        testCompanyManager.addCompany("Amazon", "tech", "low");
        testCompanyManager.addCompany("Nike", "fashion", "medium");
        testCompanyManager.addCompany("RGH", "healthcare", "low");
        testCompanyManager.addCompany("Shoppers", "pharma", "high");
        assertEquals(4, testCompanyManager.getAllCompanies().size());
        assertEquals("Amazon", testCompanyManager.getAllCompanies().get(0).getCompanyName());
        assertEquals("Nike", testCompanyManager.getAllCompanies().get(1).getCompanyName());
        assertEquals("RGH", testCompanyManager.getAllCompanies().get(2).getCompanyName());
        assertEquals("Shoppers", testCompanyManager.getAllCompanies().get(3).getCompanyName());

        assertEquals("tech", testCompanyManager.getAllCompanies().get(0).getIndustry());
        assertEquals("fashion", testCompanyManager.getAllCompanies().get(1).getIndustry());
        assertEquals("healthcare", testCompanyManager.getAllCompanies().get(2).getIndustry());
        assertEquals("pharma", testCompanyManager.getAllCompanies().get(3).getIndustry());

        assertEquals("low", testCompanyManager.getAllCompanies().get(0).getRiskRating());
        assertEquals("medium", testCompanyManager.getAllCompanies().get(1).getRiskRating());
        assertEquals("low", testCompanyManager.getAllCompanies().get(2).getRiskRating());
        assertEquals("high", testCompanyManager.getAllCompanies().get(3).getRiskRating());

    }

    // tests the getSortedCompanies method when there are no companies in the list
    @Test
    void testGetSortedCompaniesNone() {
        assertEquals(0, testCompanyManager.getSortedCompanies().size());

    }

    // tests the getSortedCompanies method when there are multiple companies in the list
    @Test
    void testGetSortedCompaniesMultiple() throws CompanyAlreadyExists, RiskRatingNotCorrect {
        testCompanyManager.addCompany("Amazon", "tech", "low");
        testCompanyManager.addCompany("Nike", "fashion", "medium");
        testCompanyManager.addCompany("RGH", "healthcare", "low");
        testCompanyManager.addCompany("Shoppers", "pharma", "high");
        assertEquals(4, testCompanyManager.getSortedCompanies().size());
        assertEquals("Shoppers", testCompanyManager.getSortedCompanies().get(0).getCompanyName());
        assertEquals("Nike", testCompanyManager.getSortedCompanies().get(1).getCompanyName());
        assertEquals("Amazon", testCompanyManager.getSortedCompanies().get(2).getCompanyName());
        assertEquals("RGH", testCompanyManager.getSortedCompanies().get(3).getCompanyName());

        assertEquals("pharma", testCompanyManager.getSortedCompanies().get(0).getIndustry());
        assertEquals("fashion", testCompanyManager.getSortedCompanies().get(1).getIndustry());
        assertEquals("tech", testCompanyManager.getSortedCompanies().get(2).getIndustry());
        assertEquals("healthcare", testCompanyManager.getSortedCompanies().get(3).getIndustry());

        assertEquals("high", testCompanyManager.getSortedCompanies().get(0).getRiskRating());
        assertEquals("medium", testCompanyManager.getSortedCompanies().get(1).getRiskRating());
        assertEquals("low", testCompanyManager.getSortedCompanies().get(2).getRiskRating());
        assertEquals("low", testCompanyManager.getSortedCompanies().get(3).getRiskRating());

    }

    // tests the getAllCompanies method when there are only companies in the list with the same risk rating
    @Test
    void testGetSortedCompaniesSame() throws CompanyAlreadyExists, RiskRatingNotCorrect {
        testCompanyManager.addCompany("Shoppers", "pharma", "medium");
        testCompanyManager.addCompany("Nike", "fashion", "medium");
        testCompanyManager.addCompany("Amazon", "tech", "medium");
        testCompanyManager.addCompany("RGH", "healthcare", "medium");
        assertEquals(4, testCompanyManager.getSortedCompanies().size());
        assertEquals("Shoppers", testCompanyManager.getSortedCompanies().get(0).getCompanyName());
        assertEquals("Nike", testCompanyManager.getSortedCompanies().get(1).getCompanyName());
        assertEquals("Amazon", testCompanyManager.getSortedCompanies().get(2).getCompanyName());
        assertEquals("RGH", testCompanyManager.getSortedCompanies().get(3).getCompanyName());

        assertEquals("pharma", testCompanyManager.getSortedCompanies().get(0).getIndustry());
        assertEquals("fashion", testCompanyManager.getSortedCompanies().get(1).getIndustry());
        assertEquals("tech", testCompanyManager.getSortedCompanies().get(2).getIndustry());
        assertEquals("healthcare", testCompanyManager.getSortedCompanies().get(3).getIndustry());

        assertEquals("medium", testCompanyManager.getSortedCompanies().get(0).getRiskRating());
        assertEquals("medium", testCompanyManager.getSortedCompanies().get(1).getRiskRating());
        assertEquals("medium", testCompanyManager.getSortedCompanies().get(2).getRiskRating());
        assertEquals("medium", testCompanyManager.getSortedCompanies().get(3).getRiskRating());

    }

    // tests the changeCompanyName method when there is only one company in the list
    @Test
    void testChangeCompanyName() throws CompanyNotInList, CompanyAlreadyExists, RiskRatingNotCorrect {
        testCompanyManager.addCompany("Nike", "fashion", "medium");
        assertEquals("Nike", testCompanyManager.getAllCompanies().get(0).getCompanyName());
        assertEquals(1, testCompanyManager.getAllCompanies().size());
        testCompanyManager.changeCompanyName("Nike", "Apple");
        assertEquals("Apple", testCompanyManager.getAllCompanies().get(0).getCompanyName());
        assertEquals("fashion", testCompanyManager.getAllCompanies().get(0).getIndustry());
        assertEquals("medium", testCompanyManager.getAllCompanies().get(0).getRiskRating());
        assertEquals(1, testCompanyManager.getAllCompanies().size());

    }

    // tests the changeCompanyName method when multiple companies in the list need their names changed
    @Test
    void testMultipleChangeCompanyName() throws CompanyNotInList, CompanyAlreadyExists, RiskRatingNotCorrect {
        testCompanyManager.addCompany("Nike", "fashion", "high");
        testCompanyManager.addCompany("Amazon", "tech", "medium");
        testCompanyManager.addCompany("RGH", "healthcare", "low");
        assertEquals(3, testCompanyManager.getAllCompanies().size());

        assertEquals("Nike", testCompanyManager.getAllCompanies().get(0).getCompanyName());
        assertEquals("Amazon", testCompanyManager.getAllCompanies().get(1).getCompanyName());
        assertEquals("RGH", testCompanyManager.getAllCompanies().get(2).getCompanyName());
        testCompanyManager.changeCompanyName("Amazon", "Apple");
        assertEquals("Nike", testCompanyManager.getAllCompanies().get(0).getCompanyName());
        assertEquals("Apple", testCompanyManager.getAllCompanies().get(1).getCompanyName());
        assertEquals("tech", testCompanyManager.getAllCompanies().get(1).getIndustry());
        assertEquals("medium", testCompanyManager.getAllCompanies().get(1).getRiskRating());
        assertEquals("RGH", testCompanyManager.getAllCompanies().get(2).getCompanyName());
        testCompanyManager.changeCompanyName("RGH", "Google");
        assertEquals("Nike", testCompanyManager.getAllCompanies().get(0).getCompanyName());
        assertEquals("Apple", testCompanyManager.getAllCompanies().get(1).getCompanyName());
        assertEquals("tech", testCompanyManager.getAllCompanies().get(1).getIndustry());
        assertEquals("medium", testCompanyManager.getAllCompanies().get(1).getRiskRating());
        assertEquals("Google", testCompanyManager.getAllCompanies().get(2).getCompanyName());
        assertEquals("healthcare", testCompanyManager.getAllCompanies().get(2).getIndustry());
        assertEquals("low", testCompanyManager.getAllCompanies().get(2).getRiskRating());
        assertEquals(3, testCompanyManager.getAllCompanies().size());

    }

    // tests the changeCompanyIndustry method when there are is only 1 company in the list
    @Test
    void testChangeCompanyIndustry() throws CompanyNotInList, CompanyAlreadyExists, RiskRatingNotCorrect {
        testCompanyManager.addCompany("Nike", "fashion", "medium");
        assertEquals("fashion", testCompanyManager.getAllCompanies().get(0).getIndustry());
        assertEquals(1, testCompanyManager.getAllCompanies().size());
        testCompanyManager.changeCompanyIndustry("Nike", "clothing");
        assertEquals("Nike", testCompanyManager.getAllCompanies().get(0).getCompanyName());
        assertEquals("clothing", testCompanyManager.getAllCompanies().get(0).getIndustry());
        assertEquals("medium", testCompanyManager.getAllCompanies().get(0).getRiskRating());
        assertEquals(1, testCompanyManager.getAllCompanies().size());

    }

    // tests the changeCompanyIndustry method when there are multiple companies in the list
    @Test
    void testMultipleChangeCompanyIndustry() throws CompanyNotInList, CompanyAlreadyExists, RiskRatingNotCorrect {
        testCompanyManager.addCompany("Nike", "fashion", "high");
        testCompanyManager.addCompany("Amazon", "tech", "medium");
        testCompanyManager.addCompany("RGH", "healthcare", "low");
        assertEquals(3, testCompanyManager.getAllCompanies().size());

        assertEquals("fashion", testCompanyManager.getAllCompanies().get(0).getIndustry());
        assertEquals("tech", testCompanyManager.getAllCompanies().get(1).getIndustry());
        assertEquals("healthcare", testCompanyManager.getAllCompanies().get(2).getIndustry());
        testCompanyManager.changeCompanyIndustry("Amazon", "shopping");
        assertEquals("Nike", testCompanyManager.getAllCompanies().get(0).getCompanyName());
        assertEquals("Amazon", testCompanyManager.getAllCompanies().get(1).getCompanyName());
        assertEquals("shopping", testCompanyManager.getAllCompanies().get(1).getIndustry());
        assertEquals("medium", testCompanyManager.getAllCompanies().get(1).getRiskRating());
        assertEquals("RGH", testCompanyManager.getAllCompanies().get(2).getCompanyName());
        testCompanyManager.changeCompanyIndustry("RGH", "pharma");
        assertEquals("Nike", testCompanyManager.getAllCompanies().get(0).getCompanyName());
        assertEquals("Amazon", testCompanyManager.getAllCompanies().get(1).getCompanyName());
        assertEquals("shopping", testCompanyManager.getAllCompanies().get(1).getIndustry());
        assertEquals("medium", testCompanyManager.getAllCompanies().get(1).getRiskRating());
        assertEquals("RGH", testCompanyManager.getAllCompanies().get(2).getCompanyName());
        assertEquals("pharma", testCompanyManager.getAllCompanies().get(2).getIndustry());
        assertEquals("low", testCompanyManager.getAllCompanies().get(2).getRiskRating());
        assertEquals(3, testCompanyManager.getAllCompanies().size());

    }

    // tests the changeCompanyRiskRating method when there is only 1 company in the list
    @Test
    void testChangeCompanyRiskRating() throws CompanyNotInList, CompanyAlreadyExists, RiskRatingNotCorrect {
        testCompanyManager.addCompany("Nike", "fashion", "medium");
        assertEquals("medium", testCompanyManager.getAllCompanies().get(0).getRiskRating());
        assertEquals(1, testCompanyManager.getAllCompanies().size());
        assertEquals(0, testCompanyManager.getAllCompaniesRiskRating("low").size());
        assertEquals(1, testCompanyManager.getAllCompaniesRiskRating("medium").size());
        assertEquals(0, testCompanyManager.getAllCompaniesRiskRating("high").size());
        testCompanyManager.changeCompanyRiskRating("Nike", "low");
        assertEquals("Nike", testCompanyManager.getAllCompanies().get(0).getCompanyName());
        assertEquals("fashion", testCompanyManager.getAllCompanies().get(0).getIndustry());
        assertEquals("low", testCompanyManager.getAllCompanies().get(0).getRiskRating());
        assertEquals(1, testCompanyManager.getAllCompanies().size());


    }

    // tests the changeCompanyRiskRating method when there are multiple companies in the list
    @Test
    void testMultipleChangeCompanyRiskRating() throws CompanyNotInList, CompanyAlreadyExists, RiskRatingNotCorrect {
        testCompanyManager.addCompany("Nike", "fashion", "high");
        testCompanyManager.addCompany("Amazon", "tech", "medium");
        testCompanyManager.addCompany("RGH", "healthcare", "low");
        assertEquals(3, testCompanyManager.getAllCompanies().size());
        assertEquals(1, testCompanyManager.getAllCompaniesRiskRating("low").size());
        assertEquals(1, testCompanyManager.getAllCompaniesRiskRating("medium").size());
        assertEquals(1, testCompanyManager.getAllCompaniesRiskRating("high").size());

        assertEquals("fashion", testCompanyManager.getAllCompanies().get(0).getIndustry());
        assertEquals("tech", testCompanyManager.getAllCompanies().get(1).getIndustry());
        assertEquals("healthcare", testCompanyManager.getAllCompanies().get(2).getIndustry());
        testCompanyManager.changeCompanyRiskRating("Amazon", "low");
        assertEquals(2, testCompanyManager.getAllCompaniesRiskRating("low").size());
        assertEquals(0, testCompanyManager.getAllCompaniesRiskRating("medium").size());
        assertEquals(1, testCompanyManager.getAllCompaniesRiskRating("high").size());
        assertEquals("Nike", testCompanyManager.getAllCompanies().get(0).getCompanyName());
        assertEquals("Amazon", testCompanyManager.getAllCompanies().get(1).getCompanyName());
        assertEquals("tech", testCompanyManager.getAllCompanies().get(1).getIndustry());
        assertEquals("low", testCompanyManager.getAllCompanies().get(1).getRiskRating());
        assertEquals("RGH", testCompanyManager.getAllCompanies().get(2).getCompanyName());

        testCompanyManager.changeCompanyRiskRating("Nike", "medium");
        assertEquals(2, testCompanyManager.getAllCompaniesRiskRating("low").size());
        assertEquals(1, testCompanyManager.getAllCompaniesRiskRating("medium").size());
        assertEquals(0, testCompanyManager.getAllCompaniesRiskRating("high").size());
        assertEquals("Nike", testCompanyManager.getAllCompanies().get(0).getCompanyName());
        assertEquals("fashion", testCompanyManager.getAllCompanies().get(0).getIndustry());
        assertEquals("medium", testCompanyManager.getAllCompanies().get(0).getRiskRating());
        assertEquals("Amazon", testCompanyManager.getAllCompanies().get(1).getCompanyName());
        assertEquals("tech", testCompanyManager.getAllCompanies().get(1).getIndustry());
        assertEquals("low", testCompanyManager.getAllCompanies().get(1).getRiskRating());
        assertEquals("RGH", testCompanyManager.getAllCompanies().get(2).getCompanyName());
        assertEquals("healthcare", testCompanyManager.getAllCompanies().get(2).getIndustry());
        assertEquals("low", testCompanyManager.getAllCompanies().get(2).getRiskRating());
        assertEquals(3, testCompanyManager.getAllCompanies().size());

    }

    // tests adding no companies from a JSON file
    @Test
    void testGetNoCompaniesFromJson() {
        try {
            testCompanyManager.getCompaniesFromJson("testReaderEmptyFile");
            assertEquals(0, testCompanyManager.getAllCompanies().size());
        } catch (IOException e) {
            fail();
        }
    }

    // tests adding no companies from a JSON file
    @Test
    void testJsonFileNoExist() {
        try {
            testCompanyManager.getCompaniesFromJson("i_do_not_exist");
            fail();
            //assertEquals(0, testCompanyManager.getAllCompanies().size());
        } catch (IOException e) {
            // pass
        }
    }

    // tests adding 2 companies to a list
    @Test
    void testGetCompaniesFromJson() {
        try {
            assertEquals(0, testCompanyManager.getAllCompanies().size());
            testCompanyManager.getCompaniesFromJson("testReaderGeneralCompanies");
            assertEquals(2, testCompanyManager.getAllCompanies().size());
            assertEquals("Apple", testCompanyManager.getAllCompanies().get(0).getCompanyName());
            assertEquals("Technology", testCompanyManager.getAllCompanies().get(0).getIndustry());
            assertEquals("low", testCompanyManager.getAllCompanies().get(0).getRiskRating());
            assertEquals("Nike", testCompanyManager.getAllCompanies().get(1).getCompanyName());
        } catch (IOException e) {
            fail();
        }
    }

    // tests adding to the list of companies but a company already exists
    @Test
    void testGetCompaniesThatExistFromJson() throws RiskRatingNotCorrect, CompanyAlreadyExists {
        try {
            assertEquals(0, testCompanyManager.getAllCompanies().size());
            testCompanyManager.addCompany("Apple", "Technology", "low");
            assertEquals(1, testCompanyManager.getAllCompanies().size());
            testCompanyManager.getCompaniesFromJson("testReaderGeneralCompanies");
            assertEquals(2, testCompanyManager.getAllCompanies().size());
            assertEquals("Apple", testCompanyManager.getAllCompanies().get(0).getCompanyName());
            assertEquals("Technology", testCompanyManager.getAllCompanies().get(0).getIndustry());
            assertEquals("low", testCompanyManager.getAllCompanies().get(0).getRiskRating());
            assertEquals("Nike", testCompanyManager.getAllCompanies().get(1).getCompanyName());
        } catch (IOException e) {
            fail();
        }
    }

    // tests adding a company from a json file that has a bad risk rating
    @Test
    void testGetCompaniesFromJsonBadRiskRating() {
        try {
            assertEquals(0, testCompanyManager.getAllCompanies().size());
            testCompanyManager.getCompaniesFromJson("testBadRiskRating");
            assertEquals(1, testCompanyManager.getAllCompanies().size());
            assertEquals("Nike", testCompanyManager.getAllCompanies().get(0).getCompanyName());
        } catch (IOException e) {
            fail();
        }
    }
}