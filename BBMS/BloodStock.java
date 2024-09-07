package BBMS;
import java.sql.*;

public class BloodStock {
    private static final String DB_URL = "jdbc:mysql://localhost/bloodbank";
    private static final String USER = "root";
    private static final String PASS = "";

    public static ResultSet getBloodStock() throws SQLException {
        String query = "SELECT * FROM BloodStock";
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement stmt = conn.prepareStatement(query);
        return stmt.executeQuery();
    }

    public static void updateStock(String bloodGroup, int quantity) throws SQLException {
        String query = "UPDATE BloodStock SET Quantity = Quantity + ? WHERE BloodGroup = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, quantity);
            stmt.setString(2, bloodGroup);
            stmt.executeUpdate();
        }
    }
}
