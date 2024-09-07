package BBMS;

import java.sql.*;

public class PatientRequests {
    private static final String DB_URL = "jdbc:mysql://localhost/bloodbank";
    private static final String USER = "root";
    private static final String PASS = "";

    public static boolean processRequest(int patientID, String bloodGroup, int quantity, Date requestDate) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

        // Check stock
        String stockQuery = "SELECT Quantity FROM BloodStock WHERE BloodGroup = ?";
        try (PreparedStatement stockStmt = conn.prepareStatement(stockQuery)) {
            stockStmt.setString(1, bloodGroup);
            ResultSet stockRs = stockStmt.executeQuery();
            if (stockRs.next() && stockRs.getInt("Quantity") >= quantity) {
                // Update BloodStock
                String updateStockQuery = "UPDATE BloodStock SET Quantity = Quantity - ? WHERE BloodGroup = ?";
                try (PreparedStatement updateStockStmt = conn.prepareStatement(updateStockQuery)) {
                    updateStockStmt.setInt(1, quantity);
                    updateStockStmt.setString(2, bloodGroup);
                    updateStockStmt.executeUpdate();
                }

                // Add Patient Request
                String requestQuery = "INSERT INTO PatientRequests (PatientID, BloodGroup, Quantity, RequestDate, Status) VALUES (?, ?, ?, ?, 'Processed')";
                try (PreparedStatement requestStmt = conn.prepareStatement(requestQuery)) {
                    requestStmt.setInt(1, patientID);
                    requestStmt.setString(2, bloodGroup);
                    requestStmt.setInt(3, quantity);
                    requestStmt.setDate(4, requestDate);
                    requestStmt.executeUpdate();
                }

                return true;
            } else {
                // Add Patient Request with 'Not Available' Status
                String requestQuery = "INSERT INTO PatientRequests (PatientID, BloodGroup, Quantity, RequestDate, Status) VALUES (?, ?, ?, ?, 'Not Available')";
                try (PreparedStatement requestStmt = conn.prepareStatement(requestQuery)) {
                    requestStmt.setInt(1, patientID);
                    requestStmt.setString(2, bloodGroup);
                    requestStmt.setInt(3, quantity);
                    requestStmt.setDate(4, requestDate);
                    requestStmt.executeUpdate();
                }

                return false;
            }
        }
    }

    public static ResultSet getPatientRequests() throws SQLException {
        String query = "SELECT * FROM PatientRequests";
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement stmt = conn.prepareStatement(query);
        return stmt.executeQuery();
    }
}
