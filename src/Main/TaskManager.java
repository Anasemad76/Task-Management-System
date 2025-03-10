package Main;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

public class TaskManager {
    private List<Task> tasks=new ArrayList<Task>();

    public void addTask(Task task){
        tasks.add(task);
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
    public void filterTaskByCompletedStatus(String username,boolean completed){
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
