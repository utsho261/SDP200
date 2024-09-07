package BBMS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import com.toedter.calendar.JDateChooser;

public class BloodBankManagementSystem {

    private static JTable donerTable;
    private static JTable stockTable;
    private static JTable donationTable;
    private static JTable patientRequestTable;

    private static final String[] BLOOD_GROUPS = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};

    public static void main(String[] args) {
        JFrame frame = new JFrame("Blood Bank Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome to Blood Bank Management System", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 20));
        frame.add(welcomeLabel, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();

        // Donor Panel
        JPanel donerPanel = new JPanel(new BorderLayout());
        donerTable = new JTable(new DefaultTableModel(new String[]{"DonerID", "Name", "BloodGroup", "PhoneNumber", "Email"}, 0));
        donerPanel.add(new JScrollPane(donerTable), BorderLayout.CENTER);

        JPanel donerButtonPanel = new JPanel();
        JTextField nameField = new JTextField(10);
        JComboBox<String> bloodGroupCombo = new JComboBox<>(BLOOD_GROUPS);
        JTextField phoneField = new JTextField(10);
        JTextField emailField = new JTextField(10);
        JButton addDonerButton = new JButton("Add Doner");
        donerButtonPanel.add(new JLabel("Name:"));
        donerButtonPanel.add(nameField);
        donerButtonPanel.add(new JLabel("Blood Group:"));
        donerButtonPanel.add(bloodGroupCombo);
        donerButtonPanel.add(new JLabel("Phone:"));
        donerButtonPanel.add(phoneField);
        donerButtonPanel.add(new JLabel("Email:"));
        donerButtonPanel.add(emailField);
        donerButtonPanel.add(addDonerButton);
        donerPanel.add(donerButtonPanel, BorderLayout.SOUTH);

        // BloodStock Panel
        JPanel stockPanel = new JPanel(new BorderLayout());
        stockTable = new JTable(new DefaultTableModel(new String[]{"BloodGroup", "Quantity"}, 0));
        stockPanel.add(new JScrollPane(stockTable), BorderLayout.CENTER);

        JPanel stockButtonPanel = new JPanel();
        JButton refreshStockButton = new JButton("Refresh Stock");
        stockButtonPanel.add(refreshStockButton);
        stockPanel.add(stockButtonPanel, BorderLayout.SOUTH);

        // Donations Panel
        JPanel donationPanel = new JPanel(new BorderLayout());
        donationTable = new JTable(new DefaultTableModel(new String[]{"DonationID", "DonerID", "BloodGroup", "DonationDate", "Quantity"}, 0));
        donationPanel.add(new JScrollPane(donationTable), BorderLayout.CENTER);

        JPanel donationButtonPanel = new JPanel();
        JTextField donerIDField = new JTextField(10);
        JDateChooser donationDateChooser = new JDateChooser();
        JTextField donationQuantityField = new JTextField(10);
        JButton addDonationButton = new JButton("Add Donation");
        donationButtonPanel.add(new JLabel("Doner ID:"));
        donationButtonPanel.add(donerIDField);
        donationButtonPanel.add(new JLabel("Donation Date:"));
        donationButtonPanel.add(donationDateChooser);
        donationButtonPanel.add(new JLabel("Quantity:"));
        donationButtonPanel.add(donationQuantityField);
        donationButtonPanel.add(addDonationButton);
        donationPanel.add(donationButtonPanel, BorderLayout.SOUTH);

        // PatientRequest Panel
        JPanel patientRequestPanel = new JPanel(new BorderLayout());
        patientRequestTable = new JTable(new DefaultTableModel(new String[]{"RequestID", "PatientID", "BloodGroup", "Quantity", "RequestDate", "Status"}, 0));
        patientRequestPanel.add(new JScrollPane(patientRequestTable), BorderLayout.CENTER);

        JPanel patientRequestButtonPanel = new JPanel();
        JTextField patientIDField = new JTextField(10);
        JComboBox<String> requestBloodGroupCombo = new JComboBox<>(BLOOD_GROUPS);
        JTextField requestQuantityField = new JTextField(10);
        JDateChooser requestDateChooser = new JDateChooser();
        JButton addRequestButton = new JButton("Add Request");
        patientRequestButtonPanel.add(new JLabel("Patient ID:"));
        patientRequestButtonPanel.add(patientIDField);
        patientRequestButtonPanel.add(new JLabel("Blood Group:"));
        patientRequestButtonPanel.add(requestBloodGroupCombo);
        patientRequestButtonPanel.add(new JLabel("Quantity:"));
        patientRequestButtonPanel.add(requestQuantityField);
        patientRequestButtonPanel.add(new JLabel("Request Date:"));
        patientRequestButtonPanel.add(requestDateChooser);
        patientRequestButtonPanel.add(addRequestButton);
        patientRequestPanel.add(patientRequestButtonPanel, BorderLayout.SOUTH);

        // Add Tabs
        tabs.addTab("Doners", donerPanel);
        tabs.addTab("Blood Stock", stockPanel);
        tabs.addTab("Donations", donationPanel);
        tabs.addTab("Patient Requests", patientRequestPanel);

        frame.add(tabs, BorderLayout.CENTER);

        // Add Button Actions
        addDonerButton.addActionListener(e -> {
            String name = nameField.getText();
            String bloodGroup = (String) bloodGroupCombo.getSelectedItem();
            String phone = phoneField.getText();
            String email = emailField.getText();
            try {
                Doner.addDoner(name, bloodGroup, phone, email);
                refreshDonerTable();
                nameField.setText("");
                bloodGroupCombo.setSelectedIndex(0);
                phoneField.setText("");
                emailField.setText("");
                JOptionPane.showMessageDialog(frame, "Doner added successfully.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Error adding doner: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        addDonationButton.addActionListener(e -> {
            int donerID = Integer.parseInt(donerIDField.getText());
            java.sql.Date donationDate = new java.sql.Date(donationDateChooser.getDate().getTime());
            int quantity = Integer.parseInt(donationQuantityField.getText());

            try {
                Donations.addDonation(donerID, donationDate.toString(), quantity);
                refreshDonationTable();
                donerIDField.setText("");
                donationDateChooser.setDate(null);
                donationQuantityField.setText("");
                JOptionPane.showMessageDialog(frame, "Donation added successfully.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Error adding donation: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        addRequestButton.addActionListener(e -> {
            int patientID = Integer.parseInt(patientIDField.getText());
            String bloodGroup = (String) requestBloodGroupCombo.getSelectedItem();
            int quantity = Integer.parseInt(requestQuantityField.getText());
            java.sql.Date requestDate = new java.sql.Date(requestDateChooser.getDate().getTime());

            try {
                boolean isSuccessful = PatientRequests.processRequest(patientID, bloodGroup, quantity, requestDate);
                refreshPatientRequestTable();
                patientIDField.setText("");
                requestBloodGroupCombo.setSelectedIndex(0);
                requestQuantityField.setText("");
                requestDateChooser.setDate(null);
                if (isSuccessful) {
                    JOptionPane.showMessageDialog(frame, "Patient request processed successfully.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Not enough blood available in stock.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Error processing request: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        refreshStockButton.addActionListener(e -> refreshStockTable());

        // Refresh tables
        refreshDonerTable();
        refreshStockTable();
        refreshDonationTable();
        refreshPatientRequestTable();

        frame.setVisible(true);
    }

    private static void refreshDonerTable() {
        try {
            ResultSet rs = Doner.getDoners();
            DefaultTableModel model = (DefaultTableModel) donerTable.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("DonerID"),
                        rs.getString("Name"),
                        rs.getString("BloodGroup"),
                        rs.getString("PhoneNumber"),
                        rs.getString("Email")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void refreshStockTable() {
        try {
            ResultSet rs = BloodStock.getBloodStock();
            DefaultTableModel model = (DefaultTableModel) stockTable.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("BloodGroup"),
                        rs.getInt("Quantity")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void refreshDonationTable() {
        try {
            ResultSet rs = Donations.getDonations();
            DefaultTableModel model = (DefaultTableModel) donationTable.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("DonationID"),
                        rs.getInt("DonerID"),
                        rs.getString("BloodGroup"),
                        rs.getDate("DonationDate"),
                        rs.getInt("Quantity")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void refreshPatientRequestTable() {
        try {
            ResultSet rs = PatientRequests.getPatientRequests();
            DefaultTableModel model = (DefaultTableModel) patientRequestTable.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("RequestID"),
                        rs.getInt("PatientID"),
                        rs.getString("BloodGroup"),
                        rs.getInt("Quantity"),
                        rs.getDate("RequestDate"),
                        rs.getString("Status")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
