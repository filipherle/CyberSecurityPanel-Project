package model;

// Represents a Company with a name, an industry and a risk rating
public class Company {

    private String companyName;
    private String companyIndustry;
    private String riskRating;

    // REQUIRES: companyName, companyIndustry and riskRating are not empty.
    //           companyName must also be one word with no spaces.
    // MODIFIES: this
    // EFFECTS: Creates a new Company object with a name, industry and risk rating
    public Company(String companyName, String companyIndustry, String riskRating) {
        this.companyName = companyName;
        this.companyIndustry = companyIndustry;
        this.riskRating = riskRating;
    }

    // EFFECTS: returns the companies industry
    public String getIndustry() {
        return this.companyIndustry; //stub
    }

    // EFFECTS: returns the companies risk rating
    public String getRiskRating() {
        return this.riskRating; //stub
    }

    // EFFECTS: returns the companies name
    public String getCompanyName() {
        return this.companyName; //stub
    }

    // MODIFIES: this
    // EFFECTS: sets/changes the companies name
    public void setCompanyName(String name) {
        this.companyName = name;
    }

    // MODIFIES: this
    // EFFECTS: sets/changes the companies risk rating
    public void setRiskRating(String riskRating) {
        this.riskRating = riskRating;
    }

    // MODIFIES: this
    // EFFECTS: sets/changes the companies industry
    public void setCompanyIndustry(String industry) {
        this.companyIndustry = industry;
    }

}
