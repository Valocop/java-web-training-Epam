package by.training.module1.service;

public final class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private final DecorService decorService = new DecorServiceImpl();

    private ServiceFactory() {}

    public static ServiceFactory getInstance() {
        return instance;
    }

    public DecorService getDecorService() {
        return decorService;
    }
}
