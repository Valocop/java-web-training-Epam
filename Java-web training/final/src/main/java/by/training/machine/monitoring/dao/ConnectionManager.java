package by.training.machine.monitoring.dao;

import java.sql.Connection;

public interface ConnectionManager {
    Connection getConnection() throws DaoException;
}
