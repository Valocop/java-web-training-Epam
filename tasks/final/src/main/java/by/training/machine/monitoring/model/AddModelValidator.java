package by.training.machine.monitoring.model;

import by.training.machine.monitoring.message.MessageManager;
import by.training.machine.monitoring.validator.ResultValidator;
import by.training.machine.monitoring.validator.ResultValidatorImpl;
import by.training.machine.monitoring.validator.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

public class AddModelValidator implements Validator {
    private final String DATE_FORMAT = "yyyy-MM-dd";

    @Override
    public ResultValidator validate(Map<String, String> data, MessageManager messageManager) {
        ResultValidator rv = new ResultValidatorImpl();
        checkName(data.get("model.name"), rv, messageManager);
        checkReleaseDate(data.get("release.date"), rv, messageManager);
        checkDescription(data.get("model.description"), rv, messageManager);
        return rv;
    }

    private void checkName(String name, ResultValidator rv, MessageManager messageManager) {
        if (name != null && !name.isEmpty()) {
            if (name.length() > 100) {
                rv.addException("model.name.error", Arrays.asList(messageManager.getMessage("modal.name.length")));
            }
        } else {
            rv.addException("model.name.error", Arrays.asList(messageManager.getMessage("modal.name.empty")));
        }
    }

    private void  checkReleaseDate(String dateStr, ResultValidator rv, MessageManager messageManager) {
        if (dateStr != null && !dateStr.isEmpty()) {
            try {
                Date date = new SimpleDateFormat(DATE_FORMAT).parse(dateStr);
            } catch (ParseException e) {
                rv.addException("release.date.error", Arrays.asList(messageManager.getMessage("model.date.incorrect")));
            }
        } else {
            rv.addException("release.date.error", Arrays.asList(messageManager.getMessage("modal.date.empty")));
        }
    }

    private void  checkDescription(String description, ResultValidator rv, MessageManager messageManager) {
        if (description != null && !description.isEmpty()) {
            if (description.length() > 200) {
                rv.addException("model.description.error", Arrays.asList(messageManager.getMessage("model.description.over")));
            }
        } else {
            rv.addException("model.description.error", Arrays.asList(messageManager.getMessage("model.description.empty")));
        }
    }
}
