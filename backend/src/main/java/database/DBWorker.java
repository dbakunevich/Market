package database;

import java.sql.*;
import java.util.ArrayList;

public class DBWorker {
    private static final String URL = "jdbc:mysql://localhost:3306/upprpo";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static Connection connection;
    private static Statement statement;

    public DBWorker() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public Boolean addUser(String username, String password) {
        try {
            statement.execute("insert into  users values (" + "'" + username + "'," + "'" + password + "'," + " CURRENT_TIMESTAMP" + ")");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String findUser(String username, String password) {
        String result;
        String query = "select username from users where username = " + "'" + username + "'" + " and password = " + "'" + password + "'";
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                result = resultSet.getString(1);
                statement.executeUpdate("update users set last_date = current_timestamp where username = " + "'" + result + "'");
                return result;
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Неверный логин или пароль!";
    }

    public Boolean addHistory(String username, String content) {
        try {
            statement.execute("insert into  users values (" + "'" + username + "'," + "'" + content + "'," + " CURRENT_TIMESTAMP" + ")");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<String> findHistory(String username) {
        ArrayList<String> results = null;

        String query = "select distinct content from search_history where username = " + "'" + username + "'";
        try {
            results = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                results.add(resultSet.getString(1));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    private Connection getConnection() {
        return connection;
    }
}
