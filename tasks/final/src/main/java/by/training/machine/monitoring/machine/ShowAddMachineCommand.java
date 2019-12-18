package by.training.machine.monitoring.machine;

import by.training.machine.monitoring.app.ApplicationConstant;
import by.training.machine.monitoring.app.SecurityService;
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
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Bean(name = ApplicationConstant.SHOW_ADD_MACHINE_CMD)
@Log4j
@AllArgsConstructor
public class ShowAddMachineCommand implements ServletCommand {
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
                req.setAttribute("models", models);
                req.setAttribute("characteristics", characteristics);
                req.setAttribute(ApplicationConstant.COMMAND_NAME, ApplicationConstant.SHOW_ADD_MACHINE_CMD);
                try {
                    req.getRequestDispatcher("/jsp/main.jsp").forward(req, resp);
                } catch (ServletException | IOException e) {
                    log.error("Fail to show add machine command", e);
                    throw new CommandException(e);
                }
            } else {
                failRedirect(req, resp);
            }
        } else {
            failRedirect(req, resp);
        }
    }

    private void failRedirect(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            resp.sendRedirect(req.getContextPath());
        } catch (IOException e) {
            log.error("Fail to show add machine command", e);
            throw new CommandException(e);
        }
    }
}
