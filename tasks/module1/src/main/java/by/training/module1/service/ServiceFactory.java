package by.training.module1.service;

public final class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private final GemstoneService gemstoneService = new GemtoneServiceImpl();

    private ServiceFactory() {}

    public static ServiceFactory getInstance() {
        return instance;
    }

    public GemstoneService getGemstoneService() {
        return gemstoneService;
    }
}
