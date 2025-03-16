package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import dao.DatabaseConnection;
import dao.TaskDAO;
import dao.UserDAO;
import model.user.Admin;
import model.user.User;
import model.user.Worker;
import service.TaskManager;
import service.UserManager;

public class Register extends JFrame{
    private JTextField userName;
    private JButton registerBtn;
    private JButton loginButton;
    private JPanel registerPanel;
    private JCheckBox adminCheckBox;
    private JLabel alreadyUser;
    private JPasswordField password1;
    private JPasswordField password2;

    // must be class field to be accessed efficiency inside eventListeners
    private UserManager userManager;
    private TaskManager taskManager;

    public Register(UserManager userManager, TaskManager taskManager) {
        this.userManager=userManager;
        this.taskManager=taskManager;
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userName.getText();
                String pass1 = new String(password1.getPassword());
                String pass2 = new String(password2.getPassword());
                if (!pass1.equals(pass2)) {
                    JOptionPane.showMessageDialog(Register.this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                boolean isAdmin = adminCheckBox.isSelected();
                boolean isRegisteredSuccessfully=userManager.registerUser(username, pass1, isAdmin,taskManager);
                if (isRegisteredSuccessfully) {
                    User user = userManager.loginUser(username,pass1,taskManager);
                    JOptionPane.showMessageDialog(registerBtn,userName.getText()+" ,Hello");
                    if (user.getIsAdmin()) {
                        AdminDashboard adminDashboard = new AdminDashboard(user);
                    }else {
                        UserDashboard userDashboard = new UserDashboard(user,taskManager);
                    }

                }

            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Login loginForm = new Login(userManager,taskManager); // Assuming Login is another JFrame class
                loginForm.setVisible(true);

            }
        });
    }

public static void main(String[] args) {
    Connection connection = null;
    try {
        connection= DatabaseConnection.getConnection();
        UserDAO userDAO = new UserDAO(connection);
        TaskDAO taskDAO = new TaskDAO(connection);
        UserManager userManager = new UserManager(userDAO);
        TaskManager taskManager = new TaskManager(taskDAO);
        Register r=new Register(userManager,taskManager);
        r.setContentPane(r.registerPanel);
        r.setTitle("Register");
        r.setBounds(400, 200, 450, 300);
//    r.setSize(500,500);
        r.setVisible(true);
        r.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }



}
}
