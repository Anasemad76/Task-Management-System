package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Main.*;
import Managers.TaskManager;
import Managers.UserManager;

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
                User user = userManager.loginUser(username,pass);
                if (user.getIsAdmin()) {
                    JOptionPane.showMessageDialog(loginButton,userName.getText()+" ,Hello");
                    Admin admin = new Admin(username, pass, taskManager);
                    admin.displayMenu();
                } else {
                    JOptionPane.showMessageDialog(loginButton,userName.getText()+" ,Hello");
                    Worker worker = new Worker(username, pass, taskManager);
                    UserDashboard userDashboard = new UserDashboard();
                    worker.displayMenu();
                }


            }
        });
    }


}
