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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class SearchServiceTest {

    SearchService searchService = new SearchService();
    List<Product> testProducts = new ArrayList<>();

    @Before
    public void init(){
        String driverPath = "D:\\apps\\geckodriver.exe";
        System.setProperty("webdriver.gecko.driver", driverPath);
        for(int i = 0; i < 100; i++){
            Product product = new Product();
            product.setPrice((i+50)%75).setName(Integer.toString((i+50)%75));
            testProducts.add(product);
        }
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

    @Test
    public void testLowPriceFilter() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = SearchService.class.getDeclaredMethod("applyLowPriceFilter", List.class, Integer.class);
        method.setAccessible(true);
        Integer testint = 25;
        List<Product> result = (List<Product>)method.invoke(searchService, testProducts, testint);
        assertNotNull(result);
        AtomicBoolean test = new AtomicBoolean(true);
        result.forEach(product -> {
            if(product.getPrice() < 25)
                test.set(false);
        });
        assertTrue(test.get());
    }

    @Test
    public void testHighPriceFilter() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = SearchService.class.getDeclaredMethod("applyHighPriceFilter", List.class, Integer.class);
        method.setAccessible(true);
        Integer testint = 25;
        List<Product> result = (List<Product>)method.invoke(searchService, testProducts, testint);
        assertNotNull(result);
        AtomicBoolean test = new AtomicBoolean(true);
        result.forEach(product -> {
            if(product.getPrice() > 25)
                test.set(false);
        });
        assertTrue(test.get());
    }

    @After
    public void close(){
        BrowserPool.getInstance().closeAll();
    }

}
