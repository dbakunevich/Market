package spring;

import org.springframework.boot.context.properties.*;
import org.springframework.context.annotation.*;

@ConfigurationProperties(prefix="db") 
@Configuration 
public class MyProperties { 
    private String host = "jdbc:mysql://localhost:3306/upprpo";
    private String login = "root"; 
    private String password = "root"; 

    public String getHost(){
        return host;
    }

    public String getLogin(){
        return login;
    }

    public String getPassword(){
        return password;
    }
} 