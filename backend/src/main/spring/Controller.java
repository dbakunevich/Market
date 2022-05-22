package spring;

import com.alibaba.fastjson2.JSON;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import parsing.src.Product;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class Controller {

    @GetMapping("/")
    String home() {
        return "Home";
    }

    @GetMapping("/search")
    public String search(@RequestParam String toSearch,
                         @RequestParam(required = false, name = "lp") Integer low_price,
                         @RequestParam(required = false, name = "hp") Integer high_price,
                         @RequestParam(required = false) Boolean price_order,
                         @RequestParam(required = false) Boolean name_order,
                         @RequestParam(required = false, defaultValue = "0") Integer page,
                         @RequestParam(required = false, defaultValue = "10") Integer page_size) {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            products.add(new Product("name " + i + " " + toSearch, i, null, "descr" + i));
        }

        int count = products.size();

        if (count != 0) {
            if (low_price != null) {
                if (high_price != null)
                    products = products.stream()
                            .filter((product -> product.getPrice() > low_price && product.getPrice() < high_price))
                            .collect(Collectors.toList());
                else
                    products = products.stream()
                            .filter((product -> product.getPrice() > low_price))
                            .collect(Collectors.toList());
            } else if (high_price != null)
                products = products.stream()
                        .filter((product -> product.getPrice() < high_price))
                        .collect(Collectors.toList());

            if (price_order != null) {
                if (!price_order) {
                    products.sort(Comparator.comparingInt(Product::getPrice));
                }
                if (price_order) {
                    products.sort((o1, o2) -> o2.getPrice() - o1.getPrice());
                }
            } else if (name_order != null) {
                if (!name_order) {
                    products.sort(Comparator.comparing(Product::getName));
                }
                if (name_order) {
                    products.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
                }
            }

            try {
                products = products.subList(page * page_size, Integer.min((page + 1) * page_size, products.size()));
            } catch (Exception e) {
                return "1";
            }

            return JSON.toJSONString(new ProductsAnswer(count, products));
        } else return "0";
    }
}
