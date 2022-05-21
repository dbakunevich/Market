package database;
import java.sql.*;

public class DBWorker {
    private static final String URL = "jdbc:mysql://localhost:3306/upprpoDB";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    Connection connection;

    public DBWorker() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if (!connection.isClosed()) {
                System.out.println("Connection is true!");
            }
        } catch (SQLException throwables) {
            System.err.println("Can't get driver connection!");
        }
    }

    public Connection getConnection() {
        return connection;
    }
}