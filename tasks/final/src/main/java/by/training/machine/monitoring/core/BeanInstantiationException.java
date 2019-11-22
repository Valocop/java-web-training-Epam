package by.training.machine.monitoring.core;

public class BeanInstantiationException extends BeanRegistrationException {

    public BeanInstantiationException(String message) {
        super(message);
    }

    public BeanInstantiationException(String message, Throwable cause) {
        super(message, cause);
    }
}
