package by.training.machine.monitoring.core;

public class MissedAnnotationException extends BeanRegistrationException {

    public MissedAnnotationException(String message) {
        super(message);
    }

    public MissedAnnotationException(String message, Throwable cause) {
        super(message, cause);
    }
}
