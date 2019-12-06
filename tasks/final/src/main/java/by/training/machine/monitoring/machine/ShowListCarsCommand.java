package by.training.machine.monitoring.machine;

import by.training.machine.monitoring.SecurityService;
import by.training.machine.monitoring.command.CommandException;
import by.training.machine.monitoring.command.ServletCommand;
import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.entity.UserEntity;
import by.training.machine.monitoring.manufacture.ManufactureDto;
import by.training.machine.monitoring.manufacture.ManufactureService;
import by.training.machine.monitoring.message.MessageManager;
import lombok.AllArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Bean(name = "showListCars")
@AllArgsConstructor
public class ShowListCarsCommand implements ServletCommand {
    private MachineService machineService;
    private ManufactureService manufactureService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        UserEntity currentUser = SecurityService.getInstance().getCurrentUser(req.getSession(false));
        MessageManager messageManager = MessageManager.getMessageManager(req);
        if (currentUser != null) {
            Optional<ManufactureDto> manufactureByUserId = manufactureService.getManufactureByUserId(currentUser.getId());
            if (manufactureByUserId.isPresent()) {
                List<MachineInfoDto> machineInfoList = machineService.getMachineInfo(manufactureByUserId.get().getId());
                try {
                    req.setAttribute("machineInfo", machineInfoList);
                    req.setAttribute("commandName", "showListCars");
                    req.getRequestDispatcher("/jsp/main.jsp").forward(req, resp);
                    return;
                } catch (ServletException | IOException e) {
                    throw new CommandException("Failed to show machines", e);
                }
            }
        }
        req.setAttribute("toast", "Failed to show list of cars");
        try {
            req.getRequestDispatcher("/jsp/main.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandException("Failed to show machines", e);
        }
    }
}
