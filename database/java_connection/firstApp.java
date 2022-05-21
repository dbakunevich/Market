import java.io.InputStream;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DBWorker worker = new DBWorker();
        String query = "SELECT * FROM USERS;";
        Scanner in = new Scanner(System.in);
        System.out.print("Input your USER_ID: ");
        int in_user_id = in.nextInt();
        System.out.print("Input TASK_ID to edit: ");
        int in_task_id = in.nextInt();
        System.out.print("Input STATUS to edit: ");
        int in_status = in.nextInt();

        try {
            Statement statement = worker.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                User user = new User(resultSet.getInt(1), resultSet.getString(2)
                        , resultSet.getInt(3), resultSet.getString(4));
                System.out.println(user);
            }

//            Task task = new Task(resultSet.getInt(1), resultSet.getString(2)
//                    , resultSet.getInt(3), resultSet.getInt(4));
//            System.out.println(task);



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
