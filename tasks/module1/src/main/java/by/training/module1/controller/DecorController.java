package by.training.module1.controller;

import by.training.module1.builder.Builder;
import by.training.module1.builder.BuilderFactory;
import by.training.module1.entity.Decor;
import by.training.module1.parser.LineParsing;
import by.training.module1.reader.DataFileReader;
import by.training.module1.service.DecorService;
import by.training.module1.service.Service;
import by.training.module1.validator.DataValidator;
import by.training.module1.validator.FileValidator;
import by.training.module1.validator.LineValidator;
import by.training.module1.validator.ResultValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DecorController implements Controller {
    private static final Logger LOGGER = LogManager.getLogger();
    private Service<Decor> decorService;
    private String path;

    public DecorController(Service<Decor> decorService, String path) {
        this.decorService = decorService;
        this.path = path;
    }

    @Override
    public boolean process() {
        FileValidator fileValidator = new FileValidator(path);
        ResultValidator resultFileValidation = fileValidator.validate();

        if (resultFileValidation.isValid()) {
            DataFileReader dataFileReader = new DataFileReader(path);
            List<String> listOfLines = null;
            try {
                listOfLines = dataFileReader.readData();
            } catch (IOException e) {
                LOGGER.error("IOException to read file [" + path + "]");
                return false;
            }

            for (String line : listOfLines) {
                LineValidator lineValidator = new LineValidator(line);
                ResultValidator resultLineValidation = lineValidator.validate();

                if (resultLineValidation.isValid()) {
                    LineParsing lineParsing = new LineParsing(line);
                    Map<String, String> params = lineParsing.parseLine();
                    DataValidator dataValidator = new DataValidator(params);
                    ResultValidator resultDataValidation = dataValidator.validation();

                    if (resultDataValidation.isValid()) {
                        String type = params.get("type");
                        Builder entityBuilder = BuilderFactory.getBuilder(type);

                        if (entityBuilder != null) {
                            Decor decor = entityBuilder.build(params);
                            decorService.add(decor);
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
