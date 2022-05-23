package database;

import java.sql.*;

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
        }

//        try {
//
////            ResultSet resultSet = statement.executeQuery(query_user);
////            while (resultSet.next()) {
////                System.out.println(resultSet.getString(1));
////                System.out.println(resultSet.getString(2));
////
////            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
    }

    public void addUser(String username, String password) {
        try {
            statement.execute("insert into  users values (" + "'" + username + "'," + "'" + password + "'," + " CURRENT_TIMESTAMP" + ")");
        } catch (SQLException e) {
            e.printStackTrace();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Неверный логин или пароль!";
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }
}
