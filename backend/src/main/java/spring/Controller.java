package spring;

import com.alibaba.fastjson2.JSON;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import parsing.Citilink;
import parsing.Product;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;

@RestController
public class Controller {

    static final Logger log = LoggerFactory.getLogger(Controller.class);

    @CrossOrigin(origins = {"http://localhost:3000",
                            "http://51.250.108.33:3000/"})
    @GetMapping("/")
    String home() {
        return "Home";
    }

    @CrossOrigin(origins = {"http://localhost:3000",
                            "http://51.250.108.33:3000/"})
    @GetMapping("/search")
    public String search(@RequestParam String toSearch,
                         @RequestParam(required = false) String login,
                         @RequestParam(required = false, name = "lp") Integer low_price,
                         @RequestParam(required = false, name = "hp") Integer high_price,
                         @RequestParam(required = false) Boolean price_order,
                         @RequestParam(required = false) Boolean name_order,
                         @RequestParam(required = false, defaultValue = "0") Integer page,
                         @RequestParam(required = false, defaultValue = "10") Integer page_size) {
        log.info("Started parsing " + toSearch);
        long timeStart = System.currentTimeMillis();
        List<Product> products = Main.requestsMap.get(toSearch);
        if (products == null) {
            products = new Citilink().search(toSearch);
            Main.requestsMap.put(toSearch, products);
        }
        long timeFinish = System.currentTimeMillis();
        log.info("Parsing of " + toSearch + " finished in " + (timeFinish - timeStart) + " ms");

        if(login != null){
            Main.dbWorker.addHistory(login, toSearch);
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
        } else {
            return "0";
        }
    }

    @CrossOrigin(origins = {"http://localhost:3000",
                            "http://51.250.108.33:3000/"})
    @GetMapping("/login")
    public String login(@RequestParam String login,
                        @RequestParam String password) {
        return JSON.toJSONString(Main.dbWorker.findUser(login, password));
    }

    @CrossOrigin(origins = {"http://localhost:3000",
                            "http://51.250.108.33:3000/"})
    @GetMapping("/registration")
    public String registration( @RequestParam String login,
                                @RequestParam String password) {
        if (Main.dbWorker.addUser(login, password))                                     
            return JSON.toJSONString(Main.dbWorker.findUser(login, password));
        return JSON.toJSONString("Данный пользователь уже зарегистрирован!");
    }

    @CrossOrigin(origins = {"http://localhost:3000",
                            "http://51.250.108.33:3000/"})
    @GetMapping("/getHistory")
    public String getHistory(@RequestParam String login) {
        ArrayList<String> result = Main.dbWorker.findHistory(login);
        if (result == null)
            return JSON.toJSONString("");
        return JSON.toJSONString(result);
    }
}
