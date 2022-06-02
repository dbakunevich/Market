package parsing;


import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.util.List;


public class CitilinkParserTest {

    @BeforeAll
    public void init(){
        String driverPath = "D:\\apps\\geckodriver.exe";
        System.setProperty("webdriver.gecko.driver", driverPath);
    }

    @Test
    public void testBrokenLink() throws Exception{
        Citilink.getUrlContent("broken");
    }

    @Test
    public void testParser(){
        List<Product> productList = new Citilink().search("iphone");
        assertNotNull(productList);
        assertNotEquals(productList.size(), 0);
    }
}
