import jakarta.persistence.*;
import service.TaskManager;
import service.UserManager;
import dao.DatabaseConnection;
import dao.TaskDAO;
import dao.UserDAO;
import model.user.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class TaskManagementSystem {
    public static void main(String[] args) {
            EntityManager em=DatabaseConnection.getEntityManager();
            Scanner scanner = new Scanner(System.in);
            UserDAO registerDao = new UserDAO(em);
            TaskDAO taskDAO = new TaskDAO(em);
            // in previous versions used to be db and only instated once, now just acts as a service/utilities
            UserManager userManager = new UserManager(registerDao);
            TaskManager taskManager = new TaskManager(taskDAO);

            System.out.println("Welcome to Task Management System");


            try{
                while (true) {
                    System.out.print("Enter username to login: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    User user = userManager.loginUser(username, password,taskManager);
                    user.displayMenu(); // polymorphism

                }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    public static void main(String[] args) {
//        System.out.println("Starting JPA with Hibernate... 🚀");
//
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate");
//        EntityManager em = emf.createEntityManager();
//
//        em.getTransaction().begin();
//
//        // Just trigger table creation
//        System.out.println("Tables created successfully! ✅");
//
//        em.getTransaction().commit();
//        em.close();
//        emf.close();
//    }
}

