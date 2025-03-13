package dao;

import Managers.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {
    private Connection conn;

    public TaskDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<Task>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("select * from tasks");
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;

    }
    public List<Task> getUserTasks(String assignedUser) {
        List<Task> tasks = new ArrayList<Task>();
        try (PreparedStatement pstmt = conn.prepareStatement("select * from tasks where assigned_user = ?")) {
            pstmt.setString(1, assignedUser);
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
                tasks.add(task);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;

    }

    public boolean addNewTask(Task task) {
        String sql = "INSERT INTO tasks (task_title, task_description, assigned_user, is_completed, priority, due_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, task.getTaskTitle());
            pstmt.setString(2, task.getTaskDescription());
            pstmt.setString(3, task.getAssignedUser());
            pstmt.setBoolean(4, task.getIsCompleted());
            pstmt.setInt(5, task.getPriority());
            pstmt.setDate(6, Date.valueOf(task.getDueDate()));

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //remove by taskId
    public boolean removeTask(int taskId) {
        String sql = "DELETE FROM tasks WHERE id=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, taskId);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //remove by taskTitle
    public boolean removeTask(String taskTitle) {
        String sql = "DELETE FROM tasks WHERE task_title=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, taskTitle);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //complete by taskId
    public boolean taskCompleted(int taskId,String assignedUser) {
        String sql = "UPDATE tasks SET is_completed = ? WHERE id=? AND assigned_user=? ";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1,true);
            pstmt.setInt(2, taskId);
            pstmt.setString(3, assignedUser);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //complete by task title
    public boolean taskCompleted(String taskTitle,String assignedUser) {
        String sql = "UPDATE tasks SET is_completed = ? WHERE task_title=? AND assigned_user=? ";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1,true);
            pstmt.setString(2, taskTitle);
            pstmt.setString(3, assignedUser);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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



}