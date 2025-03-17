package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.user.User;
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
                if (user != null) {


                    if (user.getIsAdmin()) {
                        JOptionPane.showMessageDialog(loginPanel, "Hello, " + userName.getText());
                        dispose();
                        AdminDashboard adminDashboard = new AdminDashboard(user, taskManager,userManager);
                        //user.displayMenu();
                    } else {
                        JOptionPane.showMessageDialog(loginPanel, "Hello, " + userName.getText());
                        dispose();
                        UserDashboard userDashboard = new UserDashboard(user, taskManager, userManager);
                        //user.displayMenu();
                    }
                }else{
                    JOptionPane.showMessageDialog(loginPanel, "Invalid username or password");

                }


            }
        });
    }


}
