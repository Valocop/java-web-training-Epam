package by.training.machine.monitoring.user;

import by.training.machine.monitoring.SecurityService;
import by.training.machine.monitoring.command.CommandException;
import by.training.machine.monitoring.command.ServletCommand;
import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.dao.DaoException;
import by.training.machine.monitoring.message.MessageManager;
import by.training.machine.monitoring.validator.ResultValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

@Bean(name = "registerUser")
public class RegisterUserCommand implements ServletCommand {
    private UserService userService;

    public RegisterUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        MessageManager messageManager = MessageManager.getMessageManager(req);
        String login = req.getParameter("user.reg.login");
        String password = req.getParameter("user.password");
        String email = req.getParameter("user.email");
        String name = req.getParameter("user.name");
        String address = req.getParameter("user.address");
        String tel = req.getParameter("user.tel");
        ResultValidator rs = new RegisterUserValidator().validate(new HashMap<String, String>() {{
            put("user.login", login);
            put("user.password", password);
            put("user.email", email);
            put("user.name", name);
            put("user.address", address);
            put("user.tel", tel);
        }}, messageManager);
        if (rs.isValid()) {
            UserDto userDto = UserDto.builder()
                    .login(login)
                    .password(password)
                    .email(email)
                    .name(name)
                    .address(address)
                    .tel(tel)
                    .build();
            try {
                if (userService.registerUser(userDto)) {
                    try {
                        userDto = userService.getByLogin(login);
                        SecurityService.getInstance().createSession(req.getSession(true), UserUtil.fromDto(userDto));
                        resp.sendRedirect(req.getContextPath());
                    } catch (IOException e) {
                        throw new CommandException(e);
                    }
                } else {
                    rs.addException("user.login.incorrect",
                            Arrays.asList(messageManager.getMessage("user.login.incorrect.user.exist")));
                    req.setAttribute("user.registration", "true");
                    rs.getExceptionMap().forEach(req::setAttribute);
                    try {
                        req.getRequestDispatcher("/jsp/main.jsp").forward(req, resp);
                    } catch (ServletException | IOException e) {
                        throw new CommandException(e);
                    }
                }
            } catch (DaoException e) {
                throw new CommandException(e);
            }
        } else {
            req.setAttribute("user.registration", "true");
            rs.getExceptionMap().forEach(req::setAttribute);
            try {
                req.getRequestDispatcher("/jsp/main.jsp").forward(req, resp);
            } catch (ServletException | IOException e) {
                throw new CommandException(e);
            }
        }
    }

    public UserService getUserService() {
        return userService;
    }
}
