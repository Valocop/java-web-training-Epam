package by.training.machine.monitoring.user;

import by.training.machine.monitoring.app.ApplicationContext;
import by.training.machine.monitoring.dao.*;
import lombok.extern.log4j.Log4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Log4j
@RunWith(JUnit4.class)
public class UserDaoTest {
    //language=H2
    private static final String CREATE_SCHEMA_MACHINE = "create schema machine_monitoring_schema";
    //language=H2
    private static final String DROP_SCHEMA_MACHINE = "drop schema machine_monitoring_schema";
    //language=H2
    private static final String CREATE_USER_ACCOUNT_TABLE = "create table machine_monitoring_schema.user_account" +
            "(id integer auto_increment primary key, " +
            "login varchar(30) not null," +
            "password varchar(50) not null," +
            "email varchar(50) not null," +
            "name varchar(40) not null," +
            "address varchar(50) not null," +
            "tel varchar(30) not null," +
            "picture blob)";
    //language=H2
    private static final String DROP_USER_ACCOUNT_TABLE = "drop table machine_monitoring_schema.user_account";

    @BeforeClass
    public static void contextInit() throws SQLException, DaoException {
        try {
            ApplicationContext.initialize();
        } catch (Exception e) {
            log.warn("Context was initialized", e);
        }
    }

    @Before
    public void init() throws DaoException, SQLException {
        executeSql(CREATE_SCHEMA_MACHINE);
        executeSql(CREATE_USER_ACCOUNT_TABLE);
    }

    @Test
    public void shouldSaveAndDeleteUser() throws SQLException, DaoException {
        UserDao userDao = ApplicationContext.getInstance().getBean(UserDao.class);
        UserDto userDto = getTestUser();
        Long save = userDao.save(userDto);
        Assert.assertNotNull(save);
        userDto.setId(save);
        boolean isDeleted = userDao.delete(userDto);
        Assert.assertTrue(isDeleted);
    }

    @Test
    public void shouldSaveUpdateDeleteUser() throws SQLException, DaoException {
        UserDao userDao = ApplicationContext.getInstance().getBean(UserDao.class);
        UserDto userDto = getTestUser();
        Long save = userDao.save(userDto);
        Assert.assertNotNull(save);
        userDto.setId(save);
        userDto.setName("NewName");
        boolean isUpdated = userDao.update(userDto);
        Assert.assertTrue(isUpdated);
        Optional<UserDto> userDtoOptional = userDao.findByLogin(userDto.getLogin());
        boolean isUpdatedResult = userDtoOptional.isPresent();
        Assert.assertTrue(isUpdatedResult);
        boolean isDeleted = userDao.delete(userDto);
        Assert.assertTrue(isDeleted);
    }

    @After
    public void destroy() throws SQLException, DaoException {
        executeSql(DROP_USER_ACCOUNT_TABLE);
        executeSql(DROP_SCHEMA_MACHINE);
    }

    private void executeSql(String sql) throws DaoException, SQLException {
        ConnectionManager connectionManager = ApplicationContext.getInstance().getBean(ConnectionManager.class);
        Assert.assertNotNull(connectionManager);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            connection.setAutoCommit(true);
            ps.executeUpdate();
        }
    }

    private UserDto getTestUser() {
        return UserDto.builder()
                .login("Test")
                .password("Test")
                .email("Test")
                .name("Test")
                .address("Test")
                .tel("Test")
                .picture(new byte[]{1, 2, 3})
                .build();
    }
}
