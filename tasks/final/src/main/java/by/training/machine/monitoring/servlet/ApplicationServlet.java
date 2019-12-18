package by.training.machine.monitoring.servlet;

import by.training.machine.monitoring.app.ApplicationConstant;
import by.training.machine.monitoring.app.ApplicationContext;
import by.training.machine.monitoring.command.CommandException;
import by.training.machine.monitoring.command.ServletCommand;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j
@WebServlet(urlPatterns = "/", loadOnStartup = 1, name = "app")
public class ApplicationServlet extends HttpServlet {
    private static final long serialVersionUID = -898419077104540041L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commandName = req.getParameter(ApplicationConstant.COMMAND_NAME);
        ServletCommand command = ApplicationContext.getInstance().getBean(commandName);
        if (command != null) {
            try {
                command.execute(req, resp);
            } catch (CommandException e) {
                throw new ServletException(e);
            }
        } else {
            resp.sendRedirect(req.getContextPath());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
