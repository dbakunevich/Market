package database;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.logging.Logger;


public class TestDBWorker {
    static Connection connection_main;

    public static String[] generateRandomWords(int numberOfWords)
    {
        String[] randomStrings = new String[numberOfWords];
        Random random = new Random();
        for(int i = 0; i < numberOfWords; i++)
        {
            char[] word = new char[random.nextInt(8)+3];
            for(int j = 0; j < word.length; j++)
            {
                word[j] = (char)('a' + random.nextInt(26));
            }
            randomStrings[i] = new String(word);
        }
        return randomStrings;
    }

    @Test
    public void connectionTrueTest() {
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
    public void connectionFalseTest() {
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
    public void addUserTrueTest() {
        boolean bool;
        String[] str;
        DBWorker worker = new DBWorker();
        str = generateRandomWords(4);
        bool = worker.addUser(str[0] + str[1] + str[2] + str[3], "123");
        assertTrue(bool);
    }

    @Test
    public void addUserFalseTest() {
        boolean bool;
        DBWorker worker = new DBWorker();
        bool = worker.addUser("mmikhajlov", "123");
        assertFalse(bool);
    }

    @Test
    public void findUserTrueTest() {
        boolean bool = false;
        String str;
        DBWorker worker = new DBWorker();
        str = worker.findUser("123", "123");
        if (str.equals("123")) {
            bool = true;
        }
        assertTrue(bool);
    }

}
