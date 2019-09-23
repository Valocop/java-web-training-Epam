package by.training.module1.builder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public abstract class BuilderFactory {
    private static final Logger LOGGER = LogManager.getLogger();

    public static Builder getBuilder(DecorType type, Map<String, String > param) {

        switch (type) {
            case AMBER:
                LOGGER.info("AmberBuilder was created.");
                return new AmberBuilder(param);
            case PEARL:
                LOGGER.info("PearlBuilder was created.");
                return new PearlBuilder(param);
                default:
                    LOGGER.info("Builder didn't created.");
                    return null;
        }
    }

}
