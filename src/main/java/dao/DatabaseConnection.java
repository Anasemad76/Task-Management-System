package dao;
import jakarta.persistence.*;


import java.sql.*;
public class DatabaseConnection {
    //note this could also be an interface with just one static method and still cannot be initiated

//    private static final String ConnectionUrl = "jdbc:sqlserver://localhost;Database=TaskManagmentSystem;encrypt=false;IntegratedSecurity=true";
//    private DatabaseConnection() {} // because i dont want to create objects and mae=ke more than one connection
//    public static Connection getConnection() throws SQLException {
//        Connection conn = DriverManager.getConnection(ConnectionUrl);
//        System.out.println("Connected to SQL Server successfully!");
//        return conn;
//    }
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hibernate");
    public static EntityManager getEntityManager(){
        //EntityManager is a session for managing database operations.
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }
    // should be called once, when application shut down
    public static void close(){
        ENTITY_MANAGER_FACTORY.close();
    }

}
