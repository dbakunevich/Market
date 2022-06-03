package database;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;


public class TestDBWorker {
    static Connection connection_main;


    @BeforeAll
    public static void init() {
        Logger logger = null;
        Statement statement;
        Connection connection_main;
        String host = "jdbc:mysql://51.250.16.106:3306/upprpo";
        String login = "root";
        String password = "root";
        try {
            connection_main = DriverManager.getConnection(host, login, password);
            statement = connection_main.createStatement();
        } catch (SQLException ignored) {
        }
    }

    @Test
    public static void connectionTrueTest() {
        Logger logger = null;
        Statement statement;
        Connection connection;
        String host = "jdbc:mysql://51.250.16.106:3306/upprpo";
        String login = "root";
        String password = "root";
        try {
            connection = DriverManager.getConnection(host, login, password);
            assertNotNull(connection);
            assertFalse(connection.isClosed());
            statement = connection.createStatement();
        } catch (SQLException ignored) {
        }

    }

    @Test
    public static void connectionFalseTest() {
        Logger logger = null;
        Statement statement;
        Connection connection = null;
        String host = "jdbc:mysql://51.250.16.106:3306/upprpo";
        String login = "root";
        String password = "root123";
        try {
            connection = DriverManager.getConnection(host, login, password);
            statement = connection.createStatement();
        } catch (SQLException ignored) {
        }
        assertNull(connection);
    }

    @Test
    public static void addUserTrueTest() {
        boolean bool;
        DBWorker worker = new DBWorker();
        bool = worker.addUser("mmikhajlov2", "123");
        assertTrue(bool);
    }
    
    @AfterAll
    public static void close() {
        try {
            connection_main.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
