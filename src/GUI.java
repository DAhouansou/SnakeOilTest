import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class GUI implements ActionListener {

    private int count = 0;
    private JFrame frame;
    private JPanel panel;


    private JLabel emailLabel;
    private JLabel fNameLabel;
    private JLabel lNameLabel;
    private JLabel commentLabel;

    private JTextField emailInput;
    private JTextField fNameInput;
    private JTextField lNameInput;
    private JTextField commentInput;

    private JLabel ratingBoxLabel;
    private JComboBox ratingBox;

    private JButton apply;
    private JButton viewData;

    private JLabel space;

    private JLabel label;

    private JLabel emailWarning;
    private JLabel fNameWarning;
    private JLabel lNameWarning;
    private JLabel commentWarning;


    String[] boxOptions = {"5", "4", "3", "2", "1"};

    public GUI() {
        JFrame frame = new JFrame();



        label = new JLabel("Hello");

        emailLabel = new JLabel(" Enter email address:");
        emailInput = new JTextField();

        fNameLabel = new JLabel("Enter first name");
        fNameInput = new JTextField();

        lNameLabel = new JLabel("Enter your last name");
        lNameInput = new JTextField();

        commentLabel = new JLabel("Please enter any feedback that you have about our company. *Optional");
        commentInput = new JTextField();

        ratingBoxLabel = new JLabel("Please rate our services, 1-5. 1 is lowest, 5 is highest");
        ratingBox = new JComboBox<>(boxOptions);


        apply = new JButton("Press to confirm");
        apply.addActionListener(this);

        JButton viewData = new JButton("Press to view collected data");
        viewData.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new dataViewer();
            }
        });


        emailWarning = new JLabel("");
        emailWarning.setForeground(Color.RED);

        fNameWarning = new JLabel("");
        fNameWarning.setForeground(Color.RED);

        lNameWarning =new JLabel("");
        lNameWarning.setForeground(Color.RED);

        commentWarning = new JLabel("");
        commentWarning.setForeground(Color.RED);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        space = new JLabel("\n");

        panel = new JPanel();

        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));


        panel.add(emailLabel);panel.add(emailWarning);
        panel.add(emailInput);



        panel.add(fNameLabel);panel.add(fNameWarning);
        panel.add(fNameInput);




        panel.add(lNameLabel);
        panel.add(lNameWarning);
        panel.add(lNameInput);


        panel.add(commentLabel);
        panel.add(commentWarning);
        panel.add(commentInput);



        panel.add(ratingBoxLabel);
        panel.add(ratingBox);



        panel.add(apply);

        panel.add(viewData);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Bank rating");
        frame.pack();
        frame.setVisible(true);


    }

    public static void main(String[] args) {

        new GUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final String receivedEmail = emailInput.getText();
        final String receivedFName = fNameInput.getText();
        final String receivedLName = lNameInput.getText();
        final String receivedComment = commentInput.getText();
        Object receivedRatingObj = ratingBox.getSelectedItem();
        final String receivedRating = receivedRatingObj.toString();

        String url = "jdbc:mysql://localhost:3306/snakeoiltest";
        String username = "root";
        String password = "password";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();

            ResultSet query2 = statement.executeQuery("SELECT email_address FROM visitors;");


            Statement stmt = connection.createStatement();

            ArrayList votedEmails = new ArrayList();

            while (query2.next()) {
                votedEmails.add(query2.getString(1));
            }









        if (votedEmails.contains(receivedEmail)) {
            emailWarning.setText("This email has already been used.");

        }else
            if(!receivedEmail.contains("@") || !receivedEmail.contains(".")) {
            emailWarning.setText("Did not meet the required email parameters.");

        } else if (receivedEmail.length()>40) {
            emailWarning.setText("Inputted email is too long.");

        } else if (receivedFName.isBlank())  {
            fNameWarning.setText("Please input a name.");
            emailWarning.setText("");
        } else if (receivedFName.length()>20) {
            fNameWarning.setText("Inputted name is too long.");
            emailWarning.setText("");
        } else if (receivedLName.isBlank()) {
            lNameWarning.setText("Please input a last name.");
            fNameWarning.setText("");
            emailWarning.setText("");
        } else if (receivedLName.length()>20) {
            lNameWarning.setText("Inputted last name is too long.");
            fNameWarning.setText("");
            emailWarning.setText("");
        } else if (receivedComment.length()>100) {
            commentWarning.setText("Comment is too long");
            fNameWarning.setText("");
            emailWarning.setText("");
            lNameWarning.setText("");
        } else {

            fNameWarning.setText("");
            lNameWarning.setText("");
            emailWarning.setText("");


            try {
                String query1 = "INSERT INTO visitors VALUES('" + receivedEmail + "', '" + receivedFName + "', '" + receivedLName + "', '" + receivedComment + "'," + receivedRating + ");";


                stmt.execute(query1);

                connection.close();

            } catch (Exception f) {
                System.out.println("exception");
            }

            emailInput.setText("");
            fNameInput.setText("");
            lNameInput.setText("");
            commentInput.setText("");
            ratingBox.setSelectedItem(1);

            emailWarning.setText("");
            fNameWarning.setText("");
            lNameWarning.setText("");
        }
        } catch (Exception f) {
            System.out.println("exception");
        }
    }
}