import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

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

    public UserDashboard() {


        // Table column names
        setTitle("Worker Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        setLayout(new BorderLayout());
        String[] columnNames = {"Task Title", "Task Description", "Priority", "Due Date", "Completed"};
        // Table model to hold data
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        tasksTable = new JTable(model);

        // Populate table with worker's tasks
        //loadWorkerTasks(model);
        model.addRow(new Object[]{"ay","haga",3,2002-03-03,false});
        tasksTable.getColumnModel().getColumn(0).setMinWidth(250);

        setContentPane(mainPanel);
        setVisible(true);

    }

    public static void main(String[] args) {
        UserDashboard userDashboard = new UserDashboard();

    }


}
