package model.task;

import java.time.LocalDate;


public class Task {
     private int taskId;
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

    // IMP note: all setters are package prv (bec we dont want worker to call setters only admins can from TaskManager only)

    public void markCompleted() {
        this.isCompleted = true;
    }
    public String getTaskTitle() {
        return taskTitle;
    }
    void setTaskId(int taskId) {
        this.taskId = taskId;
    }
     void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }
     void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
    public String getTaskDescription() {
        return taskDescription;
    }
    public String getAssignedUser() {
        return assignedUser;
    }
     void setAssignedUser(String assignedUser) {
        this.assignedUser = assignedUser;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public int getPriority() {
        return priority;
    }
     void setPriority(int priority) {
        this.priority = priority;
    }

    public LocalDate  getDueDate() {
        return dueDate;
    }
     void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "Task: " + taskTitle + " | Assigned To: " + assignedUser + " | Completed: " + isCompleted;
    }
}
