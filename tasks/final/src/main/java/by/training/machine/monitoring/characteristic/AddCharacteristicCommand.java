package by.training.machine.monitoring.characteristic;

import by.training.machine.monitoring.ApplicationConstant;
import by.training.machine.monitoring.SecurityService;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Bean(name = "addCharacteristic")
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
        ResultValidator rv = new AddCharacteristicValidator().validate(new HashMap<String, String>() {{
            put("characteristic.price", priceStr);
            put("characteristic.power", power);
            put("characteristic.fuel.type", fuelType);
            put("characteristic.engine.volume", engineVolume);
            put("characteristic.transmission", transmission);
        }}, messageManager);
        if (rv.isValid()) {
            if (characteristicService.assignManufactureCharacteristic(currentUser)
                    && manufactureService.getManufactureByUserId(currentUser.getId()).isPresent()) {
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
                    } catch (IOException e) {
                        throw new CommandException("Failed to execute addCharacteristic command", e);
                    }
                } else {
                    failVersion(req, resp, rv);
                }
            } else {
                failVersion(req, resp, rv);
            }
        } else {
            failVersion(req, resp, rv);
        }
    }

    private void failVersion(HttpServletRequest req, HttpServletResponse resp, ResultValidator rv) throws CommandException {
        UserEntity currentUser = SecurityService.getInstance().getCurrentUser(req.getSession(false));
        Optional<ManufactureDto> manufactureByUserId = manufactureService.getManufactureByUserId(currentUser.getId());
        if (manufactureByUserId.isPresent()) {
            List<CharacteristicDto> characteristics = characteristicService.getCharacteristicByManufacture(manufactureByUserId.get().getId());
            List<ModelDto> models = modelService.getModelByUserId(currentUser.getId());
            req.setAttribute("models", models);
            req.setAttribute("characteristics", characteristics);
        }
        rv.getExceptionMap().forEach(req::setAttribute);
        req.setAttribute("toast", "Fail to add characteristic. Try again");
        try {
            req.setAttribute("commandName", "showAddMachine");
            req.getRequestDispatcher("/jsp/main.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandException("Failed to execute AddCharacteristicCommand", e);
        }
    }
}
