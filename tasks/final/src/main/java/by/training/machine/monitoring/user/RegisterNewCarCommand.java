package by.training.machine.monitoring.user;

import by.training.machine.monitoring.app.ApplicationConstant;
import by.training.machine.monitoring.app.SecurityService;
import by.training.machine.monitoring.command.CommandException;
import by.training.machine.monitoring.command.ServletCommand;
import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.entity.UserEntity;
import by.training.machine.monitoring.message.MessageManager;
import by.training.machine.monitoring.validator.ResultValidator;
import by.training.machine.monitoring.validator.ResultValidatorImpl;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Log4j
@Bean(name = "registerNewCar")
@AllArgsConstructor
public class RegisterNewCarCommand implements ServletCommand {
    private UserService userService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        MessageManager messageManager = MessageManager.getMessageManager(req);
        UserEntity currentUser = SecurityService.getInstance().getCurrentUser(req.getSession(false));
        ResultValidator rv = new ResultValidatorImpl();
        String uniqNumber = req.getParameter("machine.uniq.number");
        if (uniqNumber != null && !uniqNumber.isEmpty() && currentUser != null) {
            if (userService.registerMachine(currentUser.getId(), uniqNumber)) {
                try {
                    resp.sendRedirect(req.getContextPath() + "/app?commandName=" + ApplicationConstant.SHOW_LIST_USER_MACHINES_CMD);
                } catch (IOException e) {
                    log.error("Failed to execute register new car command", e);
                    throw new CommandException(e);
                }
            } else {
                failForward(req, resp, rv);
            }
        } else {
            rv.addException("machine.uniq.number.error", Arrays.asList(messageManager.getMessage("uniq.number.error")));
            failForward(req, resp, rv);
        }
    }

    private void failForward(HttpServletRequest req, HttpServletResponse resp, ResultValidator rv) throws CommandException {
        try {
            rv.getExceptionMap().forEach(req::setAttribute);
            req.setAttribute("commandName", "showRegisterCar");
            req.setAttribute("toast", "Machine not found");
            req.getRequestDispatcher("/jsp/main.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            log.error("Failed to execute register new car command", e);
            throw new CommandException(e);
        }
    }
}
