package nsu.fit.upprpo.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public interface Parser {
    public Product parseProduct(File html) throws FileNotFoundException;
    public Product parseProduct(String str);
    public Product parseProduct(URL link) throws IOException, URISyntaxException;
}
