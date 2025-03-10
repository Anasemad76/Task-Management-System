package Main;

import Managers.Task;

import java.util.Map;

public interface AdminTaskService {
    void addTask(Task task);
    void editTask(String taskTitle, Map<String,Object> updates);
    void deleteTask(String taskTitle);
    void listTasks();
}
