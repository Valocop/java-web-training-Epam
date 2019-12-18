package by.training.machine.monitoring.user;

import by.training.machine.monitoring.app.ApplicationConstant;
import by.training.machine.monitoring.command.CommandException;
import by.training.machine.monitoring.command.ServletCommand;
import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.dao.DaoException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Bean(name = "updateUser")
public class UpdateUserCommand implements ServletCommand {
    private UserService userService;

    public UpdateUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        String strId = req.getParameter("user.id");
        String login = req.getParameter("user.login");
        String email = req.getParameter("user.email");
        String name = req.getParameter("user.name");
        String address = req.getParameter("user.address");
        String tel = req.getParameter("user.tel");
        String role = req.getParameter("selected.role");

        try {
            if (role != null && !role.equalsIgnoreCase("CURRENT")) {
                UserDto userDto = UserDto.builder()
                        .id(Long.parseLong(strId))
                        .login(login)
                        .name(name)
                        .email(email)
                        .address(address)
                        .tel(tel)
                        .build();
                userService.updateUser(userDto, role);
            }
            resp.sendRedirect(req.getContextPath() + "/app?commandName=" + ApplicationConstant.VIEW_ALL_USERS_CMD);
        } catch (DaoException | IOException e) {
            e.printStackTrace();
        }
    }
}
