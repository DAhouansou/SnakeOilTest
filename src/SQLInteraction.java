import java.sql.*;


public class SQLInteraction {



    public static void main(String[] args) {

        String receivedEmail = null;
        String fName = null;
        String lName = null;
        String comment = null;
        String rating = null;

        GUI rank = new GUI();

        String url = "jdbc:mysql://localhost:3306/snakeoiltest";
        String username = "root";
        String password = "password";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();


            Statement stmt = connection.createStatement();
            String query1 = "INSERT INTO visitors VALUES('" + receivedEmail + "', '"+ fName +"', '" + lName + "', '" + comment + "',"+ rating + ");";


            stmt.execute(query1);



            connection.close();

        } catch (Exception e) {
            System.out.println("exception");
        }
    }
}


