package persistance;

import model.Company;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Represents a class that saves/writes companies into a JSON file
// Code influenced by the JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonWriter {
    private final String fileName;
    private PrintWriter writer;
    private static final int TAB = 4;
    private List<Company> companyList;

    // REQUIRES: fileName does not have any slashes or periods
    // MODIFIES: this
    // EFFECTS: constructs JsonWriter to write to given destination file
    public JsonWriter(String fileName) {
        if (fileName.contains(".json")) {
            this.fileName = "./data/" + fileName;
        } else {
            this.fileName = "./data/" + fileName + ".json";
        }
    }

    // MODIFIES: this
    // EFFECTS: opens the writer and throws FileNotFoundException if the given destination file cannot
    // be opened for writing or found
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(this.fileName);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of CompanyList to destination file as a JsonArray
    public void write(List<Company> allCompanies) {
        companyList = new ArrayList<>(allCompanies);
        JSONObject json = new JSONObject();
        json.put("companies", companiesToJson());
        saveToFile(json.toString(TAB));
    }

    // EFFECTS: takes every company in the companyList and saves it as a JSONObject
    //          then returns a JSONArray of the JSONObjects
    private JSONArray companiesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Company company : companyList) {
            JSONObject json = new JSONObject();
            json.put("name", company.getCompanyName());
            json.put("industry", company.getIndustry());
            json.put("risk_rating", company.getRiskRating());
            jsonArray.put(json);
        }
        return jsonArray;
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

}
