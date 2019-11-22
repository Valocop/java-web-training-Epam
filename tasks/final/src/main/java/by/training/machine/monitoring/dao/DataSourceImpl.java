package by.training.machine.monitoring.dao;

import by.training.machine.monitoring.core.Bean;
import lombok.extern.log4j.Log4j;

import java.sql.Connection;
import java.sql.SQLException;


@Bean
@Log4j
public class DataSourceImpl implements DataSource {
    private Connection connection;

    @Override
    public Connection getConnection() throws DaoException {
        connection = PoolConnection.getInstance().getConnection();
        return connection;
    }

    @Override
    public void close() throws DaoException {
        if (connection == null) {
            throw new DaoException(new NullPointerException("Connection is null"));
        }
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
    }
}
