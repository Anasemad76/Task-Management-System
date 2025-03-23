package model.task;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
public class HighPriorityTask extends Task {
    private static final int HIGH_PRIORITY = 3;
    public HighPriorityTask() {}
    public HighPriorityTask(String taskTitle, String taskDescription, String assignedUser, boolean isCompleted, LocalDate dueDate) {
        super(taskTitle, taskDescription, assignedUser, isCompleted, HIGH_PRIORITY, dueDate);
    }

    @Override
    public int getPriority() {
            return HIGH_PRIORITY;
    }
}
