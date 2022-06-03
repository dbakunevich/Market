package database;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class TestUser {

    @Test
    public void getUserTrueTest() {
        String username;
        boolean bool = false;
        User user = new User("mmikhajlov", "123");
        username = user.getUsername();
        if (username.equals("mmikhajlov")) {
            bool = true;
        }
        assertTrue(bool);
    }

    @Test
    public void getUserFalseTest() {
        String username;
        boolean bool = false;
        User user = new User("mmikhajlov", "123");
        username = user.getUsername();
        if (username.equals("123")) {
            bool = true;
        }
        assertFalse(bool);
    }
    
}
