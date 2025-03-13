package Managers;

import Main.AdminTaskService;
import Main.WorkerTaskService;
import dao.TaskDAO;

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

//    public void addTask(Task task){
//        tasks.add(task);
//    }
    public void addTask(Task task) {
        boolean isSuccessfulQuery=taskDAO.addNewTask(task);
        System.out.println(isSuccessfulQuery ? "Task added successfully" : "Task wasn't added successfully");
    }

//    public void deleteTask(String taskTitle){
//        for(Task task:tasks){
//            if(task.getTaskTitle().equals(taskTitle)){
//                tasks.remove(task);
//            }
//        }
//
//    }
    public void deleteTask(String taskTitle){
       boolean isSuccessfulQuery=taskDAO.removeTask(taskTitle);
        System.out.println(isSuccessfulQuery ? "Task Deleted successfully" : "Task wasn't Deleted successfully");
    }

    public void editTask(String taskTitle, Map<String,Object> updates){
        for(Task task:tasks){
            if(task.getTaskTitle().equals(taskTitle)){
                updates.forEach( (key,value)->{
                    switch(key){
                        case "title":
                            task.setTaskTitle((String) value);
                            break;
                        case "description":
                            task.setTaskDescription((String) value);
                            break;
                        case "username":
                            task.setAssignedUser((String) value);
                            break;
                        case "priority":
                            task.setPriority((Integer) value);
                            break;
                        case "dueDate":
                            task.setDueDate((LocalDate) value);
                            break;
                        default:
                            System.out.println("Invalid field: " + key);

                    }
                });
            }
            break;
        }
        System.out.println("Task updated successfully!");

    }
//
//    public void listTasks(){
//        for(Task task:tasks){
//            System.out.println(task);
//        }
//
//    }

    public List<Task> listTasks(){
        List<Task>  taskList= taskDAO.getAllTasks();
        for(Task task:taskList){
            System.out.println(task);
        }
        return taskList;


    }


//    public void listUserTasks(String username){
//        for(Task task:tasks){
//           if(task.getAssignedUser().equals(username)){
//               System.out.println(task);
//           }
//        }
//    }
    public List<Task> listUserTasks(String username){
        List<Task>  taskList= taskDAO.getUserTasks(username);
        for(Task task:taskList){
            System.out.println(task);
        }
        return taskList;
    }


//    public void markTaskAsCompleted(String username, String taskTitle){
//
//        for(Task task:tasks){
//            if(task.getAssignedUser().equals(username) && task.getTaskTitle().equals(taskTitle)){
//                task.markCompleted();
//                System.out.println("task is marked as completed");
//            }
//        }
//    }


    public void markTaskAsCompleted(String username, String taskTitle){

        boolean isSuccessfulQuery=taskDAO.taskCompleted(taskTitle,username);
        System.out.println(isSuccessfulQuery ? "Task Completed successfully" : "Task wasn't Completed successfully");
    }

//    public void  filterTaskByCompletedStatus(String username,boolean completed){
//        for(Task task:tasks){
//            if (task.getAssignedUser().equals(username) && task.getIsCompleted()==completed){
//                System.out.println(task);
//            }
//        }
//    }
    public List<Task>  filterTaskByCompletedStatus(String username,boolean completed){
        List<Task> filteredTasks=taskDAO.filterByCompletion(username,completed);
        for (Task task:filteredTasks){
            System.out.println(task);
        }
        return filteredTasks;

    }

//    public void filterTaskByPriority(String username,int priority){
//        for(Task task:tasks){
//            if(task.getAssignedUser().equals(username) && task.getPriority()==priority){
//                System.out.println(task);
//            }
//        }
//    }
    public List<Task> filterTaskByPriority(String username,int priority){
        List<Task> filteredTasks=taskDAO.filterByTaskPriority(username,priority);
        for (Task task:filteredTasks){
            System.out.println(task);
        }
        return filteredTasks;

    }

    public void filterTaskByDueDate(String username,LocalDate dueDate,String condition){
        for(Task task:tasks){
            if(task.getAssignedUser().equals(username)){
                boolean matches = false;
                switch (condition) {
                    case "before":
                        matches = task.getDueDate().isBefore(dueDate);
                        break;
                    case "after":
                        matches = task.getDueDate().isAfter(dueDate);
                        break;
                    case "on":
                        matches = task.getDueDate().equals(dueDate);
                        break;
                }
                if (matches) {
                    System.out.println(task);
                }
            }
        }

    }
}
