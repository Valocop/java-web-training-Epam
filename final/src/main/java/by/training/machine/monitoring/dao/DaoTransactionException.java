package by.training.machine.monitoring.dao;

public class DaoTransactionException extends DaoException {

    public DaoTransactionException() {
        super();
    }

    public DaoTransactionException(String message) {
        super(message);
    }

    public DaoTransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoTransactionException(Throwable cause) {
        super(cause);
    }
}
