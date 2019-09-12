package by.training.module1.service.exception;

public class ServiceException extends Exception {
    private static final long serialVersionUID = 1L;

    public ServiceException() {
        super();
    }

    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(Exception e) {
        super(e);
    }

    public ServiceException(String msg, Exception e) {
        super(msg, e);
    }
}
