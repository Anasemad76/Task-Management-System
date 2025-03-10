package Main;

import java.time.LocalDate;
import java.util.List;

public interface WorkerTaskService {
    void listUserTasks(String username);
    void markTaskAsCompleted(String username, String taskTitle);
    void filterTaskByCompletedStatus(String username,boolean completed);
    void filterTaskByPriority(String username,int priority);
    void filterTaskByDueDate(String username,LocalDate dueDate,String condition);
}
