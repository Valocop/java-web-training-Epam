package by.training.machine.monitoring.filter;

import by.training.machine.monitoring.SecurityService;
import by.training.machine.monitoring.entity.UserEntity;
import by.training.machine.monitoring.entity.UserRole;
import by.training.machine.monitoring.message.MessageManager;
import lombok.extern.log4j.Log4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@WebFilter(urlPatterns = {"/*"})
@Log4j
public class CommandFilter implements Filter {
    private static final String GUEST_ROLE = "GUEST";
    private List<String> commands = new ArrayList<>();
    private List<String> roles = new ArrayList<>();
    private Map<String, List<String>> commandsRolesMap = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("security.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            commands = Arrays.asList(properties.getProperty("commands").split(", "));
            roles = Arrays.asList(properties.getProperty("roles").split(", "));
            commands.forEach(command -> {
                command = command.trim();
                List<String> roles = Arrays.asList(properties.getProperty("command." + command).split(", "));
                roles.forEach(s -> s = s.trim());
                commandsRolesMap.put(command, roles);
            });
        } catch (IOException e) {
            throw new ServletException("Failed to read security.properties", e);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        MessageManager messageManager = MessageManager.getMessageManager((HttpServletRequest) servletRequest);
        String command = servletRequest.getParameter("commandName");
        HttpSession session = ((HttpServletRequest) servletRequest).getSession(false);
        List<String> userRoles = new ArrayList<>();
        if (session != null) {
            UserEntity user = SecurityService.getInstance().getCurrentUser(session);
            if (user != null) {
                userRoles = Objects.requireNonNull(SecurityService.getInstance().getCurrentRoles(session, user)).stream()
                        .map(UserRole::getRoleName)
                        .collect(Collectors.toList());
            } else {
                userRoles.add(GUEST_ROLE);
            }
        } else {
            userRoles.add(GUEST_ROLE);
        }
        List<String> commandRoles;
        if ((commandRoles = commandsRolesMap.get(command)) != null) {
            if (userRoles.stream().anyMatch(commandRoles::contains)) {
                log.info("Command " + command + " was checked and completed");
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                log.info("Command " + command + " wasn't completed");
                servletRequest.setAttribute("toast", messageManager.getMessage("command.not.complete"));
                servletRequest.getServletContext().getRequestDispatcher("/jsp/main.jsp")
                        .forward(servletRequest, servletResponse);
            }
        } else {
            log.info("Command unknown");
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        commands.clear();
        roles.clear();
        commandsRolesMap.clear();
    }
}
