package by.training.machine.monitoring.validator;

import by.training.machine.monitoring.message.MessageManager;

import java.util.Map;

public interface Validator {
    ResultValidator validate(Map<String, String> data, MessageManager messageManager);
}
