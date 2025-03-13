package Main;

import Managers.Task;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Admin extends User{
    private AdminTaskService taskManager; // I used TaskManager object here bec it will be used inside Admin class
    public Admin(String username, String password, AdminTaskService taskManager) {
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
            System.out.println("4. Edit Task");
            System.out.println("5. Logout");
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
                    break;
                case 3:
                    System.out.print("Enter Task Title to delete: ");
                    String taskTit = scanner.nextLine();
                    taskManager.deleteTask(taskTit);
                    break;
                case 4:
                    System.out.print("Enter Task Title to edit: ");
                    String taskTit2 = scanner.nextLine();
                    System.out.println("Enter Task Information to edit (title/description/username/priority/date): ");
                    String toBeEdited = scanner.nextLine();
                    System.out.println("Enter information: ");
                    String updated = scanner.nextLine();
                    Map<String, Object> updates = new HashMap<>();
                    updates.put(toBeEdited,updated);
                    taskManager.editTask(taskTit2,updates);
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}
