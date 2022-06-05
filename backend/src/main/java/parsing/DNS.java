package parsing;

import nsu.fit.upprpo.parser.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DNS extends Parser {
    private static final String SEARCH_URL = "https://www.dns-shop.ru/search/?q=";
    private static final String DNS_BASE =
            "div#search-results " +
                    "> div.products-list " +
                    "> div.products-list__content" +
                    "> div.catalog-products " +
                    "> div.catalog-product ";
    private static final String DNS_NAME =
            "> a.catalog-product__name.text()";
    private static final String DNS_PRICE =
            "> div.product-buy" +
                    "> div.product-buy__price-wrap" +
                    "> div.product-buy__price.ownText()";
    private static final String DNS_LINK =
            "> a.catalog-product__name.attr(href)";
    private static final String DNS_IMAGE =
            "> div.catalog-product__image " +
                    "> a.catalog-product__image-link" +
                    "> picture" +
                    "> img.attr(data-src)";;
    private static final String DNS_URL = "https://www.dns-shop.ru";
    private static final String DNS_BASE_2 =
            "div#app" +
                    "> div.brands-page" +
                    "> div.brands-page__main" +
                    "> div.brands-page__container" +
                    "> div.brands-page__best-offers" +
                    "> div.base-slider-products" +
                    "> div.base-slider-products__wrapper" +
                    "> div.slider-container" +
                    "> div.slider" +
                    "> div.slider__slides" +
                    "> div.slider__wrapper" +
                    "> div.slider__slide-item" +
                    "> div" +
                    "> div.product-slide-card";
    private static final String DNS_LINK_2 =
            "> div.product-slide-card__title" +
                    "> a.product-slide-card__title_link.attr(href)";
    private static final String DNS_NAME_2 =
            "> div.product-slide-card__title" +
                    "> a.product-slide-card__title_link.text()";
    private static final String DNS_PRICE_2 =
            "> div.product-slide-card__cta" +
                    "> div.product-slide-card__price" +
                    "> div.product-slide-card__price_block" +
                    "> div.product-slide-card__price_amount.ownText()";
    private static final String DNS_IMAGE_2 =
            "> div.product-slide-card__image" +
                    "> a" +
                    "> div.product-slide-card__image_wrapper" +
                    "> img.attr(src)";

    Logger log = LoggerFactory.getLogger(Citilink.class);

    @Override
    public ArrayList<Product> search(String str) {
        ArrayList<Product> products = new ArrayList<>();
        try {
            String res = SEARCH_URL + URLEncoder.encode(str, StandardCharsets.UTF_8);
            res = getUrlContent(res);
            Document document = Jsoup.parse(res);

            try {
                addProducts(products, document, DNS_BASE , DNS_NAME, DNS_PRICE, DNS_LINK, DNS_IMAGE, "");
            } catch (Exception e) {
                try {
                    addProducts(products, document, DNS_BASE_2 , DNS_NAME_2, DNS_PRICE_2, DNS_LINK_2, DNS_IMAGE_2, "");
                } catch (Exception e2) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }
}