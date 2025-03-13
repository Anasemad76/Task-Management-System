package dao;
import java.sql.*;
public class DatabaseConnection {
    //note this could also be an interface with just one static method and still cannot be initiated

    private static final String ConnectionUrl = "jdbc:sqlserver://localhost;Database=TaskManagmentSystem;encrypt=false;IntegratedSecurity=true";
    private DatabaseConnection() {} // because i dont want to create objects and mae=ke more than one connection
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(ConnectionUrl);
        System.out.println("Connected to SQL Server successfully!");
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
//                System.out.println(", Password: " + rs.getString("password"));
//                System.out.println(", IsAdmin: " + rs.getBoolean("is_admin"));
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
