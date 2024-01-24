package persistance;

import model.Company;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// Represents a class that reads and parses companies from a JSON file.
// Code influenced by the JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReader {
    private final String fileName;
    List<Company> companiesFromJsonList;

    // REQUIRES: fileName does not have any periods or slashes
    // MODIFIES: this
    // EFFECTS: constructs reader to read from given source file
    public JsonReader(String fileName) {
        this.companiesFromJsonList = new ArrayList<>();
        if (fileName.contains(".json")) {
            this.fileName = "./data/" + fileName;
        } else {
            this.fileName = "./data/" + fileName + ".json";
        }
    }

    // EFFECTS: reads JSON from file and calls findCompaniesInJsonList to add them to the list
    //          throws IOException if an error occurs reading data from file
    public void read() throws IOException {
        String jsonData = readFile();
        JSONObject jsonObject = new JSONObject(jsonData);
        findCompaniesInJsonList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile() throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(fileName), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }
        return contentBuilder.toString();
    }

    // EFFECTS: finds and parses companies from the JSON file
    private void findCompaniesInJsonList(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("companies");
        for (Object json : jsonArray) {
            JSONObject nextCompany = (JSONObject) json;
            addCompanyFromJson(nextCompany);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new company and adds it to companiesFromJsonList
    private void addCompanyFromJson(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String industry = jsonObject.getString("industry");
        String riskRating = jsonObject.getString("risk_rating");
        Company newCompany = new Company(name, industry, riskRating);
        companiesFromJsonList.add(newCompany);
    }

    // EFFECTS: returns the list of companies read from the JSON file
    public List<Company> getCompanies() {
        return companiesFromJsonList;
    }

}
