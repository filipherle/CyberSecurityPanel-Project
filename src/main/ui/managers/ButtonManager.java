package ui.managers;

import exceptions.CompanyAlreadyExists;
import exceptions.RiskRatingNotCorrect;
import model.Company;
import model.CompanyManager;
import ui.GraphicImplementation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// Represents the manager class for handling all the button click events from the GraphicImplementation class
public class ButtonManager {
    private final CompanyManager companyManager;
    private final GraphicImplementation graphicImplementation;
    private final String[] columnNames = { "Name:", "Industry:", "Risk Rating:" };

    // MODIFIES: this
    // EFFECTS: sets up the constructor for the ButtonManager class
    public ButtonManager(GraphicImplementation graphicImplementation) {
        this.graphicImplementation = graphicImplementation;
        this.companyManager = graphicImplementation.getCompanyManager();
    }

    // MODIFIES: this
    // EFFECTS: takes the company details given in the fields, creates a company, and adds it to the current list
    // of companies. if CompanyAlreadyExists or RiskRatingNotCorrect exception is thrown, errorMsg pops up
    public void addCompany() {
        try {
            String name = graphicImplementation.getName();
            String industry = graphicImplementation.getIndustry();
            String riskRating = graphicImplementation.getRiskRating();
            companyManager.addCompany(name, industry, riskRating);
            JOptionPane.showMessageDialog(graphicImplementation, "Company added!");
        } catch (CompanyAlreadyExists f) {
            graphicImplementation.errorMessage("A company with that name already exists!");
        } catch (RiskRatingNotCorrect d) {
            graphicImplementation.errorMessage("That is not a valid risk rating!");
        }
    }

    // MODIFIES: this
    // EFFECTS: handles the button click event and removes the company from the list with the name specific in
    // the drop-down menu. If company doesn't exist, errorMsg pops up
    public void removeCompany(String name) {
        try {
            companyManager.removeCompany(name);
            JOptionPane.showMessageDialog(graphicImplementation, "Company removed!");
        } catch (Exception e) {
            graphicImplementation.errorMessage("Error!");
        }
    }

    // MODIFIES: this
    // EFFECTS: opens a new window and creates a table with all the companies that are
    // currently in the list of companies, and adds it to the screen.
    public void viewCompanies() {
        JFrame viewCompanyFrame = new JFrame("All Companies:");
        viewCompanyFrame.setPreferredSize(new Dimension(470, 470));
        JPanel mainPanel = new JPanel();

        String[][] companyRow = {};

        DefaultTableModel model = new DefaultTableModel(companyRow, columnNames);
        JTable j = new JTable(model);

        for (Company c : companyManager.viewAllCompanies()) {
            Object[] object = {c.getCompanyName(), c.getIndustry(), c.getRiskRating()};
            model.insertRow(j.getRowCount(), object);
        }

        j.setDefaultEditor(Object.class, null); // makes the table non-editable
        JScrollPane sp = new JScrollPane(j);
        mainPanel.add(sp);
        viewCompanyFrame.add(mainPanel);
        viewCompanyFrame.pack();
        viewCompanyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewCompanyFrame.setLocationRelativeTo(null);
        viewCompanyFrame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: opens a new window and views the companies that are currently in the list of companies
    //  in sorted order (risk rating high to low) in the form of a JTable
    public void viewSortedCompanies() {
        JFrame viewCompanyFrame = new JFrame("Sorted Companies:");
        viewCompanyFrame.setPreferredSize(new Dimension(470, 470));
        JPanel mainPanel = new JPanel();

        String[][] companyRow = {};

        DefaultTableModel model = new DefaultTableModel(companyRow, columnNames);
        JTable j = new JTable(model);

        for (Company c : companyManager.getSortedCompanies()) {
            Object[] object = {c.getCompanyName(), c.getIndustry(), c.getRiskRating()};
            model.insertRow(j.getRowCount(), object);
        }

        j.setDefaultEditor(Object.class, null); // makes the table non-editable
        JScrollPane sp = new JScrollPane(j);
        mainPanel.add(sp);
        viewCompanyFrame.add(mainPanel);
        viewCompanyFrame.pack();
        viewCompanyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewCompanyFrame.setLocationRelativeTo(null);
        viewCompanyFrame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: opens a new window and views the companies that have that specific riskRating in
    // the form of a JTable
    public void getCompaniesRiskRating(String riskRating) throws RiskRatingNotCorrect {
        JFrame viewCompanyFrame = new JFrame("Companies with a " + riskRating + " Risk Rating:");
        viewCompanyFrame.setPreferredSize(new Dimension(470, 470));
        JPanel mainPanel = new JPanel();

        String[][] companyRow = {};

        DefaultTableModel model = new DefaultTableModel(companyRow, columnNames);
        JTable j = new JTable(model);

        for (Company c : companyManager.getAllCompaniesRiskRating(riskRating)) {
            Object[] object = {c.getCompanyName(), c.getIndustry(), c.getRiskRating()};
            model.insertRow(j.getRowCount(), object);
        }

        j.setDefaultEditor(Object.class, null); // makes the table non-editable
        JScrollPane sp = new JScrollPane(j);
        mainPanel.add(sp);
        viewCompanyFrame.add(mainPanel);
        viewCompanyFrame.pack();
        viewCompanyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewCompanyFrame.setLocationRelativeTo(null);
        viewCompanyFrame.setVisible(true);
    }
}
