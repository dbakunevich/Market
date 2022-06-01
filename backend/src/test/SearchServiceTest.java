import com.alibaba.fastjson2.JSON;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import parsing.BrowserPool;
import parsing.Product;

import spring.Controller;
import spring.ProductsAnswer;
import spring.SearchService;

import java.lang.reflect.Method;


public class SearchServiceTest {

    SearchService searchService = new SearchService();

    @Before
    public void init(){
        String driverPath = "D:\\apps\\geckodriver.exe";
        System.setProperty("webdriver.gecko.driver", driverPath);
    }

    @Test
    public void searchTest(){
        ResponseEntity<String> answer = searchService.getProductsResponse("iphone", null, null, null, null, null, 0, 10);
        ProductsAnswer products = JSON.parseObject(answer.getBody(), ProductsAnswer.class);
        assertEquals(answer.getStatusCodeValue(), HttpStatus.OK.value());
        assertNotNull(products);
        assertTrue(products.getAmount() > 0);
        assertNotNull(products.getProducts());
        assertTrue(products.getProducts().size() > 0);
    }

    @Test
    public void testLowPriceError(){
        ResponseEntity<String> answer = searchService.getProductsResponse("iphone", null, -1, null, null, null, 0, 10);
        assertEquals(answer.getStatusCodeValue(), HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testHighPriceError(){
        ResponseEntity<String> answer = searchService.getProductsResponse("iphone", null, null, -1, null, null, 0, 10);
        assertEquals(answer.getStatusCodeValue(), HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testLowPriceGreaterError(){
        ResponseEntity<String> answer = searchService.getProductsResponse("iphone", null, 50000, 50, null, null, 0, 10);
        assertEquals(answer.getStatusCodeValue(), HttpStatus.BAD_REQUEST.value());
    }

    @After
    public void close(){
        BrowserPool.getInstance().closeAll();
    }

}
