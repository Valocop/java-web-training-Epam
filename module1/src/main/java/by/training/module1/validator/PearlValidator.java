package by.training.module1.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public class PearlValidator extends EntityValidator {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void validateAllFields(Map<String, String> lineParam, ResultValidator resultValidator) {

        if (checkColor(lineParam)) {
            LOGGER.info("Color parameters are ok.");
        } else {
            resultValidator.addException("IncorrectColor", Arrays.asList("Incorrect color parameters."));
            LOGGER.warn("Incorrect color parameters.");
        }

        if(checkHeight(lineParam)) {
            LOGGER.info("Height parameters are ok.");
        } else {
            resultValidator.addException("IncorrectHeight", Arrays.asList("Incorrect height parameters."));
            LOGGER.warn("Incorrect height parameters.");
        }
    }

    private boolean checkColor(Map<String, String> lineParam) {
        Optional<Map.Entry<String, String>> typeOptional = lineParam.entrySet().stream()
                .filter(entry -> {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    if (key.equalsIgnoreCase("color")) {
                        return NumberValidator.isInteger(value);
                    } else
                        return false;
                }).findAny();

        return typeOptional.isPresent();
    }

    private boolean checkHeight(Map<String, String> lineParam) {
        Optional<Map.Entry<String, String>> typeOptional = lineParam.entrySet().stream()
                .filter(entry -> {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    if (key.equalsIgnoreCase("height")) {
                        return NumberValidator.isDouble(value);
                    } else
                        return false;
                }).findAny();

        return typeOptional.isPresent();
    }
}
