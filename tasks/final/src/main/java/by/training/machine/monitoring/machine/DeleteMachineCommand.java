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
import java.util.Optional;

@Bean(name = ApplicationConstant.DELETE_MACHINE_CMD)
@Log4j
@AllArgsConstructor
public class DeleteMachineCommand implements ServletCommand {
    private MachineService machineService;
    private ManufactureService manufactureService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        UserEntity currentUser = SecurityService.getInstance().getCurrentUser(req.getSession(false));
        String idStr = req.getParameter("machine.id");
        try {
            Long machineId = Long.parseLong(idStr);
            Optional<ManufactureDto> manufactureOptional = manufactureService.getManufactureByUserId(currentUser.getId());
            if (manufactureOptional.isPresent()) {
                boolean isMachineDel = machineService.delMachineByManufacture(machineId, manufactureOptional.get().getId());
                if (isMachineDel) {
                    try {
                        resp.sendRedirect(req.getContextPath() + "/app?commandName=" + ApplicationConstant.SHOW_LIST_MACHINES_CMD);
                    } catch (IOException e) {
                        log.error("Failed to delete machine", e);
                        throw new CommandException(e);
                    }
                } else {
                    failForward(req, resp);
                }
            }
        } catch (NumberFormatException e) {
            failForward(req, resp);
        }
    }

    private void failForward(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        req.setAttribute(ApplicationConstant.COMMAND_NAME, ApplicationConstant.SHOW_LIST_MACHINES_CMD);
        req.setAttribute(ApplicationConstant.TOAST, "Fail to delete machine command");
        try {
            req.getRequestDispatcher("/jsp/main.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            log.error("Fail to delete machine command", e);
            throw new CommandException(e);
        }
    }
}
