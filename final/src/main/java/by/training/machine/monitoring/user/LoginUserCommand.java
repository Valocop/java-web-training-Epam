package by.training.machine.monitoring.user;

import by.training.machine.monitoring.app.ApplicationConstant;
import by.training.machine.monitoring.app.SecurityService;
import by.training.machine.monitoring.command.CommandException;
import by.training.machine.monitoring.command.ServletCommand;
import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.message.MessageManager;
import by.training.machine.monitoring.validator.ResultValidator;
import by.training.machine.monitoring.validator.ResultValidatorImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Bean(name = ApplicationConstant.LOGIN_USER_CMD)
public class LoginUserCommand implements ServletCommand {
    private UserService userService;

    public LoginUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        MessageManager messageManager = MessageManager.getMessageManager(req);
        String login = req.getParameter("user.login");
        String password = req.getParameter("user.password");
        UserDto userDto = UserDto.builder()
                .login(login)
                .password(password)
                .build();
        if (userService.loginUser(userDto)) {
            try {
                req.logout();
                userDto = userService.getByLogin(login);
                SecurityService.getInstance().createSession(req.getSession(true), UserUtil.fromDto(userDto));
                resp.sendRedirect(req.getContextPath());
            } catch (IOException | ServletException e) {
                throw new CommandException("Failed to login user", e);
            }
        } else {
            ResultValidator rv = new ResultValidatorImpl();
            rv.addException("user.login.password.incorrect", Arrays.asList(messageManager.getMessage("user.login.password.incorrect")));
            rv.getExceptionMap().forEach(req::setAttribute);
            req.setAttribute("user.login.exception", true);
            try {
                req.getRequestDispatcher("/jsp/main.jsp").forward(req, resp);
            } catch (ServletException | IOException e) {
                throw new CommandException("Failed to login user", e);
            }
        }
    }
}
