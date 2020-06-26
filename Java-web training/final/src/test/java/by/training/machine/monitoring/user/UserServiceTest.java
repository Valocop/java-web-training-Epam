package by.training.machine.monitoring.user;

import by.training.machine.monitoring.app.ApplicationContext;
import by.training.machine.monitoring.dao.*;
import by.training.machine.monitoring.machine.MachineDao;
import by.training.machine.monitoring.machine.MachineService;
import by.training.machine.monitoring.manufacture.ManufactureService;
import by.training.machine.monitoring.role.RoleService;
import by.training.machine.monitoring.service.ServiceException;
import lombok.extern.log4j.Log4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.Optional;

@Log4j
@RunWith(JUnit4.class)
public class UserServiceTest {
    private UserService userService;
    private UserDao mockUserDao;
    private RoleService mockRoleService;
    private MachineDao mockMachineDao;
    private ManufactureService mockManufactureService;
    private MachineService mockMachineService;
    private UserDto testUser;
    private UserDto loginUser;
    private UserDto registerUser;

    @BeforeClass
    public static void contextInit() {
        try {
            ApplicationContext.initialize();
        } catch (Exception e) {
            log.warn("Context was initialized", e);
        }
    }

    @Before
    public void init() throws SQLException, DaoException {
        testUser = new UserDto(1L, "Test", "Test", "Test@mail.com", "Test", "Test", "12345678", null);
        loginUser = new UserDto(1L, "Test", "Test", "Test@mail.com", "Test", "Test", "12345678", null);
        registerUser = new UserDto(2L, "New", "Test", "Test@mail.com", "Test", "Test", "12345678", null);
        mockUserDao = mockUserDao();
        mockRoleService = mockRoleService();
        mockMachineDao = Mockito.mock(MachineDao.class);
        mockManufactureService = Mockito.mock(ManufactureService.class);
        mockMachineService = Mockito.mock(MachineService.class);
        userService = new UserServiceImpl(mockUserDao, mockRoleService, mockMachineDao, mockManufactureService, mockMachineService);
    }

    @Test
    public void shouldLoginUser() throws DaoException, SQLException {
        boolean isLogin = userService.loginUser(testUser);
        Assert.assertTrue(isLogin);
    }

    @Test
    public void shouldRegisterUser() throws ServiceException {
        boolean isUserRegister = userService.registerUser(registerUser);
        Assert.assertTrue(isUserRegister);
        Mockito.verify(mockRoleService, Mockito.times(1)).assignDefaultRoles(2L);
    }

    @Test
    public void shouldDeleteUser() {
        boolean isUserDeleted = userService.deleteUser(testUser);
        Assert.assertTrue(isUserDeleted);
    }

    @Test
    public void updateUser() {
        boolean isUserUpdated = userService.updateUser(testUser);
        Assert.assertTrue(isUserUpdated);
    }

    private UserDao mockUserDao() throws DaoException {
        UserDao mockUserDao = Mockito.mock(UserDao.class);
        loginUser.setPassword(DigestUtils.md5Hex(loginUser.getPassword()));
        Mockito.when(mockUserDao.findByLogin("Test")).thenReturn(Optional.of(loginUser));
        Mockito.when(mockUserDao.findByLogin("New")).thenReturn(Optional.empty());
        Mockito.when(mockUserDao.save(registerUser)).thenReturn(2L);
        Mockito.when(mockUserDao.deleteAssignUserMachine(testUser.getId())).thenReturn(true);
        Mockito.when(mockUserDao.delete(testUser)).thenReturn(true);
        Mockito.when(mockUserDao.update(testUser)).thenReturn(true);
        return mockUserDao;
    }

    private RoleService mockRoleService() {
        RoleService mockRoleService = Mockito.mock(RoleService.class);
        Mockito.when(mockRoleService.deleteAssignRoles(testUser.getId())).thenReturn(true);
        return mockRoleService;
    }
}
