package by.training.machine.monitoring;

import java.util.Arrays;
import java.util.List;

public class ApplicationConstant {
    public static final String DEFAULT_ROLE = "DEFAULT";
    public static final String ADMIN_ROLE = "ADMIN";
    public static final String MANUFACTURER_ROLE = "MANUFACTURER";
    public static final List<String> ROLES = Arrays.asList(DEFAULT_ROLE, ADMIN_ROLE, MANUFACTURER_ROLE);
    public static final SecurityService SECURITY_SERVICE = SecurityService.getInstance();
    public static final String VIEW_ALL_USERS_CMD = "viewUserList";
    public static String LANGUAGE = "EN";
}
