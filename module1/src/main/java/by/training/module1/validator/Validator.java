package by.training.module1.validator;

import java.util.Map;

public interface Validator {
    ResultValidator validate(Map<String, String> line);
}
