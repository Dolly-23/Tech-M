package DAY10;

import java.io.*;
import java.sql.*;

public class BatchDataInsertion {
    private static final String URL = "jdbc:mysql://localhost:3306/company";
    private static final String USER = "root";
    private static final String PASS = "password";

    public static void insertDataFromCSV(String filePath) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO employees (name, email, salary) VALUES (?, ?, ?)");
             BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            conn.setAutoCommit(false);
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                stmt.setString(1, data[0]);
                stmt.setString(2, data[1]);
                stmt.setDouble(3, Double.parseDouble(data[2]));
                stmt.addBatch();
                if (++count % 1000 == 0) {
                    stmt.executeBatch();
                    conn.commit();
                }
            }
            stmt.executeBatch();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        insertDataFromCSV("employees.csv");
    }
}
