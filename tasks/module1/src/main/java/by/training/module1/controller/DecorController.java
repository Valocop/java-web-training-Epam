package by.training.module1.controller;

import by.training.module1.entity.Decor;
import by.training.module1.service.DecorServiceRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DecorController {
    private static final Logger LOGGER = LogManager.getLogger();
    private DecorServiceRepository serviceRepository;
    private String path;

    public DecorController(DecorServiceRepository serviceRepository, String path) {
        this.serviceRepository = serviceRepository;
        this.path = path;
    }

    public boolean process() {
        FileValidation fileValidation = new FileValidation(path);
        ResultValidation resultFileValidation = fileValidation.validate();

        if (resultFileValidation.isValid()) {
            DataFileReader dataFileReader = new DataFileReader(path);
            List<String> listOfLines = null;
            try {
                listOfLines = dataFileReader.readData();
            } catch (IOException e) {
                LOGGER.info("IOException to read file [" + path + "]");
                return false;
            }

            for (String line : listOfLines) {
                LineValidation lineValidation = new LineValidation(line);
                ResultValidation resultLineValidation = lineValidation.validate();

                if (resultLineValidation.isValid()) {
                    LineParsing lineParsing = new LineParsing(line);
                    Map<String, String> params = lineParsing.parseLine();
                    DataValidation dataValidation = new DataValidation(params);
                    ResultValidation resultDataValidation = dataValidation.validation();

                    if (resultDataValidation.isValid()) {
                        String type = params.get("type");
                        Builder entityBuilder = BuilderFactory.getBuilder(type);

                        if (entityBuilder != null) {
                            Decor decor = entityBuilder.build(params);
                            serviceRepository.add(decor);
                            LOGGER.info(decor.toString() + " created to service list.");
                        } else {
                            LOGGER.info("Impossible to create Decor [" + line + "]");
                        }
                    } else {
                        LOGGER.info("Data validation error " + resultDataValidation.getExceptionMap());
                    }
                } else {
                    LOGGER.info("Line validation error " + resultLineValidation.getExceptionMap());
                }
            }
            return true;
        } else {
            LOGGER.info("File validation error " + resultFileValidation.getExceptionMap());
            return false;
        }
    }
}
