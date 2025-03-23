package model.task;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
//separate tables for each subclass
@Inheritance(strategy = InheritanceType.JOINED)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int taskId;
    @Column(name = "task_title", nullable = false , unique = true)
    private String taskTitle;
    @Column(name = "task_description")
    private String taskDescription;
    // check thisssssssssssssssssssssssssssss
    @Column(name = "assigned_user")
    private String assignedUser;
    // columnDef =can be used to specify the exact SQL column definition
    @Column(name = "is_completed", nullable = false ,columnDefinition = "BIT DEFAULT 0")
    private boolean isCompleted;
    @Column(name = "priority", nullable = false , columnDefinition = "INT CHECK (priority >= 1 AND priority <= 3)")
    private int priority;
    // note : @Temporal is used only with java.util.Date
    @Column(name = "due_date")
    private LocalDate  dueDate;

    public Task() {}
    //for terminal
    public Task(String taskTitle, String taskDescription, String assignedUser, boolean isCompleted, int priority, LocalDate dueDate) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.assignedUser = assignedUser;
        this.isCompleted = isCompleted;
        this.dueDate =dueDate;
        this.priority =priority;
    }
    // for database
    public Task(int taskId,String taskTitle, String taskDescription, String assignedUser, boolean isCompleted, int priority, LocalDate dueDate) {
        this.taskId = taskId;
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
    public int getTaskId() {
        return taskId;
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
