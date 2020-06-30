package by.training.module1.controller;

import by.training.module1.builder.Builder;
import by.training.module1.builder.BuilderFactory;
import by.training.module1.builder.DecorType;
import by.training.module1.entity.Decor;
import by.training.module1.parser.LineParsing;
import by.training.module1.reader.DataFileReader;
import by.training.module1.service.Service;
import by.training.module1.validator.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DecorController {
    private static final Logger LOGGER = LogManager.getLogger();
    private Service<Decor> decorService;

    public DecorController(Service<Decor> decorService) {
        this.decorService = decorService;
    }

    public boolean process(String path) {
        FileValidator fileValidator = new FileValidator();
        ResultValidator resultFileValidation = fileValidator.validateFile(path);

        if (resultFileValidation.isValid()) {
            DataFileReader dataFileReader = new DataFileReader();
            List<String> listOfLines = null;
            try {
                listOfLines = dataFileReader.readData(path);
            } catch (IOException e) {
                LOGGER.error(e);
                return false;
            }

            for (String line : listOfLines) {
                LineValidator lineValidator = new LineValidator();
                ResultValidator resultLineValidation = lineValidator.validateLine(line);

                if (resultLineValidation.isValid()) {
                    LineParsing lineParsing = new LineParsing();
                    Map<String, String> params = lineParsing.parseLine(line);
                    String type = params.get("type");
                    Validator entityValidator = new ValidatorFactory().getValidator(type);

                    if (entityValidator != null) {
                        ResultValidator resultEntityValidator = entityValidator.validate(params);

                        if (resultEntityValidator.isValid()) {
                            Builder builder = new BuilderFactory().getBuilder(type);
                            Decor decor = builder.build(params);
                            decorService.add(decor);
                        } else {
                            LOGGER.error("Validator error " + resultEntityValidator.getExceptionMap());
                        }
                    } else {
                        LOGGER.error("Validator didn't created. Check param: type.");
                    }
                } else {
                    LOGGER.error("Line validate error " + resultLineValidation.getExceptionMap());
                }
            }
            return true;
        } else {
            LOGGER.error("File validate error " + resultFileValidation.getExceptionMap());
            return false;
        }
    }
}
