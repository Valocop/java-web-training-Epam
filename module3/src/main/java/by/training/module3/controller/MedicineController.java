package by.training.module3.controller;

import by.training.module3.builder.BuilderException;
import by.training.module3.command.Command;
import by.training.module3.command.CommandProvider;
import by.training.module3.command.CommandType;
import by.training.module3.entity.Medicine;
import by.training.module3.service.Service;
import by.training.module3.validator.FileValidator;
import by.training.module3.validator.ResultValidator;
import by.training.module3.validator.XMLValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class MedicineController {
    private static final Logger LOG = LogManager.getLogger();
    private Service<Medicine> service;

    public MedicineController(Service<Medicine> service) {
        this.service = service;
    }

    public void execute(CommandProvider<Medicine> provider, CommandType type, String xmlPath, String xsdPath) {
        FileValidator fileValidator = new FileValidator();
        ResultValidator resultFileXMLValidator = fileValidator.validateFile(xmlPath);
        ResultValidator resultFileXSDValidator = fileValidator.validateFile(xsdPath);
        if (resultFileXMLValidator.isValid() && resultFileXSDValidator.isValid()) {
            XMLValidator xmlValidator = new XMLValidator();
            ResultValidator resultXMLValidator = xmlValidator.validateXML(xmlPath, xsdPath);
            if (resultXMLValidator.isValid()) {
                Command<Medicine> command = provider.getCommand(type);
                try {
                    List<Medicine> medicines = command.execute(xmlPath);
                    medicines.forEach(medicine -> service.add(medicine));
                } catch (BuilderException e) {
                    LOG.error(e);
                }
            } else {
                throw new IllegalArgumentException("XML isn't valid.");
            }
        } else {
            throw new IllegalArgumentException("Files paths are't valid.");
        }
    }
}
