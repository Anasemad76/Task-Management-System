package view;

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
    private TaskManager taskManager;
    private User user;
    private UserManager userManager;
    private String titleDB;
    public AdminDashboard(User user, TaskManager taskManager, UserManager userManager) {
        this.taskManager = taskManager;
        this.user = user;
        this.userManager = userManager;
        // Table column names
        setTitle("Worker Dashboard");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"ID","Title", "Description","Assigned User","Completed", "Priority", "Due Date"};

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
                String isCompleted=(String) completedStatusComboBox.getSelectedItem();
                String priority = (String) priorityComboBox.getSelectedItem();
                String dueDate = dateField.getText();
                if(title.isEmpty() || description.isEmpty() || assignedUser.isEmpty() || priority.isEmpty() || dueDate.isEmpty()) {
                    JOptionPane.showMessageDialog(tasksTable,"Please Enter all fields","Try Again",JOptionPane.ERROR_MESSAGE);
                }else{
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    try{
                        LocalDate localDate = LocalDate.parse(dueDate, formatter);
                        Task newTask=new Task(title,description,assignedUser,Boolean.parseBoolean(isCompleted),Integer.parseInt(priority),localDate);
                        boolean isSuccessful=taskManager.addTask(newTask);
                        if (isSuccessful) {
                            model.addRow(new Object[]{
                                    newTask.getTaskId(),
                                    newTask.getTaskTitle(),
                                    newTask.getTaskDescription(),
                                    newTask.getAssignedUser(),
                                    newTask.getIsCompleted(),
                                    newTask.getPriority(),
                                    newTask.getDueDate()
                            });

                        }else{
                            JOptionPane.showMessageDialog(tasksTable,"Error happened in database","Try Again",JOptionPane.ERROR_MESSAGE);

                        }
                    }catch (DateTimeParseException ex){
                        JOptionPane.showMessageDialog(tasksTable,"Invalid date format! Please enter in yyyy-MM-dd format.","Try Again",JOptionPane.ERROR_MESSAGE);
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
                    JOptionPane.showMessageDialog(tasksTable,"Please select a row from the table","Try Again",JOptionPane.ERROR_MESSAGE);

                }else{
                    boolean isSuccessful=taskManager.deleteTask(tasksTable.getValueAt(row,1).toString());
                    if (isSuccessful) {
                        model.removeRow(row);
                    }else{
                        JOptionPane.showMessageDialog(tasksTable,"Error happened in database","Try Again",JOptionPane.ERROR_MESSAGE);
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
                    JOptionPane.showMessageDialog(deleteButton,"Please select a row from the table","Try Again",JOptionPane.ERROR_MESSAGE);

                }else{
                    if(changedValues.isEmpty()){
                        JOptionPane.showMessageDialog(deleteButton,"Select something to change","Try Again",JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        boolean isSuccessful=taskManager.editTask(titleDB,changedValues);
                        if (isSuccessful) {
                            JOptionPane.showMessageDialog(tasksTable,"Task updated Successfully","Ok",JOptionPane.INFORMATION_MESSAGE);
                            loadWAllTasks(model);
                            changedValues.clear();

                        }else{
                            JOptionPane.showMessageDialog(tasksTable,"Error happened in database","Try Again",JOptionPane.ERROR_MESSAGE);
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
                            titleDB = model.getValueAt(row, 1).toString();
                        }


                        switch (columnName) {
                            case "Title":
                                columnName = "task_title";
                                break;
                            case "Description":
                                 columnName = "task_description";
                                 break;
                            case "Assigned User":
                                columnName = "assigned_user";
                                break;
                            case "Completed":
                                 columnName = "is_completed";
                                 break;
                            case "Priority":
                                 columnName = "priority";
                                 break;
                            case "Due Date":
                                 columnName = "due_date";
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
    }
    private void loadWAllTasks(DefaultTableModel model){
        model.setRowCount(0);
        List<Task> allTasks =taskManager.listTasks();
        for(Task task:allTasks){
            model.addRow(new Object[]{
                    task.getTaskId(),
                    task.getTaskTitle(),
                    task.getTaskDescription(),
                    task.getAssignedUser(),
                    task.getIsCompleted(),
                    task.getPriority(),
                    task.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            });
        }

    }


}
