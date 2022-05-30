package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.*;
import database.MyProperties;

public class DBWorker {
    private static Statement statement;
    private static Connection connection;
    static Logger logger;

    String host;
    String login;
    String password;

    @Autowired 
    private MyProperties myProperties; 

    public DBWorker() {
        try {
            myProperties = new MyProperties();
            host = myProperties.getHost();
            login = myProperties.getLogin();
            password = myProperties.getPassword();
            connection = DriverManager.getConnection(host, login, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            logger.log(Level.WARNING,"Connection is false!");
            System.exit(1);
        }
    }

    public Boolean addUser(String username, String password) {
        try {
            PreparedStatement preparedStatement = null;
            String insert = "insert into  users values (?, ?, CURRENT_TIMESTAMP)";
            preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.execute();
//            statement.execute("insert into  users values (" + "'" + username + "'," + "'" + password + "'," + " CURRENT_TIMESTAMP" + ")");
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
            while (resultSet.next())
                results.add(resultSet.getString(1));
        } catch (SQLException e) {
            logger.log(Level.WARNING,"Can't find history of search!");
        }
        return results;
    }
}
