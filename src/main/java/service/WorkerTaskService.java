package service;

import model.task.Task;

import java.time.LocalDate;
import java.util.List;

public interface WorkerTaskService {
    List<Task> listUserTasks(String username);
    void markTaskAsCompleted(String username, String taskTitle);
    List<Task> filterTaskByCompletedStatus(String username,boolean completed);
    List<Task> filterTaskByPriority(String username,int priority);
    List<Task> filterTaskByDueDate(String username,LocalDate dueDate,String condition);
}
