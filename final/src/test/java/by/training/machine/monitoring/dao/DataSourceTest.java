package by.training.machine.monitoring.dao;

import by.training.machine.monitoring.app.ApplicationContext;
import lombok.extern.log4j.Log4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Log4j
@RunWith(JUnit4.class)
public class DataSourceTest {
    private static final String CREATE_TABLE_SQL = "CREATE TABLE users (" +
            "  id integer primary key," +
            "  name varchar(30)," +
            "  email  varchar(50))";
    private static final String INSERT_USER = "INSERT INTO users (name, email) VALUES (?, ?)";
    private static final String SELECT_USER = "SELECT * FROM users WHERE name = (?) AND email = (?)";
    private static final String DROP_TABLE = "DROP TABLE users";
    private static final String USER_NAME = "Alex";
    private static final String EMAIL = "Alex@gmail.com";

    @BeforeClass
    public static void init() throws DaoException {
        try {
            ApplicationContext.initialize();
        } catch (Exception e) {
            log.warn("Context was initialized", e);
        }
    }

    @Test
    public void shouldTestConnection() throws DaoException, SQLException {
        DataSource dataSource = new DataSourceImpl();
        ConnectionManager connectionManager = new ConnectionManagerImpl(new TransactionManagerImpl(dataSource), dataSource);
        Connection connection = connectionManager.getConnection();
        Assert.assertNotNull(connection);

        try {
            connection.setAutoCommit(false);
            PreparedStatement createTableStmt = connection.prepareStatement(CREATE_TABLE_SQL);
            createTableStmt.executeUpdate();
            createTableStmt.close();

            PreparedStatement insertUserStmt = connection.prepareStatement(INSERT_USER);
            insertUserStmt.setString(1, USER_NAME);
            insertUserStmt.setString(2, EMAIL);
            int inserted = insertUserStmt.executeUpdate();
            insertUserStmt.close();
            Assert.assertEquals(1, inserted);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            connection.close();
        }

        connection = dataSource.getConnection();
        Assert.assertNotNull(connection);
        PreparedStatement selectUserStmt = connection.prepareStatement(SELECT_USER);
        selectUserStmt.setString(1, USER_NAME);
        selectUserStmt.setString(2, EMAIL);
        ResultSet resultSet = selectUserStmt.executeQuery();
        while (resultSet.next()) {
            Integer id = resultSet.getInt("id");
            String testName = resultSet.getString("name");
            String testEmail = resultSet.getString("email");
            Assert.assertNotNull(id);
            Assert.assertEquals(USER_NAME, testName);
            Assert.assertEquals(EMAIL, testEmail);
        }
        resultSet.close();
        selectUserStmt.close();
        connection.close();

        connection = dataSource.getConnection();
        Assert.assertNotNull(connection);
        PreparedStatement dropTable = connection.prepareStatement(DROP_TABLE);
        boolean result = dropTable.execute();
        Assert.assertFalse(result);
        dropTable.close();
        connection.close();
    }

    @Test
    public void shouldReturnFreeConnections() throws InterruptedException, DaoException {
        DataSource dataSource = new DataSourceImpl();
        ConnectionManager connectionManager = new ConnectionManagerImpl(new TransactionManagerImpl(dataSource), dataSource);
        int poolCount = PoolConnection.getInstance().getPoolSize();
        List<Connection> connectionList = new ArrayList<>(poolCount);

        for (int i = 0; i < poolCount; i ++) {
            connectionList.add(connectionManager.getConnection());
        }

        Assert.assertEquals(0, PoolConnection.getInstance().getFreeConnections());
        Assert.assertEquals(poolCount, PoolConnection.getInstance().getBusyConnections());

        connectionList.forEach(connection -> {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("Can't close connection");
            }
        });
    }
}
