package parsing.src;

import java.net.URL;

public class Product {
    String name;
    int price;
    URL link;
    String description;

    public Product(){}

    public Product(String name, int price, URL link, String description) {
        this.name = name;
        this.price = price;
        this.link = link;
        this.description = description;
    }

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

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public URL getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", link=" + link +
                ", description='" + description + '\'' +
                '}';
    }
}
