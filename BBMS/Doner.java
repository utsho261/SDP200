package BBMS;

import java.sql.*;

public class Doner {
    private static final String DB_URL = "jdbc:mysql://localhost/bloodbank";
    private static final String USER = "root";
    private static final String PASS = "";

    public static void addDoner(String name, String bloodGroup, String phone, String email) throws SQLException {
        String query = "INSERT INTO Doner (Name, BloodGroup, PhoneNumber, Email) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, bloodGroup);
            stmt.setString(3, phone);
            stmt.setString(4, email);
            stmt.executeUpdate();
        }
    }

    public static ResultSet getDoners() throws SQLException {
        String query = "SELECT * FROM Doner";
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement stmt = conn.prepareStatement(query);
        return stmt.executeQuery();
    }
}
