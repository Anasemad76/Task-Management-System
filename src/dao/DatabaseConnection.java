package dao;
import java.sql.*;
public class DatabaseConnection {
    private static final String ConnectionUrl = "jdbc:sqlserver://localhost;Database=TaskManagmentSystem;encrypt=false;IntegratedSecurity=true";

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(ConnectionUrl);
        return conn;
    }
//    public static void main(String[] args) {
//        try {
//            // 1 Load driver & Establish Connection
//            Connection conn = DriverManager.getConnection(ConnectionUrl);
//            System.out.println("✅ Connected to SQL Server successfully!");
//
//            Statement stmt = conn.createStatement();
//            stmt.execute("INSERT INTO users(username, password,is_admin) VALUES('abdullrahman','123456',0);");
//            String selectQuery = "SELECT * FROM users;";
//            ResultSet rs = stmt.executeQuery(selectQuery);
//            System.out.println("\n📌 Users Table Data:");
//            while (rs.next()) {
//                System.out.print("ID: " + rs.getInt("id"));
//                System.out.print(", First: " + rs.getString("username"));
//                System.out.println(", Last: " + rs.getString("password"));
//
//            }
//            rs.close();
//            stmt.close();
//            conn.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
}
