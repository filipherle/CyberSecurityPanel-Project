package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import exceptions.CompanyAlreadyExists;
import exceptions.CompanyNotInList;
import exceptions.RiskRatingNotCorrect;
import model.Company;
import model.CompanyManager;
import persistance.*;

// Represents the Security Console that is responsible for all the user inputs and the main menu
public class SecurityConsole {
    private boolean keepRunning = true;

    CompanyManager companyManager = new CompanyManager();

    // EFFECTS: runs the main console
    public SecurityConsole() {
        runConsole();
    }
    
    // EFFECTS: runs the main console by displaying the menu and getting user interaction
    public void runConsole() {
        displayConsole();
        while (keepRunning) {
            System.out.print("root@console> ");
            List<String> userInputs = getUserInputs();
            try {
                if (userInputs.isEmpty() || userInputs.get(0).isEmpty()) {
                    System.out.print("");
                } else if (userInputs.get(0).equals("exit") || userInputs.get(0).equals("quit")) {
                    System.out.println("Exiting... Goodbye!\n");
                    keepRunning = false;
                } else {
                    mainProcessCommand(userInputs); // More difficult commands
                }
            } catch (CompanyNotInList e) {
                System.out.println("Company does not exist.");
            } catch (CompanyAlreadyExists e) {
                System.out.println("Company already exists.");
            } catch (RiskRatingNotCorrect e) {
                System.out.println("Invalid risk rating.");
            }
        }
    }

    // EFFECTS: returns a list of commands given (that were separated by either spaces or commas) and
    // consequently adds them into a list in the order the commands were given
    public List<String> getUserInputs() {
        Scanner userInput = new Scanner(System.in);
        String givenCommands = userInput.nextLine();
        return List.of(givenCommands.split("[ ,]+"));
    }

    // EFFECTS: processes users commands
    public void mainProcessCommand(List<String> userInputs)
            throws CompanyNotInList, CompanyAlreadyExists, RiskRatingNotCorrect {
        if (userInputs.get(0).equals("add") && userInputs.size() == 4) {
            companyManager.addCompany(userInputs.get(1), userInputs.get(2), userInputs.get(3));
            System.out.println(userInputs.get(1) + " added!");
        } else if (userInputs.get(0).equals("menu") && userInputs.size() == 1) {
            displayConsole();
        } else if (userInputs.get(0).equals("info") && userInputs.size() == 2) {
            printCompanyInfo(userInputs.get(1));
        } else if (userInputs.get(0).equals("set_name") && userInputs.size() == 3) {
            companyManager.changeCompanyName(userInputs.get(1), userInputs.get(2));
            System.out.println(userInputs.get(1) + " changed to: " + userInputs.get(2) + "!");
        } else if (userInputs.get(0).equals("set_industry") && userInputs.size() == 3) {
            companyManager.changeCompanyIndustry(userInputs.get(1), userInputs.get(2));
            System.out.println("Industry has been changed to: " + userInputs.get(2) + "!");
        } else if (userInputs.get(0).equals("set_risk") && userInputs.size() == 3) {
            companyManager.changeCompanyRiskRating(userInputs.get(1), userInputs.get(2));
            System.out.println("Risk Rating has been changed to: " + userInputs.get(2) + "!");
        } else if (userInputs.get(0).equals("remove") && userInputs.size() == 2) {
            companyManager.removeCompany(userInputs.get(1));
        } else {
            processCommands(userInputs);
        }
    }

    // EFFECTS: processes more commands from the user
    public void processCommands(List<String> userInputs) throws CompanyNotInList, RiskRatingNotCorrect {
        if (userInputs.get(0).equals("info") && userInputs.size() == 2) {
            printCompanyInfo(userInputs.get(1));
        } else if (userInputs.get(0).equals("view") && userInputs.size() == 2) {
            if (userInputs.get(1).equals("all")) {
                printCompanyList();
            } else if (userInputs.get(1).equals("sorted")) {
                printSortedCompaniesList();
            } else {
                printCompanyRiskList(userInputs.get(1));
            }
        } else if (userInputs.get(0).equals("save") && userInputs.size() == 2) {
            saveToJsonFile(userInputs.get(1));
        } else if (userInputs.get(0).equals("load") && userInputs.size() == 2) {
            loadFromJsonFile(userInputs.get(1));
        } else {
            System.out.println("Please enter a valid option, the correct parameters or type 'menu' to see the menu.");
        }
    }

    // EFFECTS: prints out information about the specific company
    public void printCompanyInfo(String company) throws CompanyNotInList {
        Company companyForInfo = companyManager.getCompanyInfo(company);
        System.out.println("Detailed Information:");
        System.out.println("Company Name: " + companyForInfo.getCompanyName());
        System.out.println("Company Industry: " + companyForInfo.getIndustry());
        System.out.println("Company Risk Rating: " + companyForInfo.getRiskRating());
    }

    // EFFECTS: prints out the name, industry, and risk rating of every company in companyList
    public void printCompanyList() {
        int index = 1;
        for (Company c : companyManager.viewAllCompanies()) {
            System.out.println(index + ". " + "Name: " + c.getCompanyName() + ", Industry: " + c.getIndustry()
                    + ", Risk Rating: " + c.getRiskRating());
            index++;
        }
    }

    // EFFECTS: prints out the name, industry, and risk rating of every company in companyList with
    //          the given risk rating
    public void printCompanyRiskList(String rating) throws RiskRatingNotCorrect {
        int index = 1;
        for (Company c : companyManager.getAllCompaniesRiskRating(rating)) {
            System.out.println(index + ". " + "Name: " + c.getCompanyName() + ", Industry: " + c.getIndustry()
                    + ", Risk Rating: " + c.getRiskRating());
            index++;
        }
    }

    // EFFECTS: prints out the name, industry, and risk rating of every company in companyList with
    //          the given risk rating
    public void printSortedCompaniesList() {
        int index = 1;
        for (Company c : companyManager.getSortedCompanies()) {
            System.out.println(index + ". " + "Name: " + c.getCompanyName() + ", Industry: " + c.getIndustry()
                    + ", Risk Rating: " + c.getRiskRating());
            index++;
        }
    }

    // REQUIRES: fileName does not have any symbols except for letters and numbers
    // EFFECTS: saves the list of companies to the specified file
    private void saveToJsonFile(String fileName) {
        try {
            JsonWriter jsonWriter = new JsonWriter(fileName);
            jsonWriter.open();
            jsonWriter.write(companyManager.getAllCompanies());
            jsonWriter.close();
            if (fileName.contains(".json")) {
                System.out.println("Successfully saved to: " + fileName);
            } else {
                System.out.println("Successfully saved to: " + fileName + ".json");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file.");
        }
    }

    // REQUIRES: fileName does not have any symbols except for letters and numbers
    // MODIFIES: this
    // EFFECTS: reads given JSON file and adds companies to the SecurityConsole
    // if a company with the same name already exists, throws CompanyAlreadyExists exception and doesn't add it
    // if a company has an incorrect riskRating, throws RiskRatingNotCorrect exception
    public void loadFromJsonFile(String fileName) {
        try {
            companyManager.getCompaniesFromJson(fileName);
            if (fileName.contains(".json")) {
                System.out.println("Loaded from " + fileName);
            } else {
                System.out.println("Loaded from " + fileName + ".json");
            }
        } catch (IOException e) {
            System.out.println("File does not exist.");
        }
    }

    // EFFECTS: displays options in the menu for the user to choose from
    public void displayConsole() {
        System.out.println("\nConsole Options:");
        System.out.println("\tadd [name] [industry] [risk_rating]  -> adds company with the given parameters");
        System.out.println("\tremove [name]                        -> removes company with given name from list");
        System.out.println("\tview [all/risk_rating/sorted]        -> views companies based on the given parameters");
        System.out.println("\tset                                  -> changes a companies name/risk rating/industry");
        System.out.println("\t   set_name [old_name] [new_name]       -> changes a companies name");
        System.out.println("\t   set_industry [name] [industry]       -> changes a companies industry");
        System.out.println("\t   set_risk [name] [risk_rating]        -> changes a companies risk rating");
        System.out.println("\tinfo [name]                          -> view detailed information about a company");
        System.out.println("\tsave [file]                          -> save application state to a file");
        System.out.println("\tload [file]                          -> load application state from a file");
        System.out.println("\texit/quit                            -> exits the console");
    }
}
