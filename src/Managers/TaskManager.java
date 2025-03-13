package Managers;

import Main.AdminTaskService;
import Main.WorkerTaskService;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class TaskManager implements AdminTaskService, WorkerTaskService {
    private List<Task> tasks=new ArrayList<Task>();

    public void addTask(Task task){
        tasks.add(task);
    }
    public void deleteTask(String taskTitle){
        for(Task task:tasks){
            if(task.getTaskTitle().equals(taskTitle)){
                tasks.remove(task);
            }
        }

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

    public void listTasks(){
        for(Task task:tasks){
            System.out.println(task);
        }

    }
    public void listUserTasks(String username){
        for(Task task:tasks){
           if(task.getAssignedUser().equals(username)){
               System.out.println(task);
           }
        }
    }
    public void markTaskAsCompleted(String username, String taskTitle){

        for(Task task:tasks){
            if(task.getAssignedUser().equals(username) && task.getTaskTitle().equals(taskTitle)){
                task.markCompleted();
                System.out.println("task is marked as completed");
            }
        }
    }
    public void  filterTaskByCompletedStatus(String username,boolean completed){
        for(Task task:tasks){
            if (task.getAssignedUser().equals(username) && task.getIsCompleted()==completed){
                System.out.println(task);
            }
        }
    }

    public void filterTaskByPriority(String username,int priority){
        for(Task task:tasks){
            if(task.getAssignedUser().equals(username) && task.getPriority()==priority){
                System.out.println(task);
            }
        }
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
