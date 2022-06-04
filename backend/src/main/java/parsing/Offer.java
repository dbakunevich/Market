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

    public Offer copy() {
        return new Offer(url, price);
    }

    public String toJson() {
        return "{\"url\":\"" + url + ";\"price\":\"" + price + "\"}";
    }

    @Override
    public boolean equals(Object other) {
        Offer o = (Offer) other;
        if ((o.price == this.price) && (o.url == o.url)) return true;
        return false;
    }

    @Override
    public String toString() {
        return "URL: " + this.url + " | Price: " + price;
    }
}
