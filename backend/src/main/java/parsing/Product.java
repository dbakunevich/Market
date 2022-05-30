package parsing;

import java.net.URL;
import java.util.ArrayList;

public class Product {
    String name;
    int price;
    URL link;
    String specifications;
    ArrayList<String> images;

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public Product setSpecifications(String specifications) {
        this.specifications = specifications;
        return this;
    }

    public Product setLink(URL link) {
        this.link = link;
        return this;
    }

    public Product setPrice(int price) {
        this.price = price;
        return this;
    }

    public void printInfo() {
        System.out.println("Product: " + name +
                "\nPrice: " + price +
                "\nURL: " + link);
        if (specifications != null)
            System.out.println("Spec: " + specifications);
    }

    public Product addImageUrl(String url) {
        if (images == null) images = new ArrayList<>();
        images.add(url);
        return this;
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

    public String getSpecifications() {
        return specifications;
    }

    public String getImages() {
        return images.get(0);
    }
}
