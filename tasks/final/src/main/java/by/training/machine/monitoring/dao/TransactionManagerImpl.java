package by.training.machine.monitoring.dao;

import by.training.machine.monitoring.core.Bean;
import lombok.extern.log4j.Log4j;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

@Log4j
@Bean
public class TransactionManagerImpl implements TransactionManager {
    private DataSource dataSource;
    private ThreadLocal<Connection> localConnection = new ThreadLocal<>();

    public TransactionManagerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void beginTransaction() throws DaoException {
        if (localConnection.get() == null) {
            Connection connection = dataSource.getConnection();
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                throw new DaoSqlException(e);
            }
            localConnection.set(connection);
        } else {
            throw new DaoTransactionException("Transaction already started");
        }
    }

    @Override
    public void commitTransaction() throws DaoSqlException, SQLException {
        Connection connection = localConnection.get();
        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException e) {
                throw new DaoSqlException(e);
            } finally {
                connection.close();
            }
        }
        localConnection.remove();
    }

    @Override
    public void rollbackTransaction() throws DaoSqlException, SQLException {
        Connection connection = localConnection.get();
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new DaoSqlException(e);
            } finally {
                connection.close();
            }
        }
        localConnection.remove();
    }

    @Override
    public Connection getConnection() throws DaoException {
        if (localConnection.get() != null) {
            return (Connection) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{Connection.class},
                    (proxy, method, args) -> {
                        if (method.getName().equals("close")) {
                            return null;
                        } else {
                            return method.invoke(localConnection.get(), args);
                        }
                    });
        }
        return dataSource.getConnection();
    }
}
