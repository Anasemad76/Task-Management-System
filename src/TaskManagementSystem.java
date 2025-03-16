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
        try(Connection connection=DatabaseConnection.getConnection()) {
            Scanner scanner = new Scanner(System.in);
            UserDAO registerDao = new UserDAO(connection);
            TaskDAO taskDAO = new TaskDAO(connection);
            UserManager userManager = new UserManager(registerDao); //like my database table for users thats why we should have only one instance
            TaskManager taskManager = new TaskManager(taskDAO); // databse for tasks for all users thats why we need to have only one instance

            System.out.println("Welcome to Task Management System");

            // Register an admin and a user for testing
//            userManager.registerUser("admin", "admin123", true, taskManager);
//            userManager.registerUser("user1", "user123", false, taskManager);

            while (true) {
                System.out.print("Enter username to login: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                User user = userManager.loginUser(username, password,taskManager);
                user.displayMenu(); // polymorphism

//                if (user.getIsAdmin()) {
//                    Admin admin = new Admin(username, password, taskManager);
//                    admin.displayMenu();
//                } else {
//                    Worker worker = new Worker(username, password, taskManager);
//                    worker.displayMenu();
//                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

