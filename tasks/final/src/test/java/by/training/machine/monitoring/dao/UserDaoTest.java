package by.training.machine.monitoring.dao;

import by.training.machine.monitoring.ApplicationContext;
import by.training.machine.monitoring.user.UserDao;
import by.training.machine.monitoring.user.UserDto;
import by.training.machine.monitoring.user.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@RunWith(JUnit4.class)
public class UserDaoTest {
    //language=PostgreSQL
    private static String CREATE_TABLE = "CREATE TABLE machine_monitoring_schema.user_test (" +
            " id BIGSERIAL UNIQUE NOT NULL PRIMARY KEY , " +
            " login VARCHAR(40) NOT NULL UNIQUE, " +
            " password VARCHAR NOT NULL, " +
            " email VARCHAR(60) NOT NULL, " +
            " name VARCHAR(100) NOT NULL, " +
            " address VARCHAR(150) NOT NULL, " +
            " tel VARCHAR(50) NOT NULL, " +
            " picture bytea NOT NULL " +
            " )";
    //language=PostgreSQL
    private static String DROP_TABLE = "DROP TABLE machine_monitoring_schema.user_test";

    @Before
    public void init() throws SQLException, DaoException {
        ApplicationContext.initialize();
        executeSql(CREATE_TABLE);
    }

    @Test
    public void shouldRegisterUserInTransaction() throws DaoException, SQLException {
        TransactionManager spyTransactionManager = spyFromBean();
        UserService userService = ApplicationContext.getInstance().getBean(UserService.class);
        Assert.assertNotNull(userService);

        UserDto dto = UserDto.builder()
                .login("Test")
                .password("Test")
                .email("Test@gmail.com")
                .address("Minsk")
                .name("Test")
                .tel("+375333718398")
                .picture(new byte[]{1, 2, 3})
                .build();
        boolean registred = userService.registerUser(dto);
        Assert.assertTrue(registred);

        List<UserDto> allUsers = userService.getAllUsers();
        Assert.assertEquals(1, allUsers.size());
        Mockito.verify(spyTransactionManager, Mockito.times(1)).beginTransaction();
        Mockito.verify(spyTransactionManager, Mockito.times(1)).commitTransaction();
        Assert.assertEquals(PoolConnection.getInstance().getPoolSize(), PoolConnection.getInstance().getFreeConnections());
    }

    private TransactionManager spyFromBean() {
        DataSource dataSource = ApplicationContext.getInstance().getBean(DataSource.class);
        TransactionManager transactionManager = ApplicationContext.getInstance().getBean(TransactionManager.class);
        ConnectionManager connectionManager = ApplicationContext.getInstance().getBean(ConnectionManagerImpl.class);
        TransactionInterceptor transactionInterceptor = ApplicationContext.getInstance().getBean(TransactionInterceptor.class);
        boolean removedTransactionManager = ApplicationContext.getInstance().removeBean(transactionManager);
        boolean removedConnectionManager = ApplicationContext.getInstance().removeBean(connectionManager);
        boolean removedTransactionInterceptor = ApplicationContext.getInstance().removeBean(transactionInterceptor);

        Assert.assertTrue(removedTransactionManager);
        Assert.assertTrue(removedConnectionManager);
        Assert.assertTrue(removedTransactionInterceptor);

        TransactionManager spyTransactionManager = Mockito.spy(new TransactionManagerImpl(dataSource));
        ConnectionManager spyConnectionManager = new ConnectionManagerImpl(spyTransactionManager, dataSource);
        TransactionInterceptor spyTransactionInterceptor = new TransactionInterceptor(spyTransactionManager);

        ApplicationContext.getInstance().registerBean(spyTransactionManager);
        ApplicationContext.getInstance().registerBean(spyConnectionManager);
        ApplicationContext.getInstance().registerBean(spyTransactionInterceptor);
        return spyTransactionManager;
    }

    @After
    public void destroy() throws SQLException, DaoException {
        executeSql(DROP_TABLE);
        ApplicationContext.getInstance().destroy();
    }

    private void executeSql(String sql) throws DaoException, SQLException {
        DataSource dataSource = new DataSourceImpl();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
        }
    }
}
