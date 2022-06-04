import java.net.MalformedURLException;
import java.net.URL;

public class Offer {
    private URL url;
    private Integer price;

    public Offer(URL link, int price) {
        this.url = link;
        this.price = price;
    }

    public Offer(URL link, String price) {
        this.url = link;
        this.price = Integer.parseInt(price.replaceAll("\\s", ""));
    }

    public Offer(String link, int price) throws MalformedURLException {
        this.url = new URL(link);
        this.price = price;
    }

    public Offer(String link, String price) throws MalformedURLException {
        this.url = new URL(link);
        this.price = Integer.parseInt(price.replaceAll("\\s", ""));
    }

    public URL getUrl() {
        return this.url;
    }

    public Integer getPrice() {
        return this.price;
    }

    @Override
    public String toString() {
        return "URL: " + this.url + " | Price: " + price;
    }
}