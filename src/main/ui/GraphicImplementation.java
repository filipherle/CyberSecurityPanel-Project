package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import com.formdev.flatlaf.FlatDarculaLaf;
import model.Company;
import model.CompanyManager;
import model.Event;
import model.EventLog;
import persistance.JsonWriter;
import ui.managers.ButtonManager;
import ui.managers.MenuManager;

import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.UIManager;
import java.io.IOException;

// The "behind-the-scenes" of the MainGraphicalInterface class. This class provides the implementation
// of the GUI and the look-and-feel of the physical interface of the application
public class GraphicImplementation extends JFrame {
    JPanel comboBoxPanel = new JPanel();

    JLabel viewLabel = new JLabel("View Companies:");
    JLabel addLabel = new JLabel("Add a Company:");
    JLabel removeLabel = new JLabel("Remove a Company:");
    JLabel picLabel;
    JLabel nameLabel = new JLabel("Name:");
    JLabel industryLabel = new JLabel("Industry:");
    JLabel riskRatingLabel = new JLabel("Risk Rating:");

    JTextField nameField = new JTextField(5);
    JTextField industryField = new JTextField(5);
    JTextField riskRatingField = new JTextField(5);

    JMenuBar menuBar = new JMenuBar();
    JMenu menuFile = new JMenu("Menu");

    JMenuItem menuItemExit = new JMenuItem("Exit");
    JMenuItem menuItemSave = new JMenuItem("Save");
    JMenuItem menuItemLoad = new JMenuItem("Load");

    JButton addButton = new JButton("Add Company");
    JButton removeButton = new JButton("Remove Company");
    JButton viewCompaniesButton = new JButton("View Companies");
    JButton viewCompaniesRiskRating = new JButton("View Risk Companies");
    JButton viewSortedCompaniesButton = new JButton("View Sorted Companies");

    String[] s1 = { "high", "medium", "low"};
    String[] companyNames = {};

    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(companyNames);
    JComboBox<String> companyPopup = new JComboBox<>(model);

    JComboBox<String> riskPopup = new JComboBox<>(s1);

    CompanyManager companyManager = new CompanyManager();
    ButtonManager buttonManager = new ButtonManager(this);
    MenuManager menuManager = new MenuManager(this);

    // EFFECTS: runs the program, sets the title, and sets the applications theme
    public GraphicImplementation() {
        super("Cyber Security Console");
        setUpUI();
        setUpMenu();
        setUpItems();
        finishUI();
    }

    // MODIFIES: this
    // EFFECTS: listens for events from buttons performs actions accordingly, if an exception is caught,
    //          an error message pops up with the specified error
    public void buttonActionPerformed(ActionEvent e)  {
        try {
            if (e.getActionCommand().equals("View Companies")) {
                buttonManager.viewCompanies();
            } else if (e.getActionCommand().equals("Add Company")) {
                buttonManager.addCompany();
                updateComboBox();
                nameField.setText("");
                industryField.setText("");
                riskRatingField.setText("");
            } else if (e.getActionCommand().equals("View Sorted Companies")) {
                buttonManager.viewSortedCompanies();
            } else if (e.getActionCommand().equals("View Risk Companies")) {
                buttonManager.getCompaniesRiskRating(riskPopup.getItemAt(riskPopup.getSelectedIndex()));
            } else if (e.getActionCommand().equals("Remove Company")) {
                buttonManager.removeCompany(companyPopup.getItemAt(companyPopup.getSelectedIndex()));
                updateComboBox();
            }
        } catch (Exception d) {
            errorMessage(String.valueOf(d));
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the combo box when a new company is added.
    public void updateComboBox() {
        model.removeAllElements();
        for (Company c : companyManager.getAllCompanies()) {
            model.addElement(c.getCompanyName());
            companyPopup.getModel().setSelectedItem(c.getCompanyName());
        }
    }

    // MODIFIES: this
    // EFFECTS: listens for events from buttons and menu items and performs actions accordingly
    //          if user exits the program, prints the event log to the console
    public void menuActionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Exit")) {
            printLog(EventLog.getInstance());
            menuManager.exitMenu();
        } else if (e.getActionCommand().equals("Save")) {
            menuManager.saveMenu();
        } else if (e.getActionCommand().equals("Load")) {
            menuManager.loadMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: sets up the default operations for the JFrame window.
    //          sets height, width, background, etc.
    private void setUpUI() {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception ex) {
            errorMessage("Failed to initialize theme.");
        }
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(510, 440));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        //setLayout(null); // change this if you want a FlowLayout()
        getContentPane().setBackground(new java.awt.Color(130, 130, 130));
        addWindowListener(windowClosing);
    }

    // EFFECTS: creates an error popup with the specified errorMsg given
    public void errorMessage(String errorMsg) {
        JFrame errorPopup = new JFrame("Error!");
        JOptionPane.showMessageDialog(errorPopup, errorMsg);
    }

    // MODIFIES: this
    // EFFECTS: sets up the menu bar, the menu items and the comboBox (drop-down) menus
    private void setUpMenu() {
        comboBoxPanel.setBackground(new java.awt.Color(130, 130, 130));
        comboBoxPanel.add(riskPopup);
        comboBoxPanel.add(companyPopup);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemLoad);
        menuFile.add(menuItemExit);
        menuBar.add(menuFile);
        setJMenuBar(menuBar);
    }

    // MODIFIES: this
    // EFFECTS: adds all of the visual components to the JFrame
    private void setUpItems() {
        addImage();
        add(nameField);
        add(nameLabel);
        add(industryLabel);
        add(riskRatingLabel);
        add(industryField);
        add(riskRatingField);
        add(viewCompaniesRiskRating);
        add(addButton);
        add(removeButton);
        add(viewCompaniesButton);
        add(viewSortedCompaniesButton);
        add(viewLabel);
        add(addLabel);
        add(removeLabel);
        comboBoxPanel.add(picLabel);
        add(riskPopup);
        add(companyPopup);
        add(comboBoxPanel);
        addActionListeners();
        updateComponents();
        setLocations();
        setSizes();
    }

    // MODIFIES: this
    // EFFECTS: adds action listeners to all the buttons and menu items
    private void addActionListeners() {
        menuItemExit.addActionListener(this::menuActionPerformed);
        menuItemSave.addActionListener(this::menuActionPerformed);
        menuItemLoad.addActionListener(this::menuActionPerformed);
        viewCompaniesButton.addActionListener(this::buttonActionPerformed);
        viewSortedCompaniesButton.addActionListener(this::buttonActionPerformed);
        addButton.addActionListener(this::buttonActionPerformed);
        removeButton.addActionListener(this::buttonActionPerformed);
        viewCompaniesRiskRating.addActionListener(this::buttonActionPerformed);
    }

    // EFFECTS: updates all the components added to the screen with the UI theme
    private void updateComponents() {
        SwingUtilities.updateComponentTreeUI(menuBar);
        SwingUtilities.updateComponentTreeUI(menuFile);
        SwingUtilities.updateComponentTreeUI(riskPopup);
        SwingUtilities.updateComponentTreeUI(companyPopup);
        SwingUtilities.updateComponentTreeUI(addButton);
        SwingUtilities.updateComponentTreeUI(viewCompaniesButton);
        SwingUtilities.updateComponentTreeUI(viewCompaniesRiskRating);
        SwingUtilities.updateComponentTreeUI(removeButton);
        SwingUtilities.updateComponentTreeUI(viewSortedCompaniesButton);
        SwingUtilities.updateComponentTreeUI(comboBoxPanel);
    }

    // MODIFIES: this
    // EFFECTS: sets the locations of all the components to their respective positions on the screen
    private void setLocations() {
        viewLabel.setLocation(30, 240);
        nameLabel.setLocation(60, 95);
        industryLabel.setLocation(167, 95);
        riskRatingLabel.setLocation(268, 95);
        addLabel.setLocation(30, 72);
        removeLabel.setLocation(30, 160);
        nameField.setLocation(30, 125);
        industryField.setLocation(140, 125);
        riskRatingField.setLocation(250, 125);
        addButton.setLocation(360, 125);
        viewCompaniesButton.setLocation(30, 320);
        viewSortedCompaniesButton.setLocation(190, 320);
        viewCompaniesRiskRating.setLocation(190, 280);
        picLabel.setLocation(300, 250);
        riskPopup.setLocation(42, 280);
        companyPopup.setLocation(30, 200);
        removeButton.setLocation(160, 200);
    }

    // MODIFIES: this
    // EFFECTS: sets the sizes (and fonts for labels) for all the components
    private void setSizes() {
        viewLabel.setSize(200, 50);
        viewLabel.setFont(new Font("Arial", Font.PLAIN, 21));
        nameLabel.setSize(120, 50);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        industryLabel.setSize(120, 50);
        industryLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        riskRatingLabel.setSize(200, 50);
        riskRatingLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        addLabel.setSize(200, 50);
        addLabel.setFont(new Font("Arial", Font.PLAIN, 21));
        removeLabel.setSize(200, 50);
        removeLabel.setFont(new Font("Arial", Font.PLAIN, 21));
        nameField.setSize(100, 30);
        industryField.setSize(100, 30);
        riskRatingField.setSize(100, 30);
        addButton.setSize(130, 30);
        viewCompaniesButton.setSize(150, 30);
        viewSortedCompaniesButton.setSize(190, 30);
        viewCompaniesRiskRating.setSize(190, 30);
        picLabel.setSize(160, 150);
        riskPopup.setSize(120, 30);
        companyPopup.setSize(120, 30);
        removeButton.setSize(180, 30);
    }

    // MODIFIES: this
    // EFFECTS: sets up the main menu splashscreen image
    private void addImage() {
        try {
            BufferedImage myPicture = ImageIO.read(new File("./data/SplashScreen.png"));
            Image image = myPicture.getScaledInstance(580, 50, Image.SCALE_DEFAULT);
            picLabel = new JLabel(new ImageIcon(image));
        } catch (IOException e) {
            errorMessage("Error loading image!");
        }
    }

    // MODIFIES: this
    // EFFECTS: finishes setting up the JFrame and makes it visible to the user
    private void finishUI() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // REQUIRES: fileName does not contain any spaces or disallowed characters
    // MODIFIES: this
    // EFFECTS: opens a popup window that saves the current list of companies to a Json file that the user specifies
    //          if any exception is caught, a message dialog pops up
    public void saveToJsonFile(String fileName) {
        try {
            JsonWriter jsonWriter = new JsonWriter(fileName);
            jsonWriter.open();
            jsonWriter.write(companyManager.getAllCompanies());
            jsonWriter.close();
            JOptionPane.showMessageDialog(GraphicImplementation.this,
                    "File saved successfully.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(GraphicImplementation.this, "Error saving file.");
        }
    }

    // EFFECTS: prints the event log out to the console when the window is closed
    WindowAdapter windowClosing = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            printLog(EventLog.getInstance());
            super.windowClosing(e);
        }
    };

    // EFFECTS: returns the text in the name JTextField
    public String getName() {
        return nameField.getText();
    }

    // EFFECTS: returns the text in the industry JTextField
    public String getIndustry() {
        return industryField.getText();
    }

    // EFFECTS: returns the text in the risk rating JTextField
    public String getRiskRating() {
        return riskRatingField.getText();
    }

    // EFFECTS: returns the company manager object
    public CompanyManager getCompanyManager() {
        return companyManager;
    }

    // EFFECTS: prints out the event log to the console
    public void printLog(EventLog el) {
        System.out.println("\nEvent Log: \n");
        for (Event next : el) {
            System.out.println(next.toString() + "\n");
        }
    }
}
