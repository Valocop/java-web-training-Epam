package by.training.module1.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public class AmberValidator extends EntityValidator {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void validateAllFields(Map<String, String> lineParam, ResultValidator resultValidator) {

        if (checkAge(lineParam)) {
            LOGGER.info("Age parameters are ok.");
        } else {
            resultValidator.addException("IncorrectAge", Arrays.asList("Incorrect age parameters."));
            LOGGER.warn("Incorrect age parameters.");
        }

        if (checkSize(lineParam)) {
            LOGGER.info("Size parameters are ok.");
        } else {
            resultValidator.addException("IncorrectSize", Arrays.asList("Incorrect size parameters."));
            LOGGER.warn("Incorrect size parameters.");
        }
    }

    private boolean checkSize(Map<String, String> lineParam) {
        Optional<Map.Entry<String, String>> typeOptional = lineParam.entrySet().stream()
                .filter(entry -> {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    if (key.equalsIgnoreCase("size")) {
                        return NumberValidator.isDouble(value);
                    } else
                        return false;
                }).findAny();

        return typeOptional.isPresent();
    }

    private boolean checkAge(Map<String, String> lineParam) {
        Optional<Map.Entry<String, String>> typeOptional = lineParam.entrySet().stream()
                .filter(entry -> {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    if (key.equalsIgnoreCase("age")) {
                        return NumberValidator.isDouble(value);
                    } else
                        return false;
                }).findAny();

        return typeOptional.isPresent();
    }
}
