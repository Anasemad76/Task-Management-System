package dao;

import Main.Admin;
import Main.User;

import java.sql.*;

public class RegistrationDAO {
    private Connection conn;
    public RegistrationDAO(Connection conn) {
        this.conn=conn;
    }
    public boolean registerUserDB(String username, String password, boolean isAdmin){
        String sql = "insert into users (username, password, isAdmin) values (?, ?, ?)";
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

    public User loginUser(String username, String password){
        String sql = "select * from users where username=? and password=?";
        try(PreparedStatement stmt= conn.prepareStatement(sql)){
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();


            if( rs.next() ){
                boolean isAdmin = rs.getBoolean("isAdmin");
                if (isAdmin){
                    return new Admin(username,password,);

                }else{
                    return new Admin(username,password,);

                }


            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
