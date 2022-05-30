package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBWorker {
    private static final String URL = "jdbc:mysql://localhost:3306/upprpo";
    private static final String USERNAME = "root";
    private static final String PAS = "root";
    static Logger logger;

    private Statement statement;

    public DBWorker() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PAS)) {
            statement = connection.createStatement();
        } catch (SQLException e) {
            logger.log(Level.WARNING,"Connection is false!");
            System.exit(1);
        }
    }

    public Boolean addUser(String username, String password) {
        try {
            statement.execute("insert into  users values (" + "'" + username + "'," + "'" + password + "'," + " CURRENT_TIMESTAMP" + ")");
            return true;
        } catch (SQLException e) {
            logger.log(Level.WARNING,"Can't create new user!");
            return false;
        }
    }

    public String findUser(String username, String password) {
        String result;
        String query = "select username from users where username = " + "'" + username + "'" + " and password = " + "'" + password + "'";
        try (ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                result = resultSet.getString(1);
                statement.executeUpdate("update users set last_date = current_timestamp where username = " + "'" + result + "'");
                return result;
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING,"Can't find this user!");
        }
        return "Неверный логин или пароль!";
    }

    public Boolean addHistory(String username, String content) {
        try {
            statement.execute("insert into  users values (" + "'" + username + "'," + "'" + content + "'," + " CURRENT_TIMESTAMP" + ")");
            return true;
        } catch (SQLException e) {
            logger.log(Level.WARNING,"Cant't add new history of search!");
            return false;
        }
    }

    public List<String> findHistory(String username) {
        List<String> results = null;
        String query = "select distinct content from search_history where username = " + "'" + username + "'";
        try (ResultSet resultSet = statement.executeQuery(query)) {
            results = new ArrayList<>();
            while (resultSet.next()) {
                results.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING,"Can't find history of search!");
        }
        return results;
    }
}
