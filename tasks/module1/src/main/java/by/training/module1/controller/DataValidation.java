package by.training.module1.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class DataValidation {
    private static final Logger LOGGER = LogManager.getLogger();
    private Map<String, String> param;
    private ResultValidation resultValidation = new ResultValidation();

    public DataValidation(Map<String, String> param) {
        this.param = param;
    }

    private void checkValue() {
        for (Map.Entry<String, String> paramMap : param.entrySet()) {
            String key = paramMap.getKey().toLowerCase();
            String value = paramMap.getValue().toLowerCase();

            if (key.equals("type")) {
                if (value.equals("amber") || value.equals("pearl")) {
                    LOGGER.info("[" + value + "] value of type correct.");
                } else {
                    resultValidation.addException("ValueOfTypeError", Arrays.asList("[" + value + "] value of type incorrect."));
                    LOGGER.info("[" + value + "] value of type incorrect.");
                }
            }

            if (key.equals("weight")) {
                try {
                    double weight = Double.parseDouble(value);
                    LOGGER.info("[" + weight + "] value of weight correct");
                } catch (NumberFormatException e) {
                    resultValidation.addException("ValueOfWeightError", Arrays.asList("[" + value + "] value of weight incorrect."));
                    LOGGER.info("[" + value + "] value of weight incorrect.");
                }
            }

            if (key.equals("transparency")) {
                try {
                    Integer transparency = Integer.parseInt(value);
                    LOGGER.info("[" + transparency + "] value of transparency correct.");
                } catch (NumberFormatException e) {
                    resultValidation.addException("ValueOfTransparencyError", Arrays.asList("[" + value + "] value of transparency incorrect."));
                    LOGGER.info("[" + value + "] value of transparency incorrect.");
                }
            }

            if (key.equals("value")) {
                try {
                    double transparency = Double.parseDouble(value);
                    LOGGER.info("[" + transparency + "] value of value correct.");
                } catch (NumberFormatException e) {
                    resultValidation.addException("ValueOfValueError", Arrays.asList("[" + value + "] value of value incorrect."));
                    LOGGER.info("[" + value + "] value of value incorrect.");
                }
            }
        }
    }

    private void checkKey() {
        List<String> paramList = new ArrayList<>();
        param.forEach((s, s2) -> paramList.add(s.toLowerCase()));

        if (paramList.contains("type")) {
            LOGGER.info("Parameters contain a type.");
            if (paramList.contains("weight")) {
                LOGGER.info("Parameters contain a weight.");
                if (paramList.contains("transparency")) {
                    LOGGER.info("Parameters contain a transparency.");
                    if (paramList.contains("value")) {
                        LOGGER.info("Parameters contain a value.");
                    } else {
                        resultValidation.addException("ContainValueError", Arrays.asList("[" + paramList.toString() +  "] parameters don't contain a value."));
                        LOGGER.info("[" + paramList.toString() +  "] parameters don't contain a value.");
                    }
                } else {
                    resultValidation.addException("ContainTransparencyError", Arrays.asList("[" + paramList.toString() +  "] parameters don't contain a transparency."));
                    LOGGER.info("[" + paramList.toString() +  "] parameters don't contain a transparency.");
                }
            } else {
                resultValidation.addException("ContainWeightError", Arrays.asList("[" + paramList.toString() +  "] parameters don't contain a weight."));
                LOGGER.info("[" + paramList.toString() +  "] parameters don't contain a weight.");
            }
        } else {
            resultValidation.addException("ContainTypeError", Arrays.asList("[" + paramList.toString() +  "] parameters don't contain a type."));
            LOGGER.info("[" + paramList.toString() +  "] parameters don't contain a type.");
        }
    }

    public ResultValidation validation() {
        checkKey();
        checkValue();
        return resultValidation;
    }
}
