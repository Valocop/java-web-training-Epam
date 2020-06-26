package by.training.module2.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultValidator {
    private static final Logger LOG = LogManager.getLogger();
    private Map<String, List<String>> exceptionMap = new HashMap<>();

    public boolean isValid() {
        if (exceptionMap.isEmpty()) {
            LOG.info("Result of validate is valid.");
            return true;
        } else {
            LOG.info("[" + exceptionMap.toString() + "Result of validate is not valid.");
            return false;
        }
    }

    void addException(String key, List<String> exceptions) {
        exceptionMap.put(key, exceptions);
    }

    public Map<String, List<String>> getExceptionMap() {
        return exceptionMap;
    }
}
