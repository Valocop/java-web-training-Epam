package by.training.module3.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class XMLValidator {
    private static final Logger LOG = LogManager.getLogger();

    public ResultValidator validateXML(String xmlPath, String xsdPath) {
        ResultValidator resultValidator = new ResultValidator();
        String schemaLang = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory factory = SchemaFactory.newInstance(schemaLang);
        try {
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
            LOG.info("XML file [" + xmlPath + "] valid by XSD file [" + xsdPath +"].");
        } catch (SAXException e) {
            resultValidator.addException("SAXException", Arrays.asList(e.getMessage()));
            LOG.error(e);
        } catch (IOException e) {
            resultValidator.addException("IOException", Arrays.asList(e.getMessage()));
            LOG.error(e);
        }
        return resultValidator;
    }
}
