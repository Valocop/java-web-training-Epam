package by.training.machine.monitoring.user;

import by.training.machine.monitoring.app.ApplicationConstant;
import by.training.machine.monitoring.command.CommandException;
import by.training.machine.monitoring.command.ServletCommand;
import by.training.machine.monitoring.core.Bean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Bean(name = ApplicationConstant.VIEW_USER_LIST_CMD)
public class ViewUserListCommand implements ServletCommand {
    private UserService userService;

    public ViewUserListCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        List<UserRoleDto> allUsers = userService.getAllUsersWithRoles();
        allUsers.sort(Comparator.comparingLong(UserRoleDto::getId));
        req.setAttribute("users", allUsers);
        req.setAttribute(ApplicationConstant.COMMAND_NAME, ApplicationConstant.VIEW_USER_LIST_CMD);
        try {
            req.getRequestDispatcher("/jsp/main.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandException("Failed to execute viewUserList command", e);
        }
    }
}
