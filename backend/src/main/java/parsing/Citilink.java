package parsing;

import nsu.fit.upprpo.parser.Product;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Citilink extends Parser {

    Logger log = LoggerFactory.getLogger(Citilink.class);

    private static final Pattern INFO                = Pattern.compile("\"@context\":([\\w\\W]*?)\\}");
    private static final Pattern PRICE               = Pattern.compile("\"price\": \"(.*?)\"");
    private static final Pattern IMAGE               = Pattern.compile("<img.*?class=\" PreviewList__image Image\".*?alt=\".*?\".*?src=\"(.*?)\"");
    private static final Pattern NAME                = Pattern.compile("\"mpn\": \"(.*?)\".*?\"brand\": \"(.*?)\"");
    private static final Pattern PROPERTIES_URL      = Pattern.compile("\"url\": \"(.*?)\"");
    private static final Pattern PROPERTIES          = Pattern.compile("(<div class=\" SpecificationsFull\">[\\w\\W]*?<div class=\"SpecificationsFull\">)");
    private static final Pattern PROPERTIES_SUBGROUP = Pattern.compile("<div class=\" SpecificationsFull\">(.*?)<div class=\" SpecificationsFull\">");
    private static final Pattern PROPERTIES_SUBNAME  = Pattern.compile("<h4.*?>([^<]*?)</h4>");
    private static final Pattern PROPERTIES_SUBPROP  = Pattern.compile("<div class=\"Specifications[^>]*?>([^<]*?)</div>");
    private static final Pattern PRODUCT_CARD        = Pattern.compile("<div class=\"ProductCardVerticalLayout ProductCardVertical__layout\">([\\w\\W]*?)Добавить в корзину");
    private static final Pattern PRODUCT_IMAGE       = Pattern.compile("<div class=\"ProductCardVertical__picture-container \">\\w*?<img.*?src=\"(.*?)\"");
    private static final Pattern PRODUCT_LINK        = Pattern.compile("<div class=\"ProductCardVertical__description \">\\w*?<a.*?href=\"(.*?)\".*?>(.*?)</a>.*?<span class=\"ProductCardVerticalPrice__price-current_current-price js--ProductCardVerticalPrice__price-current_current-price \">(.*?)</span>");

    private static final String SEARCH_URL           = "https://www.citilink.ru/search/?text=";
    private static final String BASE_URL             = "https://www.citilink.ru";

    public static final String CITILINK_BASE = "body" +
            "> div.MainWrapper" +
            "> div.MainLayout" +
            "> main.MainLayout__main" +
            "> div.SearchResults" +
            "> div.Container" +
            "> div.block_data__gtm-js" +
            "> div.ProductCardCategoryList__grid-container" +
            "> div.ProductCardCategoryList__grid" +
            "> section.GroupGrid " +
            "> div.product_data__gtm-js";
    public static final String CITILINK_LINK = CITILINK_BASE + "> a";
    public static final String CITILINK_IMAGE = CITILINK_BASE +
            "> div.ProductCardVerticalLayout" +
            "> div.ProductCardVerticalLayout__header" +
            "> div.ProductCardVerticalLayout__wrapper-cover-image" +
            "> a.ProductCardVertical__image-link" +
            "> div.ProductCardVertical__image-wrapper" +
            "> div.ProductCardVertical__picture-container" +
            "> img.ProductCardVertical__picture";
    public static final String CITILINK_PRICE = CITILINK_BASE +
            "> div.ProductCardVerticalLayout" +
            "> div.ProductCardVerticalLayout__footer" +
            "> div.ProductCardVerticalLayout__wrapper-price" +
            "> div.ProductCardVertical_mobile" +
            "> div.ProductCardVertical__price-with-amount" +
            "> div.ProductPrice" +
            "> span.ProductPrice__price" +
            "> span.ProductCardVerticalPrice__price-current_current-price";

    @Override
    public Product parseProduct(File html) throws FileNotFoundException {
        return parseProduct(getFileContent(html));
    }

    @Override
    public Product parseProduct(String str) {
        str = str.replaceAll("\n","");
        String info, temp;
        Matcher matcher;
        Product product = new Product();

        matcher = INFO.matcher(str);
        if (matcher.find()) {
            info = matcher.group(1).replaceAll("\n", "");

            matcher = NAME.matcher(info);
            if (matcher.find()) {
                product.setName(matcher.group(2) + " " + matcher.group(1));
            }

            matcher = PRICE.matcher(info);
            if (matcher.find()) {
                product.setPrice(Integer.parseInt(matcher.group(1)));
            }

            matcher = PROPERTIES_URL.matcher(info);
            if (matcher.find()) {
                try {
                    product.setLink(new URL(matcher.group(1)));
                    info = getUrlContent(matcher.group(1) + "properties/");
                    temp = info.replaceAll("\n","");
                    matcher = PROPERTIES.matcher(info);

                    if (matcher.find()) {
                        info = matcher.group(1).replaceAll("\n", "");
                        int end = -1;
                        StringBuilder spec = new StringBuilder("{");

                        matcher = PROPERTIES_SUBGROUP.matcher(info);
                        while (matcher.find()) {
                            spec.append(__tech(matcher.group(1)));

                            if (matcher.end() > 0) end = matcher.end();
                        }

                        if (end > 0) info = info.substring(end);
                        matcher = PROPERTIES.matcher(info);
                        if (matcher.find()) {
                            spec.append(__tech(matcher.group(1)));
                        }

                        spec = new StringBuilder(spec.toString().replaceAll(",}", "}"));
                        product.setSpecifications(spec.substring(0, spec.length() - 1));

                        matcher = IMAGE.matcher(temp);
                        while (matcher.find())
                            product.addImageUrl(matcher.group(1));
                    }
                } catch (IOException e) {
                    log.error("IOException: ", e);
                }
            }
        }

        return product;
    }


    @Override
    public Product parseProduct(URL link) throws IOException {
        return parseProduct(getUrlContent(link));
    }

    @Override
    public ArrayList<Product> search(String str) {
        ArrayList<Product> products = new ArrayList<>();
        try {
            String res = SEARCH_URL + URLEncoder.encode(str, StandardCharsets.UTF_8);
            res = getUrlContent(res);
            Document document = Jsoup.parse(res);
            Elements links = document.select(CITILINK_LINK);
            Elements images = document.select(CITILINK_IMAGE);
            Elements prices = document.select(CITILINK_PRICE);
            for (int i = 0; i < links.size(); i++) {
                products.add(new Product());
                products.get(i).setLink(new URL(BASE_URL + links.get(i).attr("href")));
                products.get(i).setName(images.get(i).attr("alt"));
                products.get(i).addImageUrl(images.get(i).attr("src"));
                products.get(i).setPrice(Integer.parseInt(prices.get(i).text().replaceAll(" ", "")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    private String __tech(String a) {
        StringBuilder res = new StringBuilder();
        boolean flag = false;
        Matcher subMatcher = PROPERTIES_SUBNAME.matcher(a);
        if (subMatcher.find()) 
            res.append("\"").append(subMatcher.group(1).strip()).append("\":{");

        subMatcher = PROPERTIES_SUBPROP.matcher(a);
        while (subMatcher.find()) {
            if (flag)
                res.append("\"").append(subMatcher.group(1).strip()).append("\",");
            else
                res.append("\"").append(subMatcher.group(1).strip()).append("\":");
            flag = !flag;
        }
        res.append("},");

        return res.toString();
    }
}
