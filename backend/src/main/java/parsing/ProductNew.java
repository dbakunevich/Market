import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ProductNew {
    private String type;
    private String brand;
    private String model;
    private Specifications specification = null;
    private ArrayList<Offer> offers = null;
    private ArrayList<URL> images = null;
    private Float rating = null;

    private Integer currentOffersIndex = 0;
    private Integer currentImagesIndex = 0;

    private ProductNew(String type, String brand, String model) {
        this.type = type;
        this.brand = brand;
        this.model = model;
    }

    public Offer getOfferCurrent() {
        if (offers != null) {
            try {
                return offers.get(currentOffersIndex);
            }
            catch (IndexOutOfBoundsException e) {
                currentOffersIndex = 0;
                return null;
            }
        }
        throw new NullPointerException("Offer list is null");
    }

    public Offer getOfferNext() {
        Offer offer = getOfferCurrent();
        if (offer != null) currentOffersIndex++;
        return offer;
    }

    public String getOffersJson() {
        return "{\"offers\":[" + getOffersJsonArray() + "]}";
    }

    public void resetOfferNextIndex() {
        currentOffersIndex = 0;
    }

    public URL getImageCurrent() {
        if (images != null) {
            try {
                return images.get(currentImagesIndex);
            }
            catch (IndexOutOfBoundsException e) {
                currentImagesIndex = 0;
                return null;
            }
        }
        throw new NullPointerException("Image list is null");
    }

    public URL getImageNext() {
        URL image = getImageCurrent();
        if (image != null) currentImagesIndex++;
        return image;
    }

    public void resetImageNextIndex() {
        currentImagesIndex = 0;
    }

    public String getType() {
        return type;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public Specifications getSpecification() {
        return specification.copy();
    }

    public String getSpecificationJson() {
        return specification.toJson();
    }

    public ArrayList<Offer> getOffers() {
        ArrayList<Offer> offerList = new ArrayList<>();
        for (Offer offer: offers)
            offerList.add(offer.copy());
        return offerList;
    }

    public ArrayList<URL> getImages() throws MalformedURLException {
        ArrayList<URL> imageList = new ArrayList<>();
        for (URL image: images)
            imageList.add(new URL(image.toString()));
        return imageList;
    }

    public float getRating() {
        return rating;
    }

    private void addSpecificationCategory(String categoryName) {
        specification.addCategory(categoryName);
    }

    private void addCharacteristic(String categoryName, String characteristicName, String characteristicValue) {
        specification.addCharacteristic(categoryName, characteristicName, characteristicValue);
    }

    private void addCharacteristic(String characteristicName, String characteristicValue) {
        specification.addCharacteristic(characteristicName, characteristicValue);
    }

    public void addOffer(URL link, Integer price) {
        if (offers == null) offers = new ArrayList<>();
        offers.add(new Offer(link, price));
    }

    public void addOffer(URL link, String price) {
        if (offers == null) offers = new ArrayList<>();
        offers.add(new Offer(link, price));
    }

    public void addOffer(String link, Integer price) throws MalformedURLException {
        if (offers == null) offers = new ArrayList<>();
        offers.add(new Offer(link, price));
    }

    public void addOffer(String link, String price) throws MalformedURLException {
        if (offers == null) offers = new ArrayList<>();
        offers.add(new Offer(link, price));
    }

    public void addImage(URL link) {
        if (images == null) images = new ArrayList<>();
        images.add(link);
    }

    public void addImage(String link) throws MalformedURLException {
        addImage(new URL(link));
    }

    public void clearImageList() {
       if (images != null) images.clear();
    }

    public void clearOfferList() {
        if (offers != null) offers.clear();
    }

    public void updateRating(Float rating) {
        if (this.rating == null)
            this.rating = rating;
        else
            this.rating = (this.rating + rating) / 2;
    }

    @Deprecated
    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String toJson() {
        boolean flag = true;
        String res = "{";

        if (type != null) {
            res += "\"type\":\"" + type + "\"";
            flag = false;
        }

        if (brand != null) {
            if (!flag)
                res += ";\"brand\":\"" + brand + "\"";
            else {
                res += "\"brand\":\"" + brand + "\"";
                flag = false;
            }
        }

        if (model != null) {
            if (!flag)
                res += ";\"model\":\"" + model + "\"";
            else {
                res += "\"model\":\"" + model + "\"";
                flag = false;
            }
        }

        if (specification != null) {
            if (!flag)
                res += ";\"specification\":" + specification.toJson();
            else {
                res += "\"specification\":" + specification.toJson();
                flag = false;
            }
        }

        if (offers != null) {
            if (!flag)
                res += ";\"offers\":" + getOffersJsonArray();
            else {
                res += "\"offers\":" + getOffersJsonArray();
                flag = false;
            }
        }
        if (rating != null) {
            if (!flag)
                res += ";\"rating\":\"" + String.format("%.1f", rating) + "\"";
            else {
                res += "\"rating\":\"" + String.format("%.1f", rating) + "\"";
            }
        }
        res += "}";

        return res;
    }

    @Override
    public String toString() {
        String res = "";
        if (type != null)
            res += "Type:\t" + type + "\n";
        if (brand != null)
            res += "Brand:\t" + brand + "\n";
        if (model != null)
            res += "Model:\t" + model + "\n";
        if (specification != null)
            res += "Specification:\n" + specification.toString() + "\n";
        if (offers != null) {
            res += "Offers:\n";
            for (Offer offer: offers)
                res += offer.toString() + "\n";
        }
        if (rating != null)
            res += "Rating:\t" + String.format("%.1f", rating);

        return res;
    }

    private String getOffersJsonArray() {
        String res = "[";
        boolean flag = true;
        for (Offer offer: offers) {
            if (!flag) res += ";" + offer.toJson();
            else {
                res += offer.toJson();
                flag = false;
            }
        }
        res += "]";
        return res;
    }


    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String type;
        private String brand;
        private String model;
        private Specifications specification;
        private ArrayList<Offer> offers;
        private ArrayList<URL> images;
        private Float rating;

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setBrand(String brand) {
            this.brand = brand;
            return this;
        }

        public Builder setModel(String model) {
            this.model = model;
            return this;
        }

        public ProductNew build() {
            return new ProductNew(this.type, this.brand, this.model);
        }
    }
}
