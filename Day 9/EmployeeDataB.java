package DAY10;
import java.sql.*;

public class EmployeeDataB {
    private static final String URL = "jdbc:mysql://localhost:3306/company";
    private static final String USER = "root";
    private static final String PASS = "password";

    public static void addEmployee(String name, String email, double salary) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO employees (name, email, salary) VALUES (?, ?, ?)");) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setDouble(3, salary);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public static void searchEmployee(String keyword) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM employees WHERE name LIKE ? OR email LIKE ?")) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                System.out.println(rs.getInt(1) + ", " + rs.getString(2) + ", " + rs.getString(3) + ", " + rs.getDouble(4));
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public static void updateSalary(int id, double salary) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("UPDATE employees SET salary=? WHERE id=?")) {
            stmt.setDouble(1, salary);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public static void main(String[] args) {
        addEmployee("Alice", "alice@example.com", 50000);
        searchEmployee("Alice");
        updateSalary(1, 55000);
    }
}
