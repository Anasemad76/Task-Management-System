package service;

import model.task.Task;
import model.user.User;

import java.time.LocalDate;
import java.util.List;

public interface WorkerTaskService {
    List<Task> listUserTasks(User user);
    void markTaskAsCompleted(String username, String taskTitle);
    List<Task> filterTaskByCompletedStatus(User user,boolean completed);
    List<Task> filterTaskByPriority(User user,int priority);
    List<Task> filterTaskByDueDate(User user,LocalDate dueDate,String condition);
}
