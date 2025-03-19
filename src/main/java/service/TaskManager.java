package service;

import dao.TaskDAO;
import model.task.Task;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class TaskManager implements AdminTaskService, WorkerTaskService {
    private List<Task> tasks=new ArrayList<Task>();
    private TaskDAO taskDAO;
    public TaskManager(TaskDAO taskDAO) {
        this.taskDAO=taskDAO;
    }


    public boolean addTask(Task task) {
        boolean isSuccessfulQuery=taskDAO.addNewTask(task);
        System.out.println(isSuccessfulQuery ? "Task added successfully" : "Task wasn't added successfully");
        return isSuccessfulQuery;
    }


    public boolean deleteTask(String taskTitle){
       boolean isSuccessfulQuery=taskDAO.removeTask(taskTitle);
        System.out.println(isSuccessfulQuery ? "Task Deleted successfully" : "Task wasn't Deleted successfully");
        return isSuccessfulQuery;
    }

    // for terminal
    public boolean editTask(String taskTitle, Map<String,Object> updates){
           boolean isSuccessful= taskDAO.editTask(taskTitle,updates);
           return isSuccessful;

    }



    public List<Task> listTasks(){
        List<Task>  taskList= taskDAO.getAllTasks();
        for(Task task:taskList){
            System.out.println(task);
        }
        return taskList;


    }


    public List<Task> listUserTasks(String username){
        List<Task>  taskList= taskDAO.getUserTasks(username);
        for(Task task:taskList){
            System.out.println(task);
        }
        return taskList;
    }




    public void markTaskAsCompleted(String username, String taskTitle){

        boolean isSuccessfulQuery=taskDAO.taskCompleted(taskTitle,username);
        System.out.println(isSuccessfulQuery ? "Task Completed successfully" : "Task wasn't Completed successfully");
    }

    public List<Task>  filterTaskByCompletedStatus(String username,boolean completed){
        List<Task> filteredTasks=taskDAO.filterByCompletion(username,completed);
        for (Task task:filteredTasks){
            System.out.println(task);
        }
        return filteredTasks;

    }


    public List<Task> filterTaskByPriority(String username,int priority){
        List<Task> filteredTasks=taskDAO.filterByTaskPriority(username,priority);
        for (Task task:filteredTasks){
            System.out.println(task);
        }
        return filteredTasks;

    }


public List<Task> filterTaskByDueDate(String username,LocalDate dueDate,String condition){
        List<Task> filteredTasks=taskDAO.filterByTaskDUEDate(username,dueDate,condition);
        for (Task task:filteredTasks){
            System.out.println(task);
        }

        return filteredTasks;

}
}
