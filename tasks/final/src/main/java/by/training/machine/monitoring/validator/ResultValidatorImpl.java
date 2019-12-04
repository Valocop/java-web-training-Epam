package by.training.machine.monitoring.validator;

import lombok.extern.log4j.Log4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j
public class ResultValidatorImpl implements ResultValidator {
    private Map<String, List<String>> exceptionMap = new HashMap<>();

    @Override
    public boolean isValid() {
        if (exceptionMap.isEmpty()) {
            log.info("Result of validation is valid");
            return true;
        } else {
            log.info("Result of validation is not valid");
            return false;
        }
    }

    @Override
    public void addException(String key, List<String> exceptions) {
        if (exceptionMap.containsKey(key)) {
            exceptionMap.get(key).addAll(exceptions);
        } else {
            exceptionMap.put(key, exceptions);
        }
    }

    @Override
    public Map<String, List<String>> getExceptionMap() {
        return exceptionMap;
    }
}
