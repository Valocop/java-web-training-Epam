package by.training.machine.monitoring.machine;

import by.training.machine.monitoring.SecurityService;
import by.training.machine.monitoring.characteristic.CharacteristicDto;
import by.training.machine.monitoring.characteristic.CharacteristicService;
import by.training.machine.monitoring.command.CommandException;
import by.training.machine.monitoring.command.ServletCommand;
import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.entity.UserEntity;
import by.training.machine.monitoring.manufacture.ManufactureDto;
import by.training.machine.monitoring.manufacture.ManufactureService;
import by.training.machine.monitoring.model.ModelDto;
import by.training.machine.monitoring.model.ModelService;
import lombok.AllArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Bean(name = "showAddMachine")
@AllArgsConstructor
public class ShowAddMachineCommand implements ServletCommand {
    private static String MODELS = "models";
    private static  String CHARACTERISTICS = "characteristics";
    private ModelService modelService;
    private CharacteristicService characteristicService;
    private ManufactureService manufactureService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        UserEntity currentUser = SecurityService.getInstance().getCurrentUser(req.getSession(false));
        if (currentUser != null) {
            Optional<ManufactureDto> manufactureByUserId = manufactureService.getManufactureByUserId(currentUser.getId());
            if (manufactureByUserId.isPresent()) {
                List<CharacteristicDto> characteristics = characteristicService.getCharacteristicByManufacture(manufactureByUserId.get().getId());
                List<ModelDto> models = modelService.getModelByUserId(currentUser.getId());
                req.setAttribute(MODELS, models);
                req.setAttribute(CHARACTERISTICS, characteristics);
                req.setAttribute("commandName", "showAddMachine");
                try {
                    req.getRequestDispatcher("/jsp/main.jsp").forward(req, resp);
                } catch (ServletException | IOException e) {
                    throw new CommandException("Fail to show add machine command", e);
                }
            } else {
                failVersion(req, resp);
            }
        } else {
            failVersion(req, resp);
        }
    }

    private void failVersion(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        req.setAttribute("toast", "Fail to show add machine command");
        try {
            req.getRequestDispatcher("/jsp/main.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandException("Fail to show add machine command", e);
        }
    }
}
