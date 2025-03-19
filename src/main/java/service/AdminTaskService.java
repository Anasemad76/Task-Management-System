package service;

import model.task.Task;

import java.util.List;
import java.util.Map;

public interface AdminTaskService {
    boolean addTask(Task task);
    boolean editTask(String taskTitle, Map<String,Object> updates);
    boolean deleteTask(String taskTitle);
    List<Task> listTasks();
}
