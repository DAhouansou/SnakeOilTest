import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class dataViewer {

    private JFrame dataFrame;
    private JPanel dataPanel;
    private JTable dataTable;
    private JLabel ratingAverage;
    private JButton asd;

    public dataViewer() {

        JButton asd = new JButton();
        JFrame dataFrame = new JFrame();
        JPanel dataPanel = new JPanel();
        JLabel averageRating = new JLabel("The average rating is:");

        dataPanel.add(asd);

/////////////////////////////////////////////////////////////////////////////

        dataFrame.add(dataPanel, BorderLayout.CENTER);
        dataFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dataFrame.setTitle("Bank rating");
        dataFrame.pack();
        dataFrame.setVisible(true);
        dataPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        dataPanel.setLayout(new GridLayout(0, 1));


        JTable dataTable = new JTable();
        dataTable.setBounds(30, 40, 200, 300);
        JScrollPane sp = new JScrollPane(dataTable);
        dataFrame.add(sp);
        dataFrame.setSize(300, 400);
        dataFrame.setVisible(true);

        dataPanel.add(averageRating);
        averageRating.setVisible(true);
        averageRating.setSize(5, 5);
        averageRating.setBounds(100, 60, 20, 50);



////////////////////////////////////////////////////////////////////////////


        ArrayList dataFromSQL = new ArrayList();


        String url = "jdbc:mysql://localhost:3306/snakeoiltest";
        String username = "root";
        String password = "password";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet query3 = statement.executeQuery("SELECT * FROM visitors;");
            ResultSetMetaData rsmd = query3.getMetaData();
            DefaultTableModel model = (DefaultTableModel) dataTable.getModel();


            int cols = rsmd.getColumnCount();
            String[] colName = new String[cols];
            for (int i = 0; i < cols; i++)
                colName[i] = rsmd.getColumnName(i + 1);
            model.setColumnIdentifiers(colName);

            String email, fname, lname, comment, rating;

            while (query3.next()) {
                email = query3.getString(1);
                fname = query3.getString(2);
                lname = query3.getString(3);
                comment = query3.getString(4);
                rating = query3.getString(5);
                String[] row = {email, fname, lname, comment, rating};
                model.addRow(row);
            }


            Statement stmt = connection.createStatement();
            connection.close();
        } catch (Exception f) {
            System.out.println("exception");
        }
            int rowCount = dataTable.getRowCount();

              float sum = 0;
            for(int b = 0; b < rowCount; b++) {
                sum = sum + Integer.parseInt(dataTable.getValueAt(b, 4).toString());
            }

            float avg = sum / dataTable.getRowCount();
            averageRating.setText("The average of all ratings is: " + Float.toString(avg));

            System.out.println(avg);















/*
        try {
           // Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet query4 = statement.executeQuery("SELECT COUNT(rating) FROM visitors;");

            //ResultSetMetaData rsmd4 = query4.

            connection.close();
        } catch (Exception f) {
            System.out.println("exception2");
        }
    }
*/
        // public static void main(String[] args) {
        //   new dataViewer();
        // }


    }
}
