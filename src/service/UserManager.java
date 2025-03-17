package service;

import model.user.User;
import dao.UserDAO;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private UserDAO userDAO;

    public UserManager(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean registerUser(String username, String password, boolean isAdmin, TaskManager taskManager) {
        boolean isSuccessful = userDAO.registerUserDB(username, password, isAdmin) ;
        if(isSuccessful) {
            System.out.println("User registered Successfully!");
        }else {
            System.out.println("User registering Failed!");
        }
        return isSuccessful;


    }

    public User loginUser(String username, String password,TaskManager taskManager) {

        return userDAO.loginUser(username, password,taskManager);

    }
}
