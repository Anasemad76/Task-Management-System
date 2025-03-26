package view;

import model.task.HighPriorityTask;
import model.task.Task;
import model.user.User;
import service.TaskManager;
import service.UserManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private JButton markAsCompletedButton;
    private JButton backButton;
    private User user;
    private TaskManager taskManager;
    private UserManager userManager;
    String titleMarkAsCompletedTask;

    public UserDashboard(User user,TaskManager taskManager,UserManager userManager) {
        this.user = user;
        this.taskManager=taskManager;
        this.userManager=userManager;
        this.titleMarkAsCompletedTask=null;




        // Table column names
        setTitle("Worker Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"Task ID","Task Title", "Task Description", "Priority", "Due Date", "Completed"};
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
                String[] columnNames = {"Task ID","Task Title", "Task Description", "Priority", "Due Date", "Completed"};
                DefaultTableModel model = (DefaultTableModel) tasksTable.getModel();
                model.setColumnIdentifiers(columnNames);

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
                                    List<Task> filteredTasks=taskManager.filterTaskByPriority(user, priority);
                                    if (priority==3){
                                        String[] highPriorityColumns ={"Task ID","Task Title", "Task Description", "Priority", "Due Date", "Completed","Approved"};
                                         model = (DefaultTableModel) tasksTable.getModel();

                                        // Check if the "Approve" column already exists to avoid duplication
                                        if (model.getColumnCount() != highPriorityColumns.length) {
                                            model.setColumnIdentifiers(highPriorityColumns);
                                        }

                                        printFilteredTasks(model,filteredTasks,"high");
                                        return;

                                    }
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
                                List<Task> filteredTasks=taskManager.filterTaskByDueDate(user, dueDate,condition);
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


                        List<Task> filteredTasks=taskManager.filterTaskByCompletedStatus(user, isCompleted);
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

        markAsCompletedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tasksTable.getSelectedRow();
                System.out.println(row);
                if (row < 0) {
                    JOptionPane.showMessageDialog(markAsCompletedButton, "Please select a row from the table", "Try Again", JOptionPane.ERROR_MESSAGE);

                } else {
                    boolean isSuccessful=taskManager.markTaskAsCompleted(user.getUsername(), titleMarkAsCompletedTask);
                    if(isSuccessful){
                        JOptionPane.showMessageDialog(tasksTable, "Task Marked As Completed Successfully", "Ok", JOptionPane.INFORMATION_MESSAGE);
                        loadWorkerTasks(model);
                    }else{
                        JOptionPane.showMessageDialog(tasksTable, "Error happened in database", "Try Again", JOptionPane.ERROR_MESSAGE);
                    }

                }

            }
        });


        tasksTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) { // Prevents multiple triggers
                int row = tasksTable.getSelectedRow();
                if (row != -1) { // Ensure a row is selected
                    titleMarkAsCompletedTask = model.getValueAt(row, 1).toString();
                    System.out.println("Selected Task Title: " + titleMarkAsCompletedTask);
                }
            }
        });


    }


    private void loadWorkerTasks(DefaultTableModel model) {
        String[] columnNames = {"Task ID","Task Title", "Task Description", "Priority", "Due Date", "Completed"};
        model = (DefaultTableModel) tasksTable.getModel();
        model.setColumnIdentifiers(columnNames);
        model.setRowCount(0);
        // Fetch tasks assigned to this worker from the database
        List<Task> workerTasks =taskManager.listUserTasks(user);
        for(Task task:workerTasks){
            model.addRow(new Object[]{
                    task.getTaskId(),
                    task.getTaskTitle(),
                    task.getTaskDescription(),
                    task.getPriority(),
                    task.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    task.getIsCompleted()
            });
        }

    }
    private void printFilteredTasks(DefaultTableModel model,List<Task> taskList) {
        model.setRowCount(0);
        for (Task task : taskList) {
            model.addRow(new Object[]{
                    task.getTaskId(),
                    task.getTaskTitle(),
                    task.getTaskDescription(),
                    task.getPriority(),
                    task.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    task.getIsCompleted()
            });

        }

    }
    private void printFilteredTasks(DefaultTableModel model,List<Task> taskList,String condition) {
        model.setRowCount(0);
        for (Task task : taskList) {
            System.out.println("Task Class: " + task.getClass().getName());
            System.out.println(" is approved ? "+((HighPriorityTask)task).getisApproved());
            model.addRow(new Object[]{
                    task.getTaskId(),
                    task.getTaskTitle(),
                    task.getTaskDescription(),
                    task.getPriority(),
                    task.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    task.getIsCompleted(),
                    ((HighPriorityTask) task).getisApproved() ? "Approved ✅" : "Not Approved ❌"
            });

        }

    }


}
