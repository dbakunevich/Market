import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.ResponseEntity;
import parsing.Product;
import spring.SearchService;

import java.lang.reflect.Method;
import java.util.List;

public class SearchServiceTest {

    SearchService searchService = new SearchService();

    @Before
    public void init(){
    }

    @Test
    public void searchTest(){
        ResponseEntity<> products = searchService.getProductsResponse("iphone", null, null, null, null, null, null, null);

    }

}
