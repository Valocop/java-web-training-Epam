package by.training.machine.monitoring.machine;

import by.training.machine.monitoring.ApplicationConstant;
import by.training.machine.monitoring.SecurityService;
import by.training.machine.monitoring.characteristic.CharacteristicDto;
import by.training.machine.monitoring.characteristic.CharacteristicService;
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

@Bean(name = "saveMachine")
@AllArgsConstructor
public class SaveMachineCommand implements ServletCommand {
    private MachineService machineService;
    private CharacteristicService characteristicService;
    private ModelService modelService;
    private ManufactureService manufactureService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        MessageManager messageManager = MessageManager.getMessageManager(req);
        UserEntity currentUser = SecurityService.getInstance().getCurrentUser(req.getSession(false));
        String modelIdStr = req.getParameter("model");
        String characteristicIdStr = req.getParameter("characteristic");
        String uniqStr = req.getParameter("machine.uniq.number");
        ResultValidator rv = new MachineValidator().validate(new HashMap<String, String>() {{
            put("model", modelIdStr);
            put("characteristic", characteristicIdStr);
            put("machine.uniq.number", uniqStr);
        }}, messageManager);
        Optional<ManufactureDto> manufactureByUserId = manufactureService.getManufactureByUserId(currentUser.getId());
        if (rv.isValid() && manufactureByUserId.isPresent()) {
            MachineDto machineDto = MachineDto.builder()
                    .modelId(Long.valueOf(modelIdStr))
                    .characteristicId(Long.valueOf(characteristicIdStr))
                    .uniqNumber(uniqStr)
                    .manufactureId(manufactureByUserId.get().getId())
                    .build();
            if (machineService.saveMachine(machineDto)) {
                try {
                    resp.sendRedirect(req.getContextPath() + "/app?commandName=" + ApplicationConstant.SHOW_LIST_MACHINES_CMD);
                    return;
                } catch (IOException e) {
                    throw new CommandException("Failed to redirect", e);
                }
            }
        }
        rv.getExceptionMap().forEach(req::setAttribute);
        if (manufactureByUserId.isPresent()) {
            List<CharacteristicDto> characteristics = characteristicService.getCharacteristicByManufacture(manufactureByUserId.get().getId());
            List<ModelDto> models = modelService.getModelByUserId(currentUser.getId());
            req.setAttribute("models", models);
            req.setAttribute("characteristics", characteristics);
        }
        req.setAttribute("toast", "Fail to save machine");
        req.setAttribute("commandName", "showAddMachine");
        try {
            req.getRequestDispatcher("/jsp/main.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandException("Fail to add machine", e);
        }
    }
}
