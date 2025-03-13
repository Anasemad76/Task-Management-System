import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Managers.TaskManager;
import Managers.UserManager;

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

    public Register() {
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
                userManager = new UserManager();
                taskManager = new TaskManager();
                userManager.registerUser(username, pass1, isAdmin,taskManager);
                JOptionPane.showMessageDialog(registerBtn,userName.getText()+" ,Hello");
                UserDashboard userDashboard = new UserDashboard();
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
    Register r=new Register();
    r.setContentPane(r.registerPanel);
    r.setTitle("Register");
    r.setBounds(400, 200, 450, 300);
//    r.setSize(500,500);
    r.setVisible(true);
    r.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}
}
