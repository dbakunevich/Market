package nsu.fit.upprpo.parser;

import java.net.URL;

public class Product {
    String name;
    int price;
    URL link;
    String description;

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLink(URL link) {
        this.link = link;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
