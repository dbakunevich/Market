package spring;

import com.alibaba.fastjson2.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import parsing.Citilink;
import parsing.Product;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchService {
    static Logger log = LoggerFactory.getLogger(SearchService.class);
    Map<String, List<Product>> searchHistory = new HashMap<>();

    List<Product> parseProducts(String toSearch){
        log.info("Started parsing " + toSearch);
        long timeStart = System.currentTimeMillis();
        List<Product> products = Main.requestsMap.get(toSearch);
        if (products == null) {
            products = new Citilink().search(toSearch);
            Main.requestsMap.put(toSearch, products);
        }
        long timeFinish = System.currentTimeMillis();
        log.info("Parsing of " + toSearch + " finished in " + (timeFinish - timeStart) + " ms");
        return products;
    }

    void checkFilters(Integer low_price, Integer high_price) throws SearchException {
        if(low_price != null && low_price < 0)
            throw new SearchException("Lowest price value cannot be lower than zero. Provided " + low_price);
        if(high_price != null && high_price < 0)
            throw new SearchException("Highest price value cannot be lower than zero. Provided " + high_price);
        if(high_price != null && low_price != null && low_price > high_price)
            throw new SearchException("Lowest price cannot be greater than highest price value. Provided " + low_price + " > " + high_price);
    }

    List<Product> applyLowPriceFilter(List<Product> products, Integer low_price){
        if(low_price != null)
            return products.stream().filter(product -> product.getPrice() >= low_price).collect(Collectors.toList());
        else
            return products;
    }

    List<Product> applyHighPriceFilter(List<Product> products, Integer high_price){
        if(high_price != null)
            return products.stream().filter(product -> product.getPrice() <= high_price).collect(Collectors.toList());
        else
            return products;
    }

    void filterByPrice(List<Product> products, Boolean price_order){
        if(price_order != null){
            if (!price_order) {
                products.sort(Comparator.comparingInt(Product::getPrice));
            }
            if (price_order) {
                products.sort((o1, o2) -> o2.getPrice() - o1.getPrice());
            }
        }
    }

    void filterByName(List<Product> products, Boolean name_order){
        if(name_order != null) {
            if (!name_order) {
                products.sort(Comparator.comparing(Product::getName));
            }
            if (name_order) {
                products.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
            }
        }
    }

    List<Product> getPage(List<Product> products, Integer page, Integer page_size) throws SearchException {
        if(page < 0)
            throw new SearchException("Page cannot be lower than 0. Provided " + page);
        if(page_size < 1)
            throw new SearchException("Page size cannot be lower than 1. Provided " + page_size);
        if(page * page_size >= products.size()){
            throw new SearchException("Page doesn't exists. It strats with " + (page * page_size - 1) + " element when only " + products.size() + "exists");
        }
        return products.subList(page * page_size, Integer.min((page + 1) * page_size, products.size()));
    }

    public ResponseEntity<String> getProductsResponse(String toSearch, String login, Integer low_price, Integer high_price,
                                                      Boolean price_order, Boolean name_order, Integer page, Integer page_size){

        try{
            checkFilters(low_price, high_price);
        } catch (SearchException e){
            log.warn(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        List<Product> products = parseProducts(toSearch);
        int amount = products.size();

        if(login != null){
           Main.dbWorker.addHistory(login, toSearch);
        }

        products = applyLowPriceFilter(products, low_price);
        products = applyHighPriceFilter(products, high_price);
        filterByPrice(products, price_order);
        filterByName(products, name_order);

        try{
            products = getPage(products, page, page_size);
        } catch (SearchException e) {
            log.warn(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(JSON.toJSONString(new ProductsAnswer(amount, products)), HttpStatus.OK);
    }
}
