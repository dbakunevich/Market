package database;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        DBWorker worker = new DBWorker();
        String query = "SELECT * FROM users;";
        try {
            Statement statement = worker.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}