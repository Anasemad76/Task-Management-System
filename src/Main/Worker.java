package Main;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Worker extends User{
    private TaskManager taskManager;
    public Worker(String username, String password, TaskManager taskManager) {
        super(username, password,false);
        this.taskManager = taskManager;
    }

    @Override
    public void displayMenu() {
        System.out.println("Welcome to the Worker Menu");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nWorker Menu:");
            System.out.println("1. View Assigned Tasks");
            System.out.println("2. Mark Task as Completed");
            System.out.println("3. Filter by Completion Status");
            System.out.println("4. Filter by priority (1-3)");
            System.out.println("5. Filter by due date");
            System.out.println("6. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    taskManager.listUserTasks(getUsername());
                    break;
                case 2:
                    System.out.print("Enter task title to mark as completed: ");
                    String taskTitle = scanner.nextLine();
                    taskManager.markTaskAsCompleted(getUsername(), taskTitle);
                    break;
                case 3:
                    System.out.print("Show completed tasks? (true/false): ");
                    boolean completed = scanner.nextBoolean();
                    scanner.nextLine(); // Consume newline
                    taskManager.filterTaskByCompletedStatus(getUsername(),completed);
                    break;
                case 4:
                    System.out.print("Choose priority (1-3): : ");
                    int priority = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    taskManager.filterTaskByPriority(getUsername(),priority);
                    break;

                case 5:
                    System.out.print("Enter due date to filter (yyyy-MM-dd): ");
                    String dateInput = scanner.nextLine();
                    LocalDate date = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    System.out.print("Filter by (before/after/on): ");
                    String condition = scanner.nextLine().toLowerCase();
                    taskManager.filterTaskByDueDate(getUsername(),date,condition);
                    break;

                case 6:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}
