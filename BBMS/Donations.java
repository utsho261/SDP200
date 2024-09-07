package BBMS;

import java.sql.*;

public class Donations {
    private static final String DB_URL = "jdbc:mysql://localhost/bloodbank";
    private static final String USER = "root";
    private static final String PASS = "";

    public static void addDonation(int donerID, String donationDate, int quantity) throws SQLException {
        String query = "INSERT INTO Donations (DonerID, BloodGroup, DonationDate, Quantity) VALUES (?, (SELECT BloodGroup FROM Doner WHERE DonerID = ?), ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, donerID);
            stmt.setInt(2, donerID);
            stmt.setDate(3, Date.valueOf(donationDate));
            stmt.setInt(4, quantity);
            stmt.executeUpdate();

            // Update BloodStock
            BloodStock.updateStock((String) getBloodGroup(donerID), quantity);
        }
    }

    private static String getBloodGroup(int donerID) throws SQLException {
        String query = "SELECT BloodGroup FROM Doner WHERE DonerID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, donerID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("BloodGroup");
            }
            return null;
        }
    }

    public static ResultSet getDonations() throws SQLException {
        String query = "SELECT * FROM Donations";
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement stmt = conn.prepareStatement(query);
        return stmt.executeQuery();
    }
}
