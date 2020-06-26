package by.training.machine.monitoring.machine;

import by.training.machine.monitoring.message.MessageManager;
import by.training.machine.monitoring.validator.ResultValidator;
import by.training.machine.monitoring.validator.ResultValidatorImpl;
import by.training.machine.monitoring.validator.Validator;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MachineValidator implements Validator {
    private static final String UNIQ_REGEX = "[a-zA-Z0-9]+";

    @Override
    public ResultValidator validate(Map<String, String> data, MessageManager messageManager) {
        ResultValidator rv = new ResultValidatorImpl();
        checkModel(data.get("model"), rv, messageManager);
        checkCharacteristic(data.get("characteristic"), rv, messageManager);
        checkUniqCode(data.get("machine.uniq.number"), rv, messageManager);
        return rv;
    }

    private void checkModel(String modelIdStr, ResultValidator rv, MessageManager messageManager) {
        if (modelIdStr != null && !modelIdStr.isEmpty()) {
            try {
                Long.parseLong(modelIdStr);
            } catch (NumberFormatException e) {
                rv.addException("model.id.error", Arrays.asList(messageManager.getMessage("model.id.error")));
            }
        } else {
            rv.addException("model.id.error", Arrays.asList(messageManager.getMessage("model.id.empty.error")));
        }
    }

    private void checkCharacteristic(String characteristicIdStr, ResultValidator rv, MessageManager messageManager) {
        if (characteristicIdStr != null && !characteristicIdStr.isEmpty()) {
            try {
                Long.parseLong(characteristicIdStr);
            } catch (NumberFormatException e) {
                rv.addException("characteristic.id.error", Arrays.asList(messageManager.getMessage("characteristic.id.error")));
            }
        } else {
            rv.addException("characteristic.id.error", Arrays.asList(messageManager.getMessage("characteristic.id.empty")));
        }
    }

    private void checkUniqCode(String uniqCodeStr, ResultValidator rv, MessageManager messageManager) {
        if (uniqCodeStr != null && !uniqCodeStr.isEmpty()) {
            Pattern pattern = Pattern.compile(UNIQ_REGEX);
            Matcher matcher = pattern.matcher(uniqCodeStr);
            if (uniqCodeStr.length() > 50 || !matcher.matches()) {
                rv.addException("machine.uniq.number.error", Arrays.asList(messageManager.getMessage("uniq.number.error")));
            }
        } else {
            rv.addException("machine.uniq.number.error", Arrays.asList(messageManager.getMessage("uniq.number.empty.error")));
        }
    }
}
