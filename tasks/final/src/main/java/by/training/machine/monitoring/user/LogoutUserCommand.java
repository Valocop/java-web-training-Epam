package by.training.machine.monitoring.user;

import by.training.machine.monitoring.app.ApplicationConstant;
import by.training.machine.monitoring.app.SecurityService;
import by.training.machine.monitoring.command.CommandException;
import by.training.machine.monitoring.command.ServletCommand;
import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.message.MessageManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Bean(name = ApplicationConstant.LOGOUT_USER_CMD)
public class LogoutUserCommand implements ServletCommand {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        MessageManager messageManager = MessageManager.getMessageManager(req);
        if (SecurityService.getInstance().deleteSession(req.getSession(false))) {
            try {
                resp.sendRedirect(req.getContextPath());
            } catch (IOException e) {
                throw new CommandException("Failed to logout command", e);
            }
        } else {
            req.setAttribute(ApplicationConstant.TOAST, messageManager.getMessage("user.logout.error.toast"));
            try {
                req.getRequestDispatcher("/jsp/main.jsp").forward(req, resp);
            } catch (ServletException | IOException e) {
                throw new CommandException("Failed to logout command", e);
            }
        }
    }
}
