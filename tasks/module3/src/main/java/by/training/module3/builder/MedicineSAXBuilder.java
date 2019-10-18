package by.training.module3.builder;

import by.training.module3.entity.Medicine;
import by.training.module3.handler.Handler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MedicineSAXBuilder implements Builder<Medicine> {
    private List<Medicine> medicines = new ArrayList<>();
    private Handler<Medicine> handler;

    public MedicineSAXBuilder(Handler<Medicine> handler) {
        this.handler = handler;
    }

    @Override
    public List<Medicine> getEntities() {
        return new ArrayList<>(medicines);
    }

    @Override
    public void buildEntities(String path) throws BuilderException {
        if (path == null || handler == null) {
            throw new IllegalArgumentException();
        }
        XMLReader reader = null;
        try {
            reader = XMLReaderFactory.createXMLReader();
        } catch (SAXException e) {
            throw new BuilderException(e);
        }
        reader.setContentHandler(handler);
        try {
            reader.parse(path);
        } catch (IOException e) {
            throw new BuilderException(e);
        } catch (SAXException e) {
            throw new BuilderException(e);
        }
        medicines = handler.getEntities();
    }
}
