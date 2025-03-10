package Main;

import java.time.LocalDate;
import java.util.Scanner;

public class Admin extends User{
    private TaskManager taskManager; // i sent TaskManager object here bec it will be used inside Admin class
    public Admin(String username, String password, TaskManager taskManager) {
        super(username, password,true);
        this.taskManager = taskManager;
    }

    @Override
    public void displayMenu() {
        System.out.println("Welcome to the Admin Menu");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Create Task");
            System.out.println("2. View All Tasks");
            System.out.println("3. Delete Task");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter task title: ");
                    String taskTitle = scanner.nextLine();
                    System.out.print("Enter task description: ");
                    String taskDesc = scanner.nextLine();
                    System.out.print("Enter username to assign: ");
                    String assignedUser = scanner.nextLine();
                    System.out.print("Enter priority (1-3): ");
                    int priority = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter due date (yyyy-MM-dd): ");
                    LocalDate dueDate = LocalDate.parse(scanner.nextLine());
                    taskManager.addTask(new Task(taskTitle, taskDesc, assignedUser, false, priority, dueDate));
                    break;
                case 2:
                    taskManager.listTasks();
                case 3:
                    System.out.print("Enter Task ID to delete: ");
                    int taskId = scanner.nextInt();
                    //taskManager.deleteTask(taskId);
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}
