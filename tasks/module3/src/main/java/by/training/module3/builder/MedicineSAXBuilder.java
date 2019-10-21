package by.training.module3.builder;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;

public class MedicineSAXBuilder extends Builder {

    @Override
    public void buildListMedicines(String fileNme) throws BuilderException {
        if (fileNme == null) {
            throw new IllegalArgumentException();
        }
        MedicineSAXHandler handler = new MedicineSAXHandler();
        XMLReader reader = null;
        try {
            reader = XMLReaderFactory.createXMLReader();
        } catch (SAXException e) {
            throw new BuilderException(e);
        }
        reader.setContentHandler(handler);
        try {
            reader.parse(fileNme);
        } catch (IOException | SAXException e) {
            throw new BuilderException(e);
        }
        medicines.addAll(handler.getMedicines());
    }
}
