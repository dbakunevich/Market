package parsing;


import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;


public class CitilinkParserTest {

    @BeforeAll
    public static void init(){
        String driverPath = "D:\\apps\\geckodriver.exe";
        System.setProperty("webdriver.gecko.driver", driverPath);
    }

    @Test
    public void testBrokenLink(){
        assertThrows(Exception.class, () -> Citilink.getUrlContent("broken"));
    }

    @Test
    public void testParser(){
        List<Product> productList = new Citilink().search("iphone");
        assertNotNull(productList);
        assertNotEquals(productList.size(), 0);
    }
}
