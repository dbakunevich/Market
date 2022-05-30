package parsing;


import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.script.ScriptException;
import java.io.*;
import java.net.*;
import java.util.List;

abstract public class Parser {

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
}
