package view;

import model.task.HighPriorityTask;
import model.task.Task;
import model.user.User;
import service.TaskManager;
import service.UserManager;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminDashboard extends JFrame {
    private JPanel mainPanel;
    private JTextField titleField;
    private JTextField descField;
    private JTextField assignedUserField;
    private JTextField dateField;
    private JButton addButton;
    private JButton clearButton;
    private JButton deleteButton;
    private JTable tasksTable;
    private JScrollPane scrollPane;
    private JComboBox completedStatusComboBox;
    private JComboBox priorityComboBox;
    private JButton editButton;
    private JButton logOutButton;
    private JButton viewHighPriorityTaskButton;
    private JButton backButton;
    private TaskManager taskManager;
    private User user;
    private UserManager userManager;
    private String titleId;
    private String titleTitle;
    public AdminDashboard(User user, TaskManager taskManager, UserManager userManager) {
        this.taskManager = taskManager;
        this.user = user;
        this.userManager = userManager;
        backButton.setVisible(false);
        // Table column names
        setTitle("Worker Dashboard");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"ID", "Title", "Description", "Assigned User", "Completed", "Priority", "Due Date"};

        // Table model to hold data
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        tasksTable.setModel(model);


        loadWAllTasks(model);
//        tasksTable.getColumnModel().getColumn(0).setMinWidth(250);
        setContentPane(mainPanel);
        setVisible(true);


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String description = descField.getText();
                String assignedUser = assignedUserField.getText();
                String isCompleted = (String) completedStatusComboBox.getSelectedItem();
                String priority = (String) priorityComboBox.getSelectedItem();
                String dueDate = dateField.getText();
                if (title.isEmpty() || description.isEmpty() || assignedUser.isEmpty() || priority.isEmpty() || dueDate.isEmpty()) {
                    JOptionPane.showMessageDialog(tasksTable, "Please Enter all fields", "Try Again", JOptionPane.ERROR_MESSAGE);
                } else {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    try {
                        LocalDate localDate = LocalDate.parse(dueDate, formatter);
                        Task newTask = null;
                        if (priority.equals("3")) {
                            newTask = new HighPriorityTask(title, description, Boolean.parseBoolean(isCompleted), localDate);

                        } else {
                            newTask = new Task(title, description, Boolean.parseBoolean(isCompleted), Integer.parseInt(priority), localDate);
                        }
                        boolean isSuccessful = taskManager.addTask(assignedUser, newTask);
                        if (isSuccessful) {
                            model.addRow(new Object[]{
                                    newTask.getTaskId(),
                                    newTask.getTaskTitle(),
                                    newTask.getTaskDescription(),
                                    newTask.getAssignedUser().getUsername(),
                                    newTask.getIsCompleted(),
                                    newTask.getPriority(),
                                    newTask.getDueDate()

                            });

                        } else {
                            JOptionPane.showMessageDialog(tasksTable, "Error happened in database", "Try Again", JOptionPane.ERROR_MESSAGE);

                        }
                    } catch (DateTimeParseException ex) {
                        JOptionPane.showMessageDialog(tasksTable, "Invalid date format! Please enter in yyyy-MM-dd format.", "Try Again", JOptionPane.ERROR_MESSAGE);
                    }


                }


            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                titleField.setText("");
                descField.setText("");
                assignedUserField.setText("");
                completedStatusComboBox.setSelectedIndex(0);
                completedStatusComboBox.setSelectedIndex(0);
                dateField.setText("");

            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tasksTable.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(tasksTable, "Please select a row from the table", "Try Again", JOptionPane.ERROR_MESSAGE);

                } else {
                    boolean isSuccessful = taskManager.deleteTask(tasksTable.getValueAt(row, 1).toString());
                    if (isSuccessful) {
                        model.removeRow(row);
                    } else {
                        JOptionPane.showMessageDialog(tasksTable, "Error happened in database", "Try Again", JOptionPane.ERROR_MESSAGE);
                    }


                }
            }
        });
        Map<String, Object> changedValues = new HashMap<>();

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tasksTable.getSelectedRow();
                System.out.println(row);
                if (row < 0) {
                    JOptionPane.showMessageDialog(deleteButton, "Please select a row from the table", "Try Again", JOptionPane.ERROR_MESSAGE);

                } else {
                    if (changedValues.isEmpty()) {
                        JOptionPane.showMessageDialog(deleteButton, "Select something to change", "Try Again", JOptionPane.ERROR_MESSAGE);
                    } else {
                            System.out.println("HERE : " + changedValues);
                            boolean isSuccessful = false;
                            if (changedValues.containsKey("isApproved")) {
                                if (changedValues.get("isApproved").toString().equals("true")) {
                                    isSuccessful = taskManager.approveTask(tasksTable.getValueAt(row, 1).toString());
                                    if (!isSuccessful) {
                                        JOptionPane.showMessageDialog(tasksTable, "Error happened in database , Task must be completed before approval! ", "Try Again", JOptionPane.ERROR_MESSAGE);
                                        changedValues.remove("isApproved");
                                        return;

                                    }
                                    changedValues.remove("isApproved");
                                    System.out.println(changedValues);
                                } else {
                                    JOptionPane.showMessageDialog(tasksTable, "Only allowed value is true! ", "Try Again", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            }
                            if (changedValues.containsKey("priority")) {
                                    System.out.println("ENTERED HERE");
                                    isSuccessful = taskManager.editTaskPriorty(Integer.parseInt(titleId), (Integer) changedValues.get("priority"));
                                    if (!isSuccessful) {
                                        JOptionPane.showMessageDialog(tasksTable, "Error happened in database , Task Priority wasn't updated successfully! ", "Try Again", JOptionPane.ERROR_MESSAGE);
                                        changedValues.remove("priority");
                                        return;
                                    }
                                    changedValues.remove("priority");
                                    System.out.println(changedValues);
                            }

                            if (!changedValues.isEmpty()) { // used to update anything else but priority & isApproved
                                    //TO EDIT BY ID IF THE THING I WANTED TO EDIT WAS TITLE
                                    System.out.println("HERE 2 : " + changedValues);

                                    if (titleId == null) {
                                        JOptionPane.showMessageDialog(tasksTable, "Cannot Edit Task ID!", "Try Again", JOptionPane.ERROR_MESSAGE);
                                        return;
                                    }
                                if (changedValues.containsKey("title")) {
                                    isSuccessful = taskManager.editTask(titleId, changedValues, true);

                                }else{
                                    isSuccessful = taskManager.editTask(titleTitle, changedValues, false);

                                }

                            }

                                if (isSuccessful) {
                                    JOptionPane.showMessageDialog(tasksTable, "Task updated Successfully", "Ok", JOptionPane.INFORMATION_MESSAGE);
                                    loadWAllTasks(model);
                                    changedValues.clear();

                                } else {
                                    JOptionPane.showMessageDialog(tasksTable, "Error happened in database", "Try Again", JOptionPane.ERROR_MESSAGE);

                                }
                            }
                    }


                }



        });
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int col = e.getColumn();
                    if (row != -1 && col != -1) {
                        Object newValue = model.getValueAt(row, col);
                        String columnName = model.getColumnName(col);
                        if (col != 0) {
                            titleId = model.getValueAt(row, 0).toString();
                            titleTitle=model.getValueAt(row, 1).toString();
                        }


                        switch (columnName) {
                            case "Title":
                                columnName = "taskTitle";
                                break;
                            case "Description":
                                columnName = "taskDescription";
                                break;
                            case "Assigned User":
                                columnName = "assignedUser";
                                break;
                            case "Completed":
                                columnName = "isCompleted";
                                newValue=Boolean.parseBoolean(newValue.toString());
                                break;
                            case "Priority":
                                columnName = "priority";
                                newValue=Integer.parseInt(newValue.toString());
                                break;
                            case "Due Date":
                                columnName = "dueDate";
                                newValue = LocalDate.parse(newValue.toString());
                                break;

                            case "Approve":
                                columnName = "isApproved";
                                newValue = Boolean.parseBoolean(newValue.toString());
                                break;

                        }
                        changedValues.put(columnName, newValue);


                    }
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
        viewHighPriorityTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backButton.setVisible(true);
                String[] highPriorityColumns = {"ID", "Title", "Description", "Assigned User", "Completed", "Priority", "Due Date", "Approve"};

                DefaultTableModel model = (DefaultTableModel) tasksTable.getModel();

                // Check if the "Approve" column already exists to avoid duplication
                if (model.getColumnCount() != highPriorityColumns.length) {
                    model.setColumnIdentifiers(highPriorityColumns);
                }

                // Clear table rows before inserting new data
                model.setRowCount(0);

                // Fetch high-priority tasks (priority == 3) from database
                List<HighPriorityTask> highPriorityTasks = taskManager.filterAllHighPriorityTasks();


                for (HighPriorityTask task : highPriorityTasks) {
                    Object[] rowData = {
                            task.getTaskId(),
                            task.getTaskTitle(),
                            task.getTaskDescription(),
                            task.getAssignedUser().getUsername(),
                            task.getIsCompleted(),
                            task.getPriority(),
                            task.getDueDate(),
                            task.getisApproved()
                    };
                    model.addRow(rowData);
                }


            }

        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadWAllTasks(model);
            }
        });
    }

    private void loadWAllTasks(DefaultTableModel model){
        backButton.setVisible(false);
        String[] columnNames = {"ID", "Title", "Description", "Assigned User", "Completed", "Priority", "Due Date"};
        model = (DefaultTableModel) tasksTable.getModel();
        model.setColumnIdentifiers(columnNames);
        model.setRowCount(0);
        model.setRowCount(0);
        List<Task> allTasks =taskManager.listTasks();
        for(Task task:allTasks){
            model.addRow(new Object[]{
                    task.getTaskId(),
                    task.getTaskTitle(),
                    task.getTaskDescription(),
                    task.getAssignedUser().getUsername(),
                    task.getIsCompleted(),
                    task.getPriority(),
                    task.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            });
        }

    }


}
