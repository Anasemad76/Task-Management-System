package view;


import model.task.Task;
import model.user.User;
import service.TaskManager;
import service.UserManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminDashboard extends JFrame{
    private JPanel mainPanel;
    private JPanel textPanel;
    private JLabel taskLabel;
    private JPanel filterPanel;
    private JComboBox filterCombo;
    private JPanel tablePanel;
    private JScrollPane scrollPane;
    private JTable tasksTable;
    private JButton logOutButton;

    private User user;
    TaskManager taskManager;
    UserManager userManager;

    public AdminDashboard(User user,TaskManager taskManager,UserManager userManager){
        this.user = user;
        this.taskManager = taskManager;
        this.userManager = userManager;



        setTitle("Admin Dashboard");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table Setup
        String[] columnNames = {"Task ID", "Task Title", "Description","Assigned User", "Priority", "Due Date", "Completed"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        tasksTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tasksTable);

        loadTasks(model);
        tasksTable.getColumnModel().getColumn(0).setMinWidth(250);


        // Buttons
        JButton createTaskButton = new JButton("Create Task");
        JButton editTaskButton = new JButton("Edit Task");
        JButton deleteTaskButton = new JButton("Delete Task");
        logOutButton = new JButton("Log Out");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createTaskButton);
        buttonPanel.add(editTaskButton);
        buttonPanel.add(deleteTaskButton);
        buttonPanel.add(logOutButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

//        // Load admin's tasks
//        loadAdminTasks(model);

        // Button Actions
//      createTaskButton.addActionListener(e ->      new CreateTaskForm(taskManager, this););
////        editTaskButton.addActionListener(e -> openEditTaskForm(model));
////        deleteTaskButton.addActionListener(e -> deleteSelectedTask(model));
////        logOutButton.addActionListener(e -> logOut());

        setVisible(true);
    }
    private void loadTasks(DefaultTableModel model) {
        model.setRowCount(0);
        // Fetch tasks assigned to this worker from the database
        List<Task> allTasks =taskManager.listTasks();
        for(Task task:allTasks){
            model.addRow(new Object[]{
                    task.getTaskTitle(),
                    task.getTaskDescription(),
                    task.getPriority(),
                    task.getDueDate(),
                    task.getIsCompleted()
            });
        }
    }

//    public static void main(String[] args) {
//        new AdminDashboard();
//    }
}
