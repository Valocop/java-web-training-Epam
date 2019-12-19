package by.training.machine.monitoring.user;

import by.training.machine.monitoring.app.ApplicationConstant;
import by.training.machine.monitoring.command.CommandException;
import by.training.machine.monitoring.command.ServletCommand;
import by.training.machine.monitoring.core.Bean;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Bean(name = ApplicationConstant.SHOW_REGISTER_CAR_CMD)
@Log4j
@AllArgsConstructor
public class ShowRegisterCarCommand implements ServletCommand {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        req.setAttribute(ApplicationConstant.COMMAND_NAME, ApplicationConstant.SHOW_REGISTER_CAR_CMD);
        try {
            req.getRequestDispatcher("/jsp/main.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            log.error("Failed to show register car");
            throw new CommandException(e);
        }
    }
}
