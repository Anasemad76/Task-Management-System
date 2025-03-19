package dao;

import model.user.Admin;
import model.user.User;
import model.user.Worker;
import service.TaskManager;

import java.sql.*;

public class UserDAO {
    private Connection conn;
    public UserDAO(Connection conn) {
        this.conn=conn;
    }
    public boolean registerUserDB(String username, String password, boolean isAdmin){
        String sql = "insert into users (username, password, is_admin) values (?, ?, ?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setBoolean(3, isAdmin);
            int rowInserted=stmt.executeUpdate();
            return rowInserted>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User loginUser(String username, String password, TaskManager taskManager) {
        String sql = "select * from users where username=? and password=?";
        try(PreparedStatement stmt= conn.prepareStatement(sql)){
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();


            if( rs.next() ){
                boolean isAdmin = rs.getBoolean("is_admin");
                if (isAdmin){
                    return new Admin(username,password,taskManager);

                }else{
                    return new Worker(username,password,taskManager);

                }


            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
