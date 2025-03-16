package view;

import model.task.Task;
import model.user.User;
import model.user.Worker;
import service.TaskManager;
import model.task.Task;
import service.UserManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class UserDashboard  extends JFrame {
    private JLabel taskLabel;
    private JTable tasksTable;
    private JComboBox filterCombo;
    private JButton logOutButton;
    private JPanel mainPanel;
    private JPanel textPanel;
    private JPanel filterPanel;
    private JPanel tablePanel;
    private JScrollPane scrollPane;
    private User user;
    private TaskManager taskManager;
    private UserManager userManager;

    public UserDashboard(User user,TaskManager taskManager,UserManager userManager) {
        this.user = user;
        this.taskManager=taskManager;
        this.userManager=userManager;


        // Table column names
        setTitle("Worker Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"Task Title", "Task Description", "Priority", "Due Date", "Completed"};
        // Table model to hold data
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        tasksTable.setModel(model);

        // Populate table with worker's tasks
        loadWorkerTasks(model);
        tasksTable.getColumnModel().getColumn(0).setMinWidth(250);


        setContentPane(mainPanel);
        setVisible(true);


        filterCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedFilter = (String) filterCombo.getSelectedItem();
                switch (selectedFilter) {
                    case "All":
                        loadWorkerTasks(model);
                        break;
                    case "Priority":
                        String priorityInput = JOptionPane.showInputDialog(filterCombo, "Enter priority (1-3):");
                        if (priorityInput != null) {
                            try {
                                int priority = Integer.parseInt(priorityInput);
                                if (priority < 1 || priority > 3) {
                                    JOptionPane.showMessageDialog(filterCombo, "Invalid priority! Enter a value between 1 and 3.");
                                } else {
                                    List<Task> filteredTasks=taskManager.filterTaskByPriority(user.getUsername(), priority);
                                    printFilteredTasks(model,filteredTasks);
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(filterCombo, "Please enter a valid number.");
                            }
                        }
                        break;
                    case "Due date":
                        String dateInput = JOptionPane.showInputDialog(filterCombo, "Enter Due Date (YYYY-MM-DD):");

                        if (dateInput != null) {
                            try {
                                LocalDate dueDate = LocalDate.parse(dateInput);
                                String[] options = {"on", "before", "after"};
                                String condition = (String) JOptionPane.showInputDialog(
                                        filterCombo,
                                        "Select condition:",
                                        "Filter by Due Date",
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,
                                        options,
                                        options[0] // Default selection
                                );
                                List<Task> filteredTasks=taskManager.filterTaskByDueDate(user.getUsername(), dueDate,condition);
                                printFilteredTasks(model,filteredTasks);
                            } catch (DateTimeParseException ex) {
                                JOptionPane.showMessageDialog(filterCombo, "Invalid date format! Please use YYYY-MM-DD.");
                            }
                        }
                        break;
                    case "Completion status":
                        boolean isCompleted = JOptionPane.showConfirmDialog(
                                filterCombo,
                                "Show completed tasks?",
                                "Filter",
                                JOptionPane.YES_NO_OPTION
                        ) == JOptionPane.YES_OPTION;


                        List<Task> filteredTasks=taskManager.filterTaskByCompletedStatus(user.getUsername(), isCompleted);
                        printFilteredTasks(model,filteredTasks);
                }
            }
        });
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Register registerForm = new Register(userManager, taskManager);
                registerForm.setVisible(true);

            }
        });
    }
    private void loadWorkerTasks(DefaultTableModel model) {
        model.setRowCount(0);
        // Fetch tasks assigned to this worker from the database
        List<Task> workerTasks =taskManager.listUserTasks(user.getUsername());
        for(Task task:workerTasks){
            model.addRow(new Object[]{
                    task.getTaskTitle(),
                    task.getTaskDescription(),
                    task.getPriority(),
                    task.getDueDate(),
                    task.getIsCompleted()
            });
        }

    }
    private void printFilteredTasks(DefaultTableModel model,List<Task> taskList) {
        model.setRowCount(0);
        for (Task task : taskList) {
            model.addRow(new Object[]{
                    task.getTaskTitle(),
                    task.getTaskDescription(),
                    task.getPriority(),
                    task.getDueDate(),
                    task.getIsCompleted()
            });

        }

    }

}
