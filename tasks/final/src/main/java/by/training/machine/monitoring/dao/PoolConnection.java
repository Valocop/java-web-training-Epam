package by.training.machine.monitoring.dao;

import by.training.machine.monitoring.core.Bean;
import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

@Log4j
@Bean
public final class PoolConnection implements Cloneable, Serializable {
    private static final long serialVersionUID = -23456756432456543L;
    private static PoolConnection instance;
    private static AtomicBoolean isCreated = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();

    private final int POOL_SIZE = 10;
    private BlockingQueue<Connection> freeConnectionQueue = new ArrayBlockingQueue<>(POOL_SIZE);
    private BlockingQueue<Connection> busyConnectionQueue = new ArrayBlockingQueue<>(POOL_SIZE);

    private PoolConnection() {
        if (instance != null) {
            throw new RuntimeException("Instance was created");
        }
    }

    public static PoolConnection getInstance() {
        if (!isCreated.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new PoolConnection();
                    isCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public void init() throws DaoException {
        Properties properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(inputStream);
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException | IOException e) {
            throw new DaoException(e);
        }
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                Connection connection = DriverManager.getConnection(properties.getProperty("url"), properties);
                freeConnectionQueue.offer(connection);
            } catch (SQLException e) {
                throw new DaoSqlException(e);
            }
        }
    }

    public Connection getConnection() throws DaoException {
        Connection connection;
        try {
            connection = freeConnectionQueue.take();
            busyConnectionQueue.put(connection);
        } catch (InterruptedException e) {
            throw new DaoException(e);
        }
        return (Connection) Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{Connection.class}, (proxy, method, args) -> {
                    if (method.getName().equals("close")) {
                        releaseConnection(connection);
                        return null;
                    } else {
                        return method.invoke(connection, args);
                    }
                });
    }

    private void releaseConnection(Connection connection) throws DaoException {
        if (busyConnectionQueue.remove(connection)) {
            try {
                freeConnectionQueue.put(connection);
            } catch (InterruptedException e) {
                throw new DaoException(e);
            }
        } else {
            log.warn("Connection wasn't removed from busyQueue");
        }
    }

    public void destroyConnections() throws DaoSqlException {
        try {
            for (Connection connection : freeConnectionQueue) {
                connection.close();
            }
            for (Connection connection : busyConnectionQueue) {
                connection.close();
            }
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while ((drivers.hasMoreElements())) {
                Driver driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
            }
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
    }

    public int getFreeConnections() {
        return freeConnectionQueue.size();
    }

    public int getBusyConnections() {
        return busyConnectionQueue.size();
    }

    public int getPoolSize() {
        return POOL_SIZE;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return instance;
    }

    protected Object readResolve() {
        return instance;
    }
}
