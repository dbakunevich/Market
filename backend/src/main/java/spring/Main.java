package spring;

import database.DBWorker;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@SpringBootApplication
@RestController
@EnableAutoConfiguration
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
//        DBWorker worker = new DBWorker();
//        ArrayList<String> arrayList = null;
//        arrayList = worker.findHistory("mmikhajlov");
//        for(String s : arrayList) {
//            System.out.println(s);
//        }
    }
}