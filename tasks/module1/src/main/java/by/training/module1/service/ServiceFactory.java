package by.training.module1.service;

public final class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private final GemstoneService gemstoneService = new GemstoneServiceImpl();
    private final NecklaceService necklaceService = new NecklaceServiceImpl();

    private ServiceFactory() {}

    public static ServiceFactory getInstance() {
        return instance;
    }

    public GemstoneService getGemstoneService() {
        return gemstoneService;
    }

    public NecklaceService getNecklaceService() {
        return necklaceService;
    }
}
