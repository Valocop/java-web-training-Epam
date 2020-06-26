package by.training.machine.monitoring.characteristic;

import by.training.machine.monitoring.message.MessageManager;
import by.training.machine.monitoring.validator.ResultValidator;
import by.training.machine.monitoring.validator.ResultValidatorImpl;
import by.training.machine.monitoring.validator.Validator;

import java.util.Arrays;
import java.util.Map;

public class AddCharacteristicValidator implements Validator {

    @Override
    public ResultValidator validate(Map<String, String> data, MessageManager messageManager) {
        ResultValidator rv = new ResultValidatorImpl();
        checkPrice(data.get("characteristic.price"), rv, messageManager);
        checkPower(data.get("characteristic.power"), rv, messageManager);
        checkFuelType(data.get("characteristic.fuel.type"), rv, messageManager);
        checkEngineVolume(data.get("characteristic.engine.volume"), rv, messageManager);
        checkTransmission(data.get("characteristic.transmission"), rv, messageManager);
        return rv;
    }

    private void checkPrice(String priceStr, ResultValidator rv, MessageManager messageManager) {
        if (priceStr != null && !priceStr.isEmpty()) {
            try {
                Double.valueOf(priceStr);
            } catch (NumberFormatException e) {
                rv.addException("characteristic.price.error", Arrays.asList(messageManager.getMessage("characteristic.price.error.format")));
            }
        } else {
            rv.addException("characteristic.price.error", Arrays.asList(messageManager.getMessage("characteristic.price.empty")));
        }
    }

    private void checkPower(String power, ResultValidator rv, MessageManager messageManager) {
        if (power != null && !power.isEmpty()) {
            if (power.length() > 150) {
                rv.addException("characteristic.power.error", Arrays.asList(messageManager.getMessage("characteristic.power.over")));
            }
        } else {
            rv.addException("characteristic.power.error", Arrays.asList(messageManager.getMessage("characteristic.power.empty")));
        }
    }

    private void checkFuelType(String fuelType, ResultValidator rv, MessageManager messageManager) {
        if (fuelType != null && !fuelType.isEmpty()) {
            if (fuelType.length() > 150) {
                rv.addException("characteristic.fuel.type.error", Arrays.asList(messageManager.getMessage("characteristic.fuel.type.over")));
            }
        } else {
            rv.addException("characteristic.fuel.type.error", Arrays.asList(messageManager.getMessage("characteristic.fuel.type.empty")));
        }
    }

    private void checkEngineVolume(String engineVolume, ResultValidator rv, MessageManager messageManager) {
        if (engineVolume != null && !engineVolume.isEmpty()) {
            if (engineVolume.length() > 100) {
                rv.addException("characteristic.engine.volume.error", Arrays.asList(messageManager.getMessage("characteristic.engine.volume.over")));
            }
        } else {
            rv.addException("characteristic.engine.volume.error", Arrays.asList(messageManager.getMessage("characteristic.engine.volume.empty")));
        }
    }

    private void checkTransmission(String transmission, ResultValidator rv, MessageManager messageManager) {
        if (transmission != null && !transmission.isEmpty()) {
            if (transmission.length() > 150) {
                rv.addException("characteristic.transmission.error", Arrays.asList(messageManager.getMessage("characteristic.transmission.over")));
            }
        } else {
            rv.addException("characteristic.transmission.error", Arrays.asList(messageManager.getMessage("characteristic.transmission.empty")));
        }
    }
}
