package by.training.machine.monitoring.user;

import by.training.machine.monitoring.app.ApplicationConstant;
import by.training.machine.monitoring.app.SecurityService;
import by.training.machine.monitoring.command.CommandException;
import by.training.machine.monitoring.command.ServletCommand;
import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.entity.UserEntity;
import by.training.machine.monitoring.machine.MachineActionService;
import by.training.machine.monitoring.machine.MachineInfoDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Bean(name = ApplicationConstant.SHOW_LIST_USER_MACHINES_CMD)
@Log4j
@AllArgsConstructor
public class ShowUserListCarsCommand implements ServletCommand {
    private UserService userService;
    private MachineActionService machineActionService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        UserEntity currentUser = SecurityService.getInstance().getCurrentUser(req.getSession(false));
        List<MachineInfoDto> assignMachines = userService.getAssignMachines(currentUser.getId());
        assignMachines.forEach(machineInfoDto -> {
            machineActionService.addRandomData(machineInfoDto.getMachineDto().getId());
        });
        if (!assignMachines.isEmpty()) {
            req.setAttribute("userMachines", assignMachines);
            req.setAttribute(ApplicationConstant.COMMAND_NAME, ApplicationConstant.SHOW_LIST_USER_MACHINES_CMD);
            try {
                req.getRequestDispatcher("/jsp/main.jsp").forward(req, resp);
            } catch (ServletException | IOException e) {
                log.error("Failed to execute showUserListCars command", e);
                throw new CommandException(e);
            }
        } else {
            req.setAttribute(ApplicationConstant.TOAST, "Not assailable cars");
            req.setAttribute(ApplicationConstant.COMMAND_NAME, ApplicationConstant.SHOW_REGISTER_CAR_CMD);
            try {
                req.getRequestDispatcher("/jsp/main.jsp").forward(req, resp);
            } catch (ServletException | IOException e) {
                log.error("Failed to execute showUserListCars command", e);
                throw new CommandException(e);
            }
        }
    }
}
