package Managers;

import Main.Admin;
import Main.User;
import Main.Worker;
import dao.RegistrationDAO;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private Map<String, User> users =new HashMap<>(); //acts like database for now
    private RegistrationDAO registrationDAO;

    public UserManager(RegistrationDAO registrationDAO) {
        this.registrationDAO = registrationDAO;
    }

    public boolean registerUser(String username, String password, boolean isAdmin, TaskManager taskManager) {
        boolean isSuccessful =registrationDAO.registerUserDB(username, password, isAdmin) ;
        if(isSuccessful) {
            System.out.println("User registered Successfully!");
        }else {
            System.out.println("User registering Failed!");
        }
        return isSuccessful;

//        if (users.containsKey(username)) {
//            System.out.println("Username already exists!");
//            return;
//        }
//
//        if (isAdmin){
//            users.put(username,new Admin(username,password,taskManager));
//        }
//        else{
//            users.put(username,new Worker(username,password,taskManager));
//
//        }
//
//        System.out.println("User registered Successfully!");
    }

    public User loginUser(String username, String password){

        return registrationDAO.loginUser(username, password);

//        User user = users.get(username);
//        if(user != null && user.authenticate(password)){
//            System.out.println("User logged in successfully!");
//            return user;
//        }
//        else{
//            System.out.println("Username or password is incorrect!");
//            return null;
//        }
    }
}
