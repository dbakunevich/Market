package parsing;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spring.Controller;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.util.List;


public class CitilinkParserTest {

    @Before
    public void init(){
        String driverPath = "D:\\apps\\geckodriver.exe";
        System.setProperty("webdriver.gecko.driver", driverPath);
    }

    @Test(expected = MalformedURLException.class)
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
