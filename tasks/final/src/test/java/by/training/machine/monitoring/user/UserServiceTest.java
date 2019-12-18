package by.training.machine.monitoring.user;

import by.training.machine.monitoring.app.ApplicationContext;
import by.training.machine.monitoring.dao.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.sql.SQLException;

@RunWith(JUnit4.class)
public class UserServiceTest {

    @Before
    public void init() throws SQLException, DaoException {
        ApplicationContext.initialize();
    }

    @Test
    public void shouldRegisterUserAndDelete() throws DaoException, SQLException {
        TransactionManager spyTransactionManager = spyFromBean();
        UserService userService = ApplicationContext.getInstance().getBean(UserService.class);
        Assert.assertNotNull(userService);

        UserDto userDto = getTestUser();
        boolean isUserRegister = userService.registerUser(userDto);
        Assert.assertTrue(isUserRegister);
        UserDto userDtoSave = userService.getByLogin("Test");
        Assert.assertEquals(userDto.getName(), userDtoSave.getName());
        Mockito.verify(spyTransactionManager, Mockito.times(1)).beginTransaction();
        Mockito.verify(spyTransactionManager, Mockito.times(1)).commitTransaction();
        boolean isUserDeleted = userService.deleteUser(userDtoSave);
        Assert.assertTrue(isUserDeleted);
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
        ApplicationContext.getInstance().destroy();
    }
}
