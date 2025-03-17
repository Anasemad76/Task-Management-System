package model.task;

import java.time.LocalDate;


public class Task {
//    private int taskId;
    private String taskTitle;
    private String taskDescription;
    private String assignedUser;
    private boolean isCompleted;
    private int priority;
    private LocalDate  dueDate;

    public Task(String taskTitle, String taskDescription, String assignedUser, boolean isCompleted, int priority, LocalDate dueDate) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.assignedUser = assignedUser;
        this.isCompleted = isCompleted;
        this.dueDate =dueDate;
        this.priority =priority;
    }




    public String getTaskTitle() {
        return taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }
    public String getAssignedUser() {
        return assignedUser;
    }


    public boolean getIsCompleted() {
        return isCompleted;
    }

    public int getPriority() {
        return priority;
    }

    public LocalDate  getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        return "Task: " + taskTitle + " | Assigned To: " + assignedUser + " | Completed: " + isCompleted;
    }
}
