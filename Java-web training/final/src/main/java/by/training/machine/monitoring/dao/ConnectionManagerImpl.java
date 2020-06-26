package by.training.machine.monitoring.dao;

import by.training.machine.monitoring.core.Bean;

import java.lang.reflect.Proxy;
import java.sql.Connection;

@Bean
public class ConnectionManagerImpl implements ConnectionManager {
    private TransactionManager transactionManager;
    private DataSource dataSource;

    public ConnectionManagerImpl(TransactionManager transactionManager, DataSource dataSource) {
        this.transactionManager = transactionManager;
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() throws DaoException {
        Connection managerConnection = transactionManager.getConnection();
        return managerConnection != null ? managerConnection : dataSource.getConnection();
    }
}
