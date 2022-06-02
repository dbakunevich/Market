import com.alibaba.fastjson2.JSON;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import parsing.BrowserPool;
import parsing.Product;
import spring.ProductsAnswer;
import spring.SearchService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


@SpringBootTest
public class TestSearchService {

    SearchService searchService = new SearchService();
    List<Product> testProducts = new ArrayList<>();

    @BeforeAll
    public void init() {
        String driverPath = "D:\\apps\\geckodriver.exe";
        System.setProperty("webdriver.gecko.driver", driverPath);
        for (int i = 0; i < 100; i++) {
            Product product = new Product();
            product.setPrice((i + 50) % 75).setName(Integer.toString((i + 50) % 75));
            testProducts.add(product);
        }
        BrowserPool.getInstance();
    }

    @Test
    public void searchTest() {
        ResponseEntity<String> answer = searchService.getProductsResponse("iphone", null, null, null, null, null, 0, 10);
        ProductsAnswer products = JSON.parseObject(answer.getBody(), ProductsAnswer.class);
        assertEquals(answer.getStatusCodeValue(), HttpStatus.OK.value());
        assertNotNull(products);
        assertTrue(products.getAmount() > 0);
        assertNotNull(products.getProducts());
        assertTrue(products.getProducts().size() > 0);
    }

    @Test
    public void testLowPriceError() {
        ResponseEntity<String> answer = searchService.getProductsResponse("iphone", null, -1, null, null, null, 0, 10);
        assertEquals(answer.getStatusCodeValue(), HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testHighPriceError() {
        ResponseEntity<String> answer = searchService.getProductsResponse("iphone", null, null, -1, null, null, 0, 10);
        assertEquals(answer.getStatusCodeValue(), HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testLowPriceGreaterError() {
        ResponseEntity<String> answer = searchService.getProductsResponse("iphone", null, 50000, 50, null, null, 0, 10);
        assertEquals(answer.getStatusCodeValue(), HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testLowPriceFilter() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = SearchService.class.getDeclaredMethod("applyLowPriceFilter", List.class, Integer.class);
        method.setAccessible(true);
        Integer testint = 25;
        List<Product> result = (List<Product>) method.invoke(searchService, testProducts, testint);
        assertNotNull(result);
        AtomicBoolean test = new AtomicBoolean(true);
        result.forEach(product -> {
            if (product.getPrice() < 25)
                test.set(false);
        });
        assertTrue(test.get());
    }

    @Test
    public void testHighPriceFilter() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = SearchService.class.getDeclaredMethod("applyHighPriceFilter", List.class, Integer.class);
        method.setAccessible(true);
        Integer testint = 25;
        List<Product> result = (List<Product>) method.invoke(searchService, testProducts, testint);
        assertNotNull(result);
        AtomicBoolean test = new AtomicBoolean(true);
        result.forEach(product -> {
            if (product.getPrice() > 25)
                test.set(false);
        });
        assertTrue(test.get());
    }

    @Test
    public void testPriceSortingAsc() {
        ResponseEntity<String> answer = searchService.getProductsResponse("iphone", null, null, null, true, null, 0, 10);
        List<Product> products = JSON.parseObject(answer.getBody(), ProductsAnswer.class).getProducts();
        Boolean test = true;
        assertNotNull(products);
        for (int i = 0; i < products.size() - 1; i++) {
            if (products.get(i).getPrice() < products.get(i + 1).getPrice()) {
                test = false;
                break;
            }
        }
        assertTrue(test);
    }

    @Test
    public void testPriceSortingDesc() {
        ResponseEntity<String> answer = searchService.getProductsResponse("iphone", null, null, null, false, null, 0, 10);
        List<Product> products = JSON.parseObject(answer.getBody(), ProductsAnswer.class).getProducts();
        Boolean test = true;
        assertNotNull(products);
        for (int i = 0; i < products.size() - 1; i++) {
            if (products.get(i).getPrice() > products.get(i + 1).getPrice()) {
                test = false;
                break;
            }
        }
        assertTrue(test);
    }

    @Test
    public void testNameSortingAsc() {
        ResponseEntity<String> answer = searchService.getProductsResponse("iphone", null, null, null, null, false, 0, 10);
        List<Product> products = JSON.parseObject(answer.getBody(), ProductsAnswer.class).getProducts();
        Boolean test = true;
        assertNotNull(products);
        for (int i = 0; i < products.size() - 1; i++) {
            if (products.get(i).getName().compareTo(products.get(i + 1).getName()) > 0) {
                test = false;
                break;
            }
        }
        assertTrue(test);
    }

    @Test
    public void testNameSortingDesc() {
        ResponseEntity<String> answer = searchService.getProductsResponse("iphone", null, null, null, null, true, 0, 10);
        assertEquals(answer.getStatusCodeValue(), HttpStatus.OK.value());
        List<Product> products = JSON.parseObject(answer.getBody(), ProductsAnswer.class).getProducts();
        Boolean test = true;
        assertNotNull(products);
        for (int i = 0; i < products.size() - 1; i++) {
            if (products.get(i).getName().compareTo(products.get(i + 1).getName()) < 0) {
                test = false;
                break;
            }
        }
        assertTrue(test);
    }

    @Test
    public void testLowPageNumber(){
        ResponseEntity<String> answer = searchService.getProductsResponse("iphone", null, null, null, null, null, -1, 10);
        assertEquals(answer.getStatusCodeValue(), HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testLowPageSize(){
        ResponseEntity<String> answer = searchService.getProductsResponse("iphone", null, null, null, null, null, 0, -1);
        assertEquals(answer.getStatusCodeValue(), HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testPageDoesentExists(){
        ResponseEntity<String> answer = searchService.getProductsResponse("iphone", null, null, null, null, null, 0, 10);
        assertEquals(answer.getStatusCodeValue(), HttpStatus.OK.value());
        ProductsAnswer productsAnswer = JSON.parseObject(answer.getBody(), ProductsAnswer.class);
        int amount = productsAnswer.getAmount();
        int pageSize = amount;
        int page = 1;
        answer = searchService.getProductsResponse("iphone", null, null, null, null, null, page, pageSize);
        assertNotNull(answer);
        assertEquals(answer.getStatusCodeValue(), HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testPaging(){
        int page_size = 15;
        ResponseEntity<String> answer = searchService.getProductsResponse("iphone", null, null, null, null, null, 0, page_size);
        assertEquals(answer.getStatusCodeValue(), HttpStatus.OK.value());
        ProductsAnswer productsAnswer = JSON.parseObject(answer.getBody(), ProductsAnswer.class);
        assertEquals(productsAnswer.getProducts().size(), page_size);
    }

    /*@After
    public void close() {
        BrowserPool.getInstance().closeAll();
    }*/

}
