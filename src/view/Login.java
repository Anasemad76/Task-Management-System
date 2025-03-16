package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.user.Admin;
import model.user.User;
import model.user.Worker;
import service.TaskManager;
import service.UserManager;

public class Login extends JFrame{
    private JPanel loginPanel;
    private JTextField userName;
    private JButton loginButton;
    private JLabel alreadyUser;
    private JPasswordField password;

    private UserManager userManager;
    private TaskManager taskManager;
    public Login(UserManager userManager, TaskManager taskManager) {
        this.userManager = userManager;
        this.taskManager = taskManager;
        setContentPane(loginPanel);
        setTitle("Login");
        setBounds(400, 200, 450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userName.getText();
                String pass = new String(password.getPassword());
                User user = userManager.loginUser(username,pass,taskManager);
                if (user.getIsAdmin()) {
                    JOptionPane.showMessageDialog(loginButton,userName.getText()+" ,Hello");
                    AdminDashboard  adminDashboard= new AdminDashboard(user);
                    //user.displayMenu();
                } else {
                    JOptionPane.showMessageDialog(loginButton,userName.getText()+" ,Hello");

                    UserDashboard userDashboard = new UserDashboard(user,taskManager);
                    //user.displayMenu();
                }


            }
        });
    }


}
