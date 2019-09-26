package by.training.module1.builder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;


public class BuilderFactory {
    private static final Logger LOGGER = LogManager.getLogger();

    public Builder getBuilder(String type) {
        Optional<DecorType> decorType = DecorType.fromString(type);

        if (!decorType.isPresent()) {
            return null;
        }

        switch (decorType.get()) {
            case AMBER:
                LOGGER.info("AmberBuilder was created.");
                return new AmberBuilder();
            case PEARL:
                LOGGER.info("PearlBuilder was created.");
                return new PearlBuilder();
                default:
                    LOGGER.info("Builder didn't created.");
                    return null;
        }
    }

}
