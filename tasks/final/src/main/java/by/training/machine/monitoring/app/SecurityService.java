package by.training.machine.monitoring.app;

import by.training.machine.monitoring.dao.DaoException;
import by.training.machine.monitoring.entity.UserEntity;
import by.training.machine.monitoring.entity.UserRole;
import by.training.machine.monitoring.role.RoleService;
import by.training.machine.monitoring.role.RoleServiceImpl;
import by.training.machine.monitoring.role.RoleUtil;
import by.training.machine.monitoring.user.UserDto;
import by.training.machine.monitoring.user.UserUtil;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Log4j
public final class SecurityService {
    private final static AtomicBoolean INITIALIZED = new AtomicBoolean(false);
    private final static Lock INITIALIZE_LOCK = new ReentrantLock();
    private static SecurityService INSTANCE;
    private ConcurrentHashMap<HttpSession, UserEntity> sessionUserMap = new ConcurrentHashMap<>();

    private SecurityService() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Instance was created");
        }
    }

    public static SecurityService getInstance() {
        if (!INITIALIZED.get()) {
            INITIALIZE_LOCK.lock();
            try {
                if (INSTANCE == null) {
                    INSTANCE = new SecurityService();
                    INITIALIZED.set(true);
                }
            } finally {
                INITIALIZE_LOCK.unlock();
            }
        }
        return INSTANCE;
    }

    public void createSession(HttpSession session, UserEntity user) {
        sessionUserMap.put(session, user);
        log.info("Session was put to poll");
    }

    public boolean deleteSession(HttpSession session) {
        if (sessionUserMap.containsKey(session)) {
            sessionUserMap.remove(session);
            log.info("Session was deleted from poll");
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteSession(Long userId) {
        HttpSession findSession = null;
        for (Map.Entry<HttpSession, UserEntity> map : sessionUserMap.entrySet()) {
            if (map.getValue().getId().equals(userId)) {
                findSession = map.getKey();
            }
        }
        if (findSession != null && sessionUserMap.containsKey(findSession)) {
            sessionUserMap.remove(findSession);
            return true;
        }
        return false;
    }

    public boolean canExecute(String CommandName) {
        return false;
    }

    public UserEntity getCurrentUser(HttpSession session) {
        return sessionUserMap.get(session);
    }

    public List<UserRole> getCurrentRoles(HttpSession session) {
        List<UserRole> userRoles = new ArrayList<>();
        UserEntity user = getCurrentUser(session);
        if (user != null) {
            RoleService roleService = ApplicationContext.getInstance().getBean(RoleServiceImpl.class);
            UserDto userDto = UserUtil.fromEntity(user);
            try {
                userRoles = roleService.getUserRoles(userDto).stream()
                        .map(RoleUtil::fromDto)
                        .collect(Collectors.toList());
            } catch (DaoException e) {
                throw new IllegalStateException("Failed to get user roles");
            }
        }
        return userRoles;
    }

    public List<UserRole> getCurrentRoles(HttpSession session, UserEntity userEntity) {
        List<UserRole> userRoles = new ArrayList<>();
        if (sessionUserMap.containsKey(session)) {
            RoleService service = ApplicationContext.getInstance().getBean(RoleServiceImpl.class);
            try {
                userRoles = service.getUserRoles(UserUtil.fromEntity(userEntity)).stream()
                        .map(RoleUtil::fromDto).collect(Collectors.toList());
            } catch (DaoException e) {
                throw new IllegalStateException("Failed to get user roles");
            }
        }
        return userRoles;
    }

    public boolean isLogIn(HttpSession session) {
        return sessionUserMap.containsKey(session);
    }

    public boolean containRole(HttpSession session, String role) {
        if (sessionUserMap.containsKey(session)) {
            List<UserRole> roles = getCurrentRoles(session);
            return roles.stream()
                    .anyMatch(userRole -> userRole.getRoleName().trim().equalsIgnoreCase(role.trim()));
        } else {
            return false;
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return INSTANCE;
    }

    protected Object readResolve() {
        return INSTANCE;
    }
}
