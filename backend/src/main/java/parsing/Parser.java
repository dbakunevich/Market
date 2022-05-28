package parsing;

import org.openqa.selenium.firefox.FirefoxDriver;
import javax.script.ScriptException;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

abstract public class Parser {
    // Парсинг информации о продукте из файла html
    abstract public Product parseProduct(File html) throws FileNotFoundException;

    // Парсинг информации о продукте из строки
    abstract public Product parseProduct(String str);

    // Парсинг информации о продукте по его URL
    abstract public Product parseProduct(URL link) throws IOException, URISyntaxException, ScriptException;

    // Поиск товаров по заданной строке и формирование списка с товарами
    abstract public ArrayList<Product> search(String str);

    public static String getFileContent(String path) throws FileNotFoundException {
        return getFileContent(new File(path));
    }

    public static String getFileContent(File file) throws FileNotFoundException {
        char[] a = new char[(int)file.length() / 2 + 1];
        FileReader reader = new FileReader(file);
        try {
            reader.read(a);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return new String(a);
    }

    public static String getUrlContent(String link) throws IOException {
        return getUrlContent(new URL(link));
    }

    public static String getUrlContent(URL url) {
        FirefoxDriver firefoxDriver = new FirefoxDriver();
        firefoxDriver.get(url.toString());
        String result = firefoxDriver.getPageSource();
        firefoxDriver.close();
        return result;
    }
}
