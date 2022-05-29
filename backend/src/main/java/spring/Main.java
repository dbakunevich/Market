package spring;

import database.DBWorker;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class Main {
    
    public static DBWorker dbWorker;
    public static FirefoxDriver firefoxDriver;

    public static void main(String[] args) {
        String driverPath = "/usr/local/bin/geckodriver";
        System.setProperty("webdriver.gecko.driver", driverPath);
        FirefoxBinary firefoxBinary = new FirefoxBinary();
        FirefoxOptions options = new FirefoxOptions();
        options.setBinary(firefoxBinary);
        options.setHeadless(true);
        firefoxDriver = new FirefoxDriver(options);
        SpringApplication.run(Main.class, args);
    }
}
