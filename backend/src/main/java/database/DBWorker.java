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
            logger.log(Level.WARNING, "Connection is false!");
            System.exit(1);
        }
    }

    public Boolean addUser(String username, String password) {
        PreparedStatement preparedStatementInsert = null;
        try {

            String insert = "insert into  users (username, password, last_date) values  (?, ?, CURRENT_TIMESTAMP)";
            preparedStatementInsert = connection.prepareStatement(insert);
            preparedStatementInsert.setString(1, username);
            preparedStatementInsert.setString(2, password);
            preparedStatementInsert.executeUpdate();
//            statement.execute("insert into  users values (" + "'" + username + "'," + "'" + password + "'," + " CURRENT_TIMESTAMP" + ")");
            return true;

        } catch (SQLException e) {
            logger.log(Level.WARNING, "Can't create new user!");
            return false;
        } finally {
            try {
                if (preparedStatementInsert != null) {
                    preparedStatementInsert.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String findUser(String username, String password) {
        String query = "select username from users where username = ? and password = ?";
        String update = "update users set last_date = current_timestamp where username = ?";
        String result;
        PreparedStatement preparedStatementQuery = null;
        PreparedStatement preparedStatementUpdate = null;
        ResultSet resultSet = null;
        //String query = "select username from users where username = " + "'" + username + "'" + " and password = " + "'" + password + "'";
        try {
            preparedStatementQuery = connection.prepareStatement(query);
            preparedStatementQuery.setString(1, username);
            preparedStatementQuery.setString(2, password);
            resultSet = preparedStatementQuery.executeQuery();

            while (resultSet.next()) {
                result = resultSet.getString(1);
                preparedStatementUpdate = connection.prepareStatement(update);
                preparedStatementUpdate.setString(1, result);
                preparedStatementUpdate.executeUpdate();
                //statement.executeUpdate("update users set last_date = current_timestamp where username = " + "'" + result + "'");
                return result;
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Can't find this user!");
        } finally {
            try {
                if (preparedStatementQuery != null && preparedStatementUpdate != null && resultSet != null) {
                    preparedStatementQuery.close();
                    preparedStatementUpdate.close();
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "Неверный логин или пароль!";
    }

    public Boolean addHistory(String username, String content) {
        String insert = "insert into  users (username, password, last_date) values (?, ?, CURRENT_TIMESTAMP)";
        PreparedStatement preparedStatementInsert = null;
        try {
            preparedStatementInsert = connection.prepareStatement(insert);
            preparedStatementInsert.setString(1, username);
            preparedStatementInsert.setString(2, content);
            preparedStatementInsert.executeUpdate();
            //statement.execute("insert into  users values (" + "'" + username + "'," + "'" + content + "'," + " CURRENT_TIMESTAMP" + ")");
            return true;
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Cant't add new history of search!");
            return false;
        } finally {
            try {
                if (preparedStatementInsert != null) {
                    preparedStatementInsert.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<String> findHistory(String username) {
        List<String> results = null;
        PreparedStatement preparedStatementQuery = null;
        ResultSet resultSet = null;
        String query = "select distinct content from search_history where username = ?";

        try {
            results = new ArrayList<>();
            preparedStatementQuery = connection.prepareStatement(query);
            preparedStatementQuery.setString(1, username);
            resultSet = preparedStatementQuery.executeQuery();
            while (resultSet.next())
                results.add(resultSet.getString(1));
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Can't find history of search!");
        } finally {
            try {
                if (preparedStatementQuery != null && resultSet != null) {
                    preparedStatementQuery.close();
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

}
