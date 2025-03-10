package Main;


import Managers.TaskManager;
import Managers.UserManager;

import java.util.Scanner;

public class TaskManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserManager userManager = new UserManager(); //like my database table for users thats why we should have only one instance
        TaskManager taskManager = new TaskManager(); // databse for tasks for all users thats why we need to have only one instance

        System.out.println("Welcome to Task Management System");

        // Register an admin and a user for testing
        userManager.registerUser("admin", "admin123", true,taskManager);
        userManager.registerUser("user1", "user123", false,taskManager);

        while (true){
            System.out.print("Enter username to login: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            User user = userManager.loginUser(username,password);

            if (user.getIsAdmin()) {
                Admin admin = new Admin(username, password, taskManager);
                admin.displayMenu();
            } else {
                Worker worker = new Worker(username, password, taskManager);
                worker.displayMenu();
            }
        }

    }
}

