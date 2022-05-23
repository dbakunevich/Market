package nsu.fit.upprpo.parser;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;

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

    public static String getUrlContent(URL url) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine, res = "";

        while ((inputLine = in.readLine()) != null)
            res += inputLine + "\n";
        in.close();

        return res;
    }
}
