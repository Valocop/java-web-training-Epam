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

public class DecorController implements Controller<Decor> {
    private static final Logger LOGGER = LogManager.getLogger();
    private Service<Decor> decorService;
    private String path;

    public DecorController(Service<Decor> decorService, String path) {
        this.decorService = decorService;
        this.path = path;
    }

    @Override
    public boolean process() {
        Validator fileValidator = new FileValidator(path);
        ResultValidator resultFileValidation = fileValidator.validate();

        if (resultFileValidation.isValid()) {
            DataFileReader dataFileReader = new DataFileReader(path);
            List<String> listOfLines = null;
            try {
                listOfLines = dataFileReader.readData();
            } catch (IOException e) {
                LOGGER.error(e);
                return false;
            }

            for (String line : listOfLines) {
                Validator lineValidator = new LineValidator(line);
                ResultValidator resultLineValidation = lineValidator.validate();

                if (resultLineValidation.isValid()) {
                    LineParsing lineParsing = new LineParsing(line);
                    Map<String, String> params = lineParsing.parseLine();
                    DataValidator dataValidator = new DataValidator(params);
                    ResultValidator resultDataValidation = dataValidator.validate();

                    if (resultDataValidation.isValid()) {
                        String type = params.get("type");
                        Optional<DecorType> decorType = DecorType.fromString(type);
                        Builder entityBuilder = null;

                        if (decorType.isPresent()) {
                            entityBuilder = BuilderFactory.getBuilder(decorType.get(), params);
                        }

                        if (entityBuilder != null) {
                            Decor decor = entityBuilder.build();
                            decorService.add(decor);
                            LOGGER.info(decor.toString() + " created to service list.");
                        } else {
                            LOGGER.error("Impossible to create Decor [" + line + "]");
                        }
                    } else {
                        LOGGER.error("Data validate error " + resultDataValidation.getExceptionMap());
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
