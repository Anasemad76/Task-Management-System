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

}
