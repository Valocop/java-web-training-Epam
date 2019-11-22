package by.training.machine.monitoring.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface TransactionManager {
    void beginTransaction() throws DaoException;
    void commitTransaction() throws DaoException, SQLException;
    void rollbackTransaction() throws DaoException, SQLException;
    Connection getConnection() throws DaoException;
}
