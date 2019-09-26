package by.training.module1.validator;

import by.training.module1.builder.DecorType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public abstract class EntityValidator implements Validator {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public ResultValidator validate(Map<String, String> lineParam) {
        ResultValidator resultValidator = new ResultValidator();
        validateCommonFields(lineParam, resultValidator);
        validateAllFields(lineParam, resultValidator);
        return resultValidator;
    }

    private ResultValidator validateCommonFields(Map<String, String> lineParam, ResultValidator resultValidator) {

        if (checkType(lineParam)) {
            LOGGER.info("Type parameters are ok.");
        } else {
            resultValidator.addException("IncorrectType", Arrays.asList("Incorrect type."));
            LOGGER.warn("Incorrect type parameters.");
        }

        if (checkValue(lineParam)) {
            LOGGER.info("Value parameters are ok.");
        } else {
            resultValidator.addException("IncorrectValue", Arrays.asList("Incorrect value."));
            LOGGER.warn("Incorrect value parameters.");
        }

        if (checkWeight(lineParam)) {
            LOGGER.info("Weight parameters are ok.");
        } else {
            resultValidator.addException("IncorrectWeight", Arrays.asList("Incorrect weight."));
            LOGGER.warn("Incorrect weight parameters.");
        }

        if (checkTransparency(lineParam)) {
            LOGGER.info("Transparency parameters are ok.");
        } else {
            resultValidator.addException("IncorrectTransparency", Arrays.asList("Incorrect transparency."));
            LOGGER.warn("Incorrect transparency parameters.");
        }
        return resultValidator;
    }

    private boolean checkType(Map<String, String> lineParam) {
        Optional<Map.Entry<String, String>> typeOptional = lineParam.entrySet().stream()
                .filter(entry -> {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    if (key.equalsIgnoreCase("type")) {
                        return DecorType.fromString(value).isPresent();
                    } else
                        return false;
                }).findAny();

        return typeOptional.isPresent();
    }

    private boolean checkValue(Map<String, String> lineParam) {
        Optional<Map.Entry<String, String>> valueOptional = lineParam.entrySet().stream()
                .filter(entry -> {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    if (key.equalsIgnoreCase("value")) {
                        return NumberValidator.isDouble(value);
                    } else
                        return false;
                }).findAny();

        return valueOptional.isPresent();
    }

    private boolean checkWeight(Map<String, String> lineParam) {
        Optional<Map.Entry<String, String>> weightOptional = lineParam.entrySet().stream()
                .filter(entry -> {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    if (key.equalsIgnoreCase("weight")) {
                        return NumberValidator.isDouble(value);
                    } else
                        return false;
                }).findAny();

        return weightOptional.isPresent();
    }

    private boolean checkTransparency(Map<String, String> lineParam) {
        Optional<Map.Entry<String, String>> transpOptional = lineParam.entrySet().stream()
                .filter(entry -> {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    if (key.equalsIgnoreCase("transparency")) {
                        return NumberValidator.isInteger(value);
                    } else
                        return false;
                }).findAny();

        return transpOptional.isPresent();
    }

    protected abstract void validateAllFields(Map<String, String> lineParam, ResultValidator resultValidator);
}
