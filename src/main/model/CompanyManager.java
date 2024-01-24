package model;

import exceptions.CompanyAlreadyExists;
import exceptions.CompanyNotInList;
import exceptions.RiskRatingNotCorrect;
import persistance.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Represents a CompanyManager class that stores/makes changes to Companies that the Security Company manages.
public class CompanyManager {
    List<Company> companyList;

    // MODIFIES: this
    // EFFECTS: creates a new companyManager object with an empty list of companies
    public CompanyManager() {
        companyList = new ArrayList<>();
    }

    // REQUIRES: companyName, companyIndustry and riskRating are not empty.
    //           companyName must be one word with no spaces
    // MODIFIES: this
    // EFFECTS: adds a company to the list of companies with the given name, industry, and risk rating.
    //          if risk rating is not "high", "medium", or "low", throws RiskRatingNotCorrect Exception.
    //          if companyName is the same as another company in the list, throws CompanyAlreadyExists Exception.
    //          logs the addition of a new company to the event log
    public void addCompany(String companyName, String companyIndustry, String riskRating)
            throws CompanyAlreadyExists, RiskRatingNotCorrect {
        riskRatingNotCorrect(riskRating);
        companyAlreadyExists(companyName);
        Company newCompany = new Company(companyName, companyIndustry, riskRating);
        companyList.add(newCompany);
        EventLog.getInstance().logEvent(new Event("Company '" + companyName + "' added."));
    }

    // REQUIRES: companyName is not empty
    // EFFECTS: Used as a checker for the CompanyAlreadyExists exception
    //          if company name given already exists in the list of companies, throws CompanyAlreadyExists exception.
    private void companyAlreadyExists(String companyName) throws CompanyAlreadyExists {
        for (Company c : companyList) {
            if (c.getCompanyName().equals(companyName)) {
                throw new CompanyAlreadyExists();
            }
        }
    }

    // EFFECTS: Used as a checker for the RiskRatingNotCorrect exception
    //         if given risk rating is not "low", "medium" or "high", throws RiskRatingNotCorrect exception.
    private void riskRatingNotCorrect(String riskRating) throws RiskRatingNotCorrect {
        if (!riskRating.equals("low") && !riskRating.equals("medium") && !riskRating.equals("high")) {
            throw new RiskRatingNotCorrect();
        }
    }

    // MODIFIES: this
    // EFFECTS: removes a company with the given companyName from companyList
    //          logs the removal of a company to the event log
    public void removeCompany(String companyName) throws CompanyNotInList {
        int companyIndex = getIndexFromName(companyName);
        companyList.remove(companyIndex);
        EventLog.getInstance().logEvent(new Event("Company '" + companyName + "' removed."));
    }

    // EFFECTS: get index of a company in companyList from the name of the company
    //          if the company does not exist, throws CompanyNotInList exception.
    private int getIndexFromName(String companyName) throws CompanyNotInList {
        int companyIndex = -1;
        for (Company c : companyList) {
            companyIndex++;
            if (c.getCompanyName().equals(companyName)) {
                return companyIndex;
            }
        }
        throw new CompanyNotInList();
    }

    // MODIFIES: this
    // EFFECTS: adds the companies from a JSON file into the list of companies,
    //          if the company already exists or the risk rating is not correct, it skips the company
    //          logs the loading of a file to the event log
    public void getCompaniesFromJson(String fileName) throws IOException {
        JsonReader jsonReader = new JsonReader(fileName);
        jsonReader.read();
        for (Company c : jsonReader.getCompanies()) {
            try {
                addCompany(c.getCompanyName(), c.getIndustry(), c.getRiskRating());
            } catch (CompanyAlreadyExists | RiskRatingNotCorrect e) {
                // skip company if it already exists or if its risk rating is invalid
            }
        }
        EventLog.getInstance().logEvent(new Event("Companies loaded from file: " + fileName));
    }

    // EFFECTS: returns the correct Company from companyList to fetch information from
    public Company getCompanyInfo(String companyName) throws CompanyNotInList {
        int companyIndex = getIndexFromName(companyName);
        return companyList.get(companyIndex);
    }

    // REQUIRES: riskRating is not empty
    // EFFECTS: prints out and returns all companies in companyList with the given risk rating in the order
    //          they were added to the list
    //          if riskRating given is invalid, throws RiskRatingNotCorrect exception
    //          logs the viewing to the event log
    public List<Company> getAllCompaniesRiskRating(String riskRating) throws RiskRatingNotCorrect {
        List<Company> riskCompanyList = new ArrayList<>();
        riskRatingNotCorrect(riskRating);
        for (Company c : companyList) {
            if (c.getRiskRating().equals(riskRating)) {
                riskCompanyList.add(c);
            }
        }
        EventLog.getInstance().logEvent(new Event("Viewed all companies with a " + riskRating
                + " risk rating"));
        return riskCompanyList;
    }

    // EFFECTS: returns a list of all the companies currently in the list of companies
    public List<Company> getAllCompanies() {
        return companyList;
    }

    // EFFECTS: returns a list of all the companies currently in the list of companies to view
    //          logs the viewing to the event log
    public List<Company> viewAllCompanies() {
        EventLog.getInstance().logEvent(new Event("Viewed all companies."));
        return companyList;
    }

    // EFFECTS: returns all companies in companyList sorted by their riskRating
    //          from high to low. if multiple companies have the same risk rating, whichever one was added first
    //          will be returned first
    //          logs the viewing to the event log
    public List<Company> getSortedCompanies() {
        List<Company> highList = new ArrayList<>();
        List<Company> medList = new ArrayList<>();
        List<Company> lowList = new ArrayList<>();
        for (Company c : companyList) {
            if (c.getRiskRating().equals("high")) {
                highList.add(c);
            } else if (c.getRiskRating().equals("medium")) {
                medList.add(c);
            } else {
                lowList.add(c);
            }
        }
        List<Company> sortedList = new ArrayList<>(highList);
        sortedList.addAll(medList);
        sortedList.addAll(lowList);
        EventLog.getInstance().logEvent(new Event("Viewed the sorted list of companies."));
        return sortedList;
    }

    // MODIFIES: this
    // EFFECTS: changes a companies name
    //          if the oldName given does not exist, throws a CompanyNotInList exception.
    //          if the newName given already exists in the list, throws a CompanyAlreadyExists exception.
    public void changeCompanyName(String oldName, String newName) throws CompanyNotInList, CompanyAlreadyExists {
        companyAlreadyExists(newName);
        int index = getIndexFromName(oldName);
        companyList.get(index).setCompanyName(newName);
    }

    // MODIFIES: this
    // EFFECTS: changes a companies industry to the one given
    public void changeCompanyIndustry(String name, String newIndustry) throws CompanyNotInList {
        int index = getIndexFromName(name);
        companyList.get(index).setCompanyIndustry(newIndustry);
    }

    // MODIFIES: this
    // EFFECTS: changes a companies risk rating to the one given
    //          if newRiskRating is not "low", "medium", or "high", throws riskRatingNotCorrect Exception.
    public void changeCompanyRiskRating(String name, String newRiskRating) throws CompanyNotInList,
            RiskRatingNotCorrect {
        riskRatingNotCorrect(newRiskRating);
        int index = getIndexFromName(name);
        companyList.get(index).setRiskRating(newRiskRating);
    }

}
