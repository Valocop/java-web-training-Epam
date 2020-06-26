package by.training.machine.monitoring.dao;

import java.sql.Connection;

public interface DataSource {
    Connection getConnection() throws DaoException;
    void close() throws DaoException;
}
