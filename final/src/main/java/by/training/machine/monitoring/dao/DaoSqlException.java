package by.training.machine.monitoring.dao;

public class DaoSqlException extends DaoException {

    public DaoSqlException() {
        super();
    }

    public DaoSqlException(String message) {
        super(message);
    }

    public DaoSqlException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoSqlException(Throwable cause) {
        super(cause);
    }
}
