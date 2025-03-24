package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import model.user.Admin;
import model.user.User;
import model.user.Worker;
import service.TaskManager;

import java.sql.*;

public class UserDAO {
    private EntityManager em;
    public UserDAO(EntityManager em) {
        this.em=em;
    }
//    public boolean registerUserDB(String username, String password, boolean isAdmin){
//        String sql = "insert into users (username, password, is_admin) values (?, ?, ?)";
//        try(PreparedStatement stmt = conn.prepareStatement(sql)){
//            stmt.setString(1, username);
//            stmt.setString(2, password);
//            stmt.setBoolean(3, isAdmin);
//            int rowInserted=stmt.executeUpdate();
//            return rowInserted>0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
    public boolean registerUserDB(User user){
        // With a Transaction, it either ALL succeeds or ALL fails(Moslty used with write operations)
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(user);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) { // tx not commited and still active locking database resources
                tx.rollback(); // free database resources and undo any changes happend before error
            }
            e.printStackTrace();
            return false;
        }
        return true;


    }

//    public User loginUser(String username, String password, TaskManager taskManager) {
//        String sql = "select * from users where username=? and password=?";
//        try(PreparedStatement stmt= conn.prepareStatement(sql)){
//            stmt.setString(1, username);
//            stmt.setString(2, password);
//            ResultSet rs = stmt.executeQuery();
//
//
//            if( rs.next() ){
//                boolean isAdmin = rs.getBoolean("is_admin");
//                if (isAdmin){
//                    return new Admin(username,password,taskManager);
//
//                }else{
//                    return new Worker(username,password,taskManager);
//
//                }
//
//
//            }
//
//        }catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public User loginUser(String username, String password, TaskManager taskManager) {
            // JPQL REQUIRED HERE TO WRITE A COMPLEX NOT BUILT IN QUERY
            try{
                TypedQuery<User> query =em.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password",User.class);
                query.setParameter("username", username);
                query.setParameter("password", password);
                //JPA returns the correct subclass object, but the declared reference type is User
                User user= query.getSingleResult(); // returns single only one user (If no user is found → Exception is thrown (NoResultException))
                //.get just fetches result

                //instanceof checks the actual object type, not the reference type
                 if(user instanceof Admin){
                        ((Admin)user).setTaskManager(taskManager);
                 }else if(user instanceof Worker){
                        ((Worker)user).setTaskManager(taskManager);
                    }
                 return user;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }





    }



}
