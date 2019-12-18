package by.training.machine.monitoring.machine;

import by.training.machine.monitoring.app.ApplicationConstant;
import by.training.machine.monitoring.app.SecurityService;
import by.training.machine.monitoring.command.CommandException;
import by.training.machine.monitoring.command.ServletCommand;
import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.entity.UserEntity;
import by.training.machine.monitoring.manufacture.ManufactureDto;
import by.training.machine.monitoring.manufacture.ManufactureService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Bean(name = ApplicationConstant.SHOW_LIST_MACHINES_CMD)
@Log4j
@AllArgsConstructor
public class ShowListCarsCommand implements ServletCommand {
    private MachineService machineService;
    private ManufactureService manufactureService;
    private MachineActionService machineActionService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        UserEntity currentUser = SecurityService.getInstance().getCurrentUser(req.getSession(false));
        if (currentUser != null) {
            Optional<ManufactureDto> manufactureByUserId = manufactureService.getManufactureByUserId(currentUser.getId());
            if (manufactureByUserId.isPresent()) {
                List<MachineInfoDto> machineInfoList = machineService.getMachineInfo(manufactureByUserId.get().getId());
                machineInfoList.forEach(machineInfoDto -> {
                    machineActionService.addRandomData(machineInfoDto.getMachineDto().getId());
                });
                try {
                    req.setAttribute("machineInfo", machineInfoList);
                    req.setAttribute(ApplicationConstant.COMMAND_NAME, ApplicationConstant.SHOW_LIST_MACHINES_CMD);
                    req.getRequestDispatcher("/jsp/main.jsp").forward(req, resp);
                    return;
                } catch (ServletException | IOException e) {
                    log.error("Failed to show machines", e);
                    throw new CommandException(e);
                }
            }
        }
        req.setAttribute(ApplicationConstant.TOAST, "Failed to show list of cars");
        try {
            req.getRequestDispatcher("/jsp/main.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            log.error("Failed to show machines", e);
            throw new CommandException(e);
        }
    }
}
