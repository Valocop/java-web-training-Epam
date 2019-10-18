package by.training.module3.builder;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public interface Builder<T> {
    List<T> getEntities();
    void buildEntities(String path) throws BuilderException;
}
