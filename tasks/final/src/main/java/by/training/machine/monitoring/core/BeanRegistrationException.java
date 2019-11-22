package by.training.machine.monitoring.core;

public class BeanRegistrationException extends RuntimeException {

    public BeanRegistrationException(String message) {
        super(message);
    }

    public BeanRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
