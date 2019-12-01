package by.training.machine.monitoring.dao;

import by.training.machine.monitoring.core.Bean;
import lombok.extern.log4j.Log4j;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedQueue;

@Log4j
@Bean
public class TransactionManagerQueue implements TransactionManager {
    private DataSource dataSource;
    private ThreadLocal<ConcurrentLinkedQueue<Connection>> localConnection = new ThreadLocal<>();

    public TransactionManagerQueue(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void beginTransaction() throws DaoException {
        if (localConnection.get() == null) {
            Connection connection = dataSource.getConnection();
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                throw new DaoSqlException("Failed to set false autocommit", e);
            }
            localConnection.set(new ConcurrentLinkedQueue<>());
            localConnection.get().offer(connection);
        } else {
            log.info("Transaction was started");
            Connection connection = dataSource.getConnection();
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                throw new DaoSqlException("Failed to set false autocommit", e);
            }
            localConnection.get().offer(connection);
        }
    }

    @Override
    public void commitTransaction() throws DaoException, SQLException {
        Connection connection = localConnection.get().poll();
        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException e) {
                throw new DaoSqlException(e);
            } finally {
                connection.close();
            }
        }
        if (localConnection.get().isEmpty()) {
            localConnection.remove();
        }
    }

    @Override
    public void rollbackTransaction() throws DaoException, SQLException {
        Connection connection = localConnection.get().poll();
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new DaoSqlException(e);
            } finally {
                connection.close();
            }
        }
        if (localConnection.get().isEmpty()) {
            localConnection.remove();
        }
    }

    @Override
    public Connection getConnection() throws DaoException {
        if (localConnection.get() != null && localConnection.get().peek() != null) {
            return (Connection) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{Connection.class},
                    (proxy, method, args) -> {
                        if (method.getName().equals("close")) {
                            return null;
                        } else {
                            return method.invoke(localConnection.get().peek(), args);
                        }
                    });
        }
        return dataSource.getConnection();
    }
}
