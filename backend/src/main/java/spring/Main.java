package spring;

import database.DBWorker;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@SpringBootApplication
@RestController
public class Main {
    
    public static DBWorker dbWorker = new DBWorker();

    public static void main(String[] args) {
        String driverPath = "/usr/local/bin/geckodriver";
        System.setProperty("webdriver.gecko.driver", driverPath);
        SpringApplication.run(Main.class, args);
    }
}
