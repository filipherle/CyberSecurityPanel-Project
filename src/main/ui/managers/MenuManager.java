package ui.managers;

import model.CompanyManager;
import ui.GraphicImplementation;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

// Represents the manager class for the menu tab that handles all events when the menu buttons are clicked
public class MenuManager {
    private final CompanyManager companyManager;
    private final GraphicImplementation graphicImplementation;

    // MODIFIES: this
    // EFFECTS: sets up the constructor for the MenuManager class
    public MenuManager(GraphicImplementation graphicImplementation) {
        this.graphicImplementation = graphicImplementation;
        this.companyManager = graphicImplementation.getCompanyManager();
    }

    // EFFECTS: closes the application when this menu item is clicked
    public void exitMenu() {
        System.exit(1);
    }

    // EFFECTS: Saves the current list of companies to the specified JSON file chosen by the user in the save dialog
    public void saveMenu() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save File");
        fileChooser.setCurrentDirectory(new File("./data"));

        int userSelection = fileChooser.showSaveDialog(graphicImplementation);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (fileToSave != null) {
                graphicImplementation.saveToJsonFile(fileToSave.getName());
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the companies from the chosen JSON file to the current
    //          list of companies. if IOException is thrown, errorMsg pops up
    public void loadMenu() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("./data"));
        int result = fileChooser.showOpenDialog(graphicImplementation);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                companyManager.getCompaniesFromJson(selectedFile.getName());
                graphicImplementation.updateComboBox();
                JOptionPane.showMessageDialog(graphicImplementation,
                        "File loaded!");
            } catch (IOException e) {
                graphicImplementation.errorMessage("Error loading file!");
            }
        }
    }
}
