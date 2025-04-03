package DAY10;

import java.sql.*;

public class EcommerceOrder {
    private static final String URL = "jdbc:mysql://localhost:3306/ecommerce";
    private static final String USER = "root";
    private static final String PASS = "password";

    public static void addOrder(int id, String customer, String status) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO orders VALUES (?, ?, ?)");) {
            stmt.setInt(1, id);
            stmt.setString(2, customer);
            stmt.setString(3, status);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public static void getOrder(int id) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM orders WHERE id=?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                System.out.println(rs.getInt(1) + ", " + rs.getString(2) + ", " + rs.getString(3));
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public static void updateOrder(int id, String status) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("UPDATE orders SET status=? WHERE id=?")) {
            stmt.setString(1, status);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public static void deleteOrder() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM orders WHERE status='Canceled'");) {
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public static void main(String[] args) {
        addOrder(1, "John Doe", "Pending");
        getOrder(1);
        updateOrder(1, "Shipped");
        deleteOrder();
    }
}
