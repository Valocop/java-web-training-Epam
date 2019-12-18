package by.training.machine.monitoring.characteristic;

import by.training.machine.monitoring.app.ApplicationConstant;
import by.training.machine.monitoring.app.SecurityService;
import by.training.machine.monitoring.command.CommandException;
import by.training.machine.monitoring.command.ServletCommand;
import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.entity.UserEntity;
import by.training.machine.monitoring.manufacture.ManufactureDto;
import by.training.machine.monitoring.manufacture.ManufactureService;
import by.training.machine.monitoring.message.MessageManager;
import by.training.machine.monitoring.model.ModelDto;
import by.training.machine.monitoring.model.ModelService;
import by.training.machine.monitoring.validator.ResultValidator;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Bean(name = ApplicationConstant.ADD_CHARACTERISTIC_CMD)
@Log4j
@AllArgsConstructor
public class AddCharacteristicCommand implements ServletCommand {
    private CharacteristicService characteristicService;
    private ManufactureService manufactureService;
    private ModelService modelService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        MessageManager messageManager = MessageManager.getMessageManager(req);
        String priceStr = req.getParameter("characteristic.price");
        String power = req.getParameter("characteristic.power");
        String fuelType = req.getParameter("characteristic.fuel.type");
        String engineVolume = req.getParameter("characteristic.engine.volume");
        String transmission = req.getParameter("characteristic.transmission");
        UserEntity currentUser = SecurityService.getInstance().getCurrentUser(req.getSession(false));
        Map<String, String> data = new HashMap<>();
        data.put("characteristic.price", priceStr);
        data.put("characteristic.power", power);
        data.put("characteristic.fuel.type", fuelType);
        data.put("characteristic.engine.volume", engineVolume);
        data.put("characteristic.transmission", transmission);
        ResultValidator rv = new AddCharacteristicValidator().validate(data, messageManager);
        if (rv.isValid()) {
            if (manufactureService.getManufactureByUserId(currentUser.getId()).isPresent()) {
                CharacteristicDto characteristicDto = CharacteristicDto.builder()
                        .price(Double.valueOf(priceStr))
                        .power(power)
                        .fuelType(fuelType)
                        .engineVolume(engineVolume)
                        .transmission(transmission)
                        .manufactureId(manufactureService.getManufactureByUserId(currentUser.getId()).get().getId())
                        .build();
                if (characteristicService.saveCharacteristic(characteristicDto)) {
                    try {
                        resp.sendRedirect(req.getContextPath() + "/app?commandName=" + ApplicationConstant.SHOW_ADD_MACHINE_CMD);
                        return;
                    } catch (IOException e) {
                        log.error("Failed to execute addCharacteristic command", e);
                        throw new CommandException(e);
                    }
                }
            }
        }
        failForward(req, resp, currentUser, rv);
    }

    private void failForward(HttpServletRequest req, HttpServletResponse resp, UserEntity currentUser, ResultValidator rv) throws CommandException {
        Optional<ManufactureDto> manufactureByUserId = manufactureService.getManufactureByUserId(currentUser.getId());
        if (manufactureByUserId.isPresent()) {
            List<CharacteristicDto> characteristics = characteristicService.getCharacteristicByManufacture(manufactureByUserId.get().getId());
            List<ModelDto> models = modelService.getModelByUserId(currentUser.getId());
            req.setAttribute("models", models);
            req.setAttribute("characteristics", characteristics);
        }
        rv.getExceptionMap().forEach(req::setAttribute);
        req.setAttribute(ApplicationConstant.TOAST, "Fail to add characteristic. Try again");
        try {
            req.setAttribute(ApplicationConstant.COMMAND_NAME, ApplicationConstant.SHOW_ADD_MACHINE_CMD);
            req.getRequestDispatcher("/jsp/main.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            log.error("Failed to execute AddCharacteristicCommand", e);
            throw new CommandException(e);
        }
    }
}
