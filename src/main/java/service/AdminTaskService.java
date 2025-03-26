package service;

import model.task.HighPriorityTask;
import model.task.Task;

import java.util.List;
import java.util.Map;

public interface AdminTaskService {
    boolean addTask(String username,Task task);
    boolean editTask(String taskTitle, Map<String,Object> updates,boolean isGUI);
    boolean editTaskPriorty(int id, int newPriority);
    boolean deleteTask(String taskTitle);
    boolean approveTask(String taskTitle);
    List<HighPriorityTask> filterAllHighPriorityTasks();
    List<Task> listTasks();
}
