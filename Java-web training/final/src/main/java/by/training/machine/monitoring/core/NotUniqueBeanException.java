package by.training.machine.monitoring.core;

public class NotUniqueBeanException extends BeanRegistrationException {

    public NotUniqueBeanException(String message) {
        super(message);
    }

    public NotUniqueBeanException(String message, Throwable cause) {
        super(message, cause);
    }
}
