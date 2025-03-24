package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transaction;
import model.task.Task;
import model.user.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class TaskDAO {
    private EntityManager em;

    public TaskDAO(EntityManager em) {
        this.em = em;
    }

//    public List<Task> getAllTasks() {
//        List<Task> tasks = new ArrayList<Task>();
//        try (Statement stmt = conn.createStatement()) {
//            ResultSet rs = stmt.executeQuery("select * from tasks");
//            while (rs.next()) {
//                Task task = new Task(
//                        rs.getInt("id"),
//                        rs.getString("task_title"),
//                        rs.getString("task_description"),
//                        rs.getString("assigned_user"),
//                        rs.getBoolean("is_completed"),
//                        rs.getInt("priority"),
//                        rs.getDate("due_date").toLocalDate()
//                );
//
//                tasks.add(task);
//
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return tasks;
//
//    }
    public List<Task> getAllTasks() {
        //note: TypedQuery<T> is a subinterface of Query that enforces type safety
        // And used only for SELECT queries.
        TypedQuery<Task> query = em.createQuery("SELECT t from Task t ", Task.class);
        List<Task> tasks = query.getResultList();
        return tasks;


    }

//    public List<Task> getUserTasks(String assignedUser) {
//        List<Task> tasks = new ArrayList<Task>();
//        try (PreparedStatement pstmt = conn.prepareStatement("select * from tasks where assigned_user = ?")) {
//            pstmt.setString(1, assignedUser);
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()) {
//                Task task = new Task(
//                        rs.getInt("id"),
//                        rs.getString("task_title"),
//                        rs.getString("task_description"),
//                        rs.getString("assigned_user"),
//                        rs.getBoolean("is_completed"),
//                        rs.getInt("priority"),
//                        rs.getDate("due_date").toLocalDate()
//                );
//
//                tasks.add(task);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return tasks;
//
//    }
    public List<Task> getUserTasks(User user) {
        TypedQuery<Task> query=em.createQuery("SELECT t from Task t where t.assignedUser = :user", Task.class);
        query.setParameter("user", user);
        List<Task> tasks = query.getResultList();
        return tasks;


    }

//    public boolean addNewTask(Task task) {
//        String sql = "INSERT INTO tasks (task_title, task_description, assigned_user, is_completed, priority, due_date) VALUES (?, ?, ?, ?, ?, ?)";
//        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, task.getTaskTitle());
//            pstmt.setString(2, task.getTaskDescription());
//            pstmt.setString(3, task.getAssignedUser());
//            pstmt.setBoolean(4, task.getIsCompleted());
//            pstmt.setInt(5, task.getPriority());
//            pstmt.setDate(6, Date.valueOf(task.getDueDate()));
//
//            int rows = pstmt.executeUpdate();
//            return rows > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
    public boolean addNewTask(Task task) {
        EntityTransaction tx = em.getTransaction();
        try{
            tx.begin();
            em.persist(task);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        }

    }

//    //remove by taskId
//    public boolean removeTask(int taskId) {
//        String sql = "DELETE FROM tasks WHERE id=?";
//        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setInt(1, taskId);
//            int rows = pstmt.executeUpdate();
//            return rows > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
    //remove by taskId
    public boolean removeTask(int taskId) {
        EntityTransaction tx = em.getTransaction();
        try{
            tx.begin();
            Task task = em.find(Task.class, taskId); // returns null if not found
            if(task != null) {
                em.remove(task);
                tx.commit();
                return true;
            }
            return false;


        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        }

    }

//    //remove by taskTitle
//    public boolean removeTask(String taskTitle) {
//        String sql = "DELETE FROM tasks WHERE task_title=?";
//        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, taskTitle);
//            int rows = pstmt.executeUpdate();
//            return rows > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
    //remove by taskTitle
    public boolean removeTask(String taskTitle) {
        EntityTransaction tx = em.getTransaction();
        try{
            tx.begin();
            Query query=em.createQuery("DELETE FROM Task t WHERE t.taskTitle = :title", Task.class);
            query.setParameter("title", taskTitle);
            int row=query.executeUpdate(); // Executes update (this is used for UPDATE, DELETE, INSERT)
            tx.commit();
            return row>0;

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;

        }

    }


    // for terminal
    public boolean editTask(String taskTitle, Map<String,Object> updates) {
        if (updates.isEmpty()) {
            System.out.println("No updates provided.");
            return false;
        }
        StringBuilder sql = new StringBuilder("UPDATE tasks SET ");
        updates.forEach((key, value) -> sql.append(key).append(" = ?, ") );
        sql.setLength(sql.length()-2);
        sql.append(" WHERE task_title=?");
        try(PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            int index = 1;
            for(Map.Entry<String,Object> entry:updates.entrySet()){
                Object value = entry.getValue();
                if(value instanceof String){
                    pstmt.setString(index++, (String) value);
                }
                else if(value instanceof Integer){
                    pstmt.setInt(index++, (Integer) value);
                }
                else if(value instanceof Boolean){
                    pstmt.setBoolean(index++, (Boolean) value);
                }
                else if(value instanceof LocalDate){
                    pstmt.setDate(index++, Date.valueOf((LocalDate) value));
                }else {
                    System.out.println("Invalid field type for key: " + entry.getKey());
                    return false;
                }
            }
            pstmt.setString(index,taskTitle);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Task updated successfully");
                return true;
            }else{
                System.out.println("No task found with title: " +taskTitle);
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;


    }

//    //complete by taskId
//    public boolean taskCompleted(int taskId,String assignedUser) {
//        String sql = "UPDATE tasks SET is_completed = ? WHERE id=? AND assigned_user=? ";
//        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setBoolean(1,true);
//            pstmt.setInt(2, taskId);
//            pstmt.setString(3, assignedUser);
//            int rows = pstmt.executeUpdate();
//            return rows > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//complete by taskId
    public boolean taskCompleted(int taskId) {
        EntityTransaction tx = em.getTransaction();
        try{
            tx.begin();
            Task task = em.find(Task.class, taskId);
            if(task != null) {
                task.setIsCompleted(true);
                // em.merge(task); not needed becasue task is managed by persistence context(handled by em) not detached
                tx.commit();
                return true;
            }
            return false;

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;

        }

    }
//    //complete by task title
//    public boolean taskCompleted(String taskTitle,String assignedUser) {
//        String sql = "UPDATE tasks SET is_completed = ? WHERE task_title=? AND assigned_user=? ";
//        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setBoolean(1,true);
//            pstmt.setString(2, taskTitle);
//            pstmt.setString(3, assignedUser);
//            int rows = pstmt.executeUpdate();
//            return rows > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
    //complete by task title
    public boolean taskCompleted(String taskTitle) {
        EntityTransaction tx = em.getTransaction();
        try {
            Query query = em.createQuery("UPDATE Task t SET t.isCompleted = true WHERE t.taskTitle = :title", Task.class);
            query.setParameter("title", taskTitle);
            int rows = query.executeUpdate();
            tx.commit();
            return rows > 0;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;

        }

    }

    public List<Task> filterByCompletion(String username,boolean isCompleted) {
        List<Task> tasks = new ArrayList<Task>();
        String sql="select * from tasks where assigned_user=? and is_completed=?";
        try(PreparedStatement pstmt=conn.prepareStatement(sql)){
            pstmt.setString(1,username);
            pstmt.setBoolean(2,isCompleted);
            ResultSet rs=pstmt.executeQuery();
            while (rs.next()) {
                Task task = new Task(
                        rs.getString("task_title"),
                        rs.getString("task_description"),
                        rs.getString("assigned_user"),
                        rs.getBoolean("is_completed"),
                        rs.getInt("priority"),
                        rs.getDate("due_date").toLocalDate()
                );
                //task.setTaskId(rs.getInt("id");
                tasks.add(task);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return tasks;
    }

    public List<Task> filterByTaskPriority(String username,int priority) {
        List<Task> tasks = new ArrayList<Task>();
        String sql="select * from tasks where assigned_user=? and priority=?";
        try(PreparedStatement pstmt=conn.prepareStatement(sql)){
            pstmt.setString(1,username);
            pstmt.setInt(2,priority);
            ResultSet rs=pstmt.executeQuery();
            while (rs.next()) {
                Task task = new Task(
                        rs.getString("task_title"),
                        rs.getString("task_description"),
                        rs.getString("assigned_user"),
                        rs.getBoolean("is_completed"),
                        rs.getInt("priority"),
                        rs.getDate("due_date").toLocalDate()
                );
                //task.setTaskId(rs.getInt("id");
                tasks.add(task);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return tasks;
    }

    public List<Task> filterByTaskDUEDate(String username, LocalDate dueDate,String condition) {
        List<Task> filteredTasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE assigned_user = ? AND ";
        switch(condition){
            case "before":
                sql += "due_date < ? ";
                break;
            case "after":
                sql += "due_date > ? ";
                break;
            case "on":
                sql += "due_date = ? ";
                break;
            default:
                System.out.println("Invalid condition: Use 'before', 'after', or 'on'");
                return filteredTasks;
        }
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, username);
            pstmt.setDate(2, Date.valueOf(dueDate));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Task task = new Task(
                        rs.getString("task_title"),
                        rs.getString("task_description"),
                        rs.getString("assigned_user"),
                        rs.getBoolean("is_completed"),
                        rs.getInt("priority"),
                        rs.getDate("due_date").toLocalDate()
                );
                //task.setTaskId(rs.getInt("id");
                filteredTasks.add(task);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredTasks;
    }



}