package parsing;


import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.script.ScriptException;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Parser {

    static Logger log = LoggerFactory.getLogger(Parser.class);
    // Парсинг информации о продукте из файла html
    public abstract Product parseProduct(File html) throws FileNotFoundException;

    // Парсинг информации о продукте из строки
    public abstract Product parseProduct(String str);

    // Парсинг информации о продукте по его URL
    public abstract Product parseProduct(URL link) throws IOException, URISyntaxException, ScriptException;

    // Поиск товаров по заданной строке и формирование списка с товарами
    public abstract List<Product> search(String str);

    public static String getFileContent(String path) throws FileNotFoundException {
        return getFileContent(new File(path));
    }

    public static String getFileContent(File file) throws FileNotFoundException {
        char[] a = new char[(int)file.length() / 2 + 1];
        try(FileReader reader = new FileReader(file)) {
            reader.read(a);
        } catch (IOException e) {
            log.error("IOException: ", e);
            return null;
        }
        return new String(a);
    }

    public static String getUrlContent(String link) throws IOException {
        return getUrlContent(new URL(link));
    }

    public static String getUrlContent(URL url) {
        String content = "";
        try {
            log.info("Trying to get browser");
            FirefoxDriver driver = BrowserPool.getInstance().getBrowser();
            log.info("Got browser: " + driver.toString());
            driver.get(url.toString());
            content = driver.getPageSource();
            BrowserPool.getInstance().returnBrowser(driver);
        } catch (InterruptedException e) {
            log.error("InterruptedException: ", e);
            Thread.currentThread().interrupt();
        }
        return content;
    }

    public static ArrayList<String> getHtmlValues(Document html, String path) throws Exception {
        Elements elements;
        ArrayList<String> res = new ArrayList<>();
        String func = "";
        try {
            func = path.substring(path.lastIndexOf('.'));
            path = path.substring(0, path.lastIndexOf('.'));
        } catch (IndexOutOfBoundsException e) {}
        if (func.startsWith(".text()")) {
            elements = html.select(path);
            for (Element e: elements)
                res.add(e.text());
        }
        else if (func.startsWith(".attr(")) {
            elements = html.select(path);
            func = func.substring(func.indexOf('(') + 1, func.lastIndexOf(')'));
            for (Element e: elements)
                res.add(e.attr(func));
        }
        else {
            elements = html.select(path + func);
            for (Element e: elements)
                res.add(e.toString());
        }
        if (elements.isEmpty()) throw new Exception();
        return res;
    }
}
