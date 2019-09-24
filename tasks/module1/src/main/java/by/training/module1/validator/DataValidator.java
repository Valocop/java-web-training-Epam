package by.training.module1.validator;

import by.training.module1.builder.DecorType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class DataValidator implements Validator {
    private static final Logger LOGGER = LogManager.getLogger();
    private Map<String, String> param;
    private ResultValidator resultValidator = new ResultValidator();

    public DataValidator(Map<String, String> param) {
        this.param = param;
    }

    private void checkValue() {
        String typeValue = param.get("type");
        String weightValue = param.get("weight");
        String valueValue = param.get("value");
        String transpValue = param.get("transparency");

        if (DecorType.fromString(typeValue).isPresent()) {
            LOGGER.info("[" + typeValue + "] value of type correct.");

            if (isDouble(weightValue)) {
                LOGGER.info("[" + weightValue + "] value of weight correct");

                if (isDouble(valueValue)) {
                    LOGGER.info("[" + valueValue + "] value of value correct.");

                    if (isInteger(transpValue)) {
                        LOGGER.info("[" + transpValue + "] value of transparency correct.");
                    } else {
                        resultValidator.addException("ValueOfTransparencyError", Arrays.asList("["
                                + transpValue + "] value of transparency incorrect."));
                        LOGGER.error("[" + transpValue + "] value of transparency incorrect.");
                    }
                } else {
                    resultValidator.addException("ValueOfValueError", Arrays.asList("["
                            + valueValue + "] value of value incorrect."));
                    LOGGER.error("[" + valueValue + "] value of value incorrect.");
                }
            } else {
                resultValidator.addException("ValueOfWeightError", Arrays.asList("["
                        + weightValue + "] value of weight incorrect."));
                LOGGER.error("[" + weightValue + "] value of weight incorrect.");
            }
        } else {
            resultValidator.addException("ValueOfTypeError", Arrays.asList("[" + typeValue
                    + "] value of type incorrect."));
            LOGGER.error("[" + typeValue + "] value of type incorrect.");
        }
    }

    private void checkKey() {
        Set<String> paramSet = param.keySet();

        if (matchValue("type", paramSet)) {
            LOGGER.info("Parameters contain a type.");

            if (matchValue("weight", paramSet)) {
                LOGGER.info("Parameters contain a weight.");

                if (matchValue("value", paramSet)) {
                    LOGGER.info("Parameters contain a value.");

                    if (matchValue("transparency", paramSet)) {
                        LOGGER.info("Parameters contain a transparency.");
                    } else {
                        resultValidator.addException("ContainTransparencyError", Arrays.asList("["
                                + paramSet.toString()
                                +  "] parameters don't contain a transparency."));
                        LOGGER.error("[" + paramSet.toString()
                                +  "] parameters don't contain a transparency.");
                    }
                } else {
                    resultValidator.addException("ContainValueError", Arrays.asList("["
                            + paramSet.toString() +  "] parameters don't contain a value."));
                    LOGGER.error("[" + paramSet.toString()
                            +  "] parameters don't contain a value.");
                }
            } else {
                resultValidator.addException("ContainWeightError", Arrays.asList("["
                        + paramSet.toString() + "] parameters don't contain a weight."));
                LOGGER.error("[" + paramSet.toString()
                        +  "] parameters don't contain a weight.");
            }
        } else {
            resultValidator.addException("ContainTypeError", Arrays.asList("["
                    + paramSet.toString()
                    + "] parameters don't contain a type."));
            LOGGER.error("[" + paramSet.toString()
                    +  "] parameters don't contain a type.");
        }
    }

    private boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean matchValue(String key, Set<String> keys) {
        return keys.stream().anyMatch(s -> s.equalsIgnoreCase(key));
    }

    @Override
    public ResultValidator validate() {
        checkKey();
        if (resultValidator.getExceptionMap().isEmpty()) {
            checkValue();
        }
        return resultValidator;
    }
}
