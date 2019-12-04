package by.training.machine.monitoring.user;

import by.training.machine.monitoring.ApplicationConstant;
import by.training.machine.monitoring.command.CommandException;
import by.training.machine.monitoring.command.ServletCommand;
import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.dao.DaoException;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Bean(name = "deleteUser")
@Log4j
public class DeleteUserCommand implements ServletCommand {
    private UserService userService;

    public DeleteUserCommand(UserService userService) {
        this.userService = userService;
    }

    public UserService getUserService() {
        return userService;
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        String strId = req.getParameter("user.id");
        String login = req.getParameter("user.login");
        String email = req.getParameter("user.email");
        String address = req.getParameter("user.address");
        String tel = req.getParameter("user.tel");
        Long userId = null;
        try {
            userId = Long.valueOf(strId);
        } catch (NumberFormatException e) {
            log.warn("Failed to parse id to delete user", e);
            try {
                resp.sendRedirect(req.getContextPath() + "/app?commandName=" + ApplicationConstant.VIEW_ALL_USERS_CMD);
            } catch (IOException ex) {
                throw new CommandException("Failed to delete user", e);
            }
        }
        UserDto userDto = UserDto.builder()
                .id(userId)
                .login(login)
                .email(email)
                .address(address)
                .tel(tel)
                .build();
        try {
            userService.deleteUser(userDto);
            resp.sendRedirect(req.getContextPath() + "/app?commandName=" + ApplicationConstant.VIEW_ALL_USERS_CMD);
        } catch (DaoException | IOException e) {
            throw new CommandException("Failed to delete user", e);
        }
    }
}
