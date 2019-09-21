package by.training.module1.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultValidator {
    private static final Logger LOGGER = LogManager.getLogger();
    private Map<String, List<String>> exceptionMap = new HashMap<>();

    public boolean isValid() {
        if (exceptionMap.isEmpty()) {
            LOGGER.info("Result of validation is valid.");
        } else {
            LOGGER.info("[" + exceptionMap.toString() + "Result of validation is not valid.");
        }
        return exceptionMap.isEmpty();
    }

    void addException(String key, List<String> exceptions) {
        exceptionMap.put(key, exceptions);
    }

    public Map<String, List<String>> getExceptionMap() {
        return exceptionMap;
    }
}