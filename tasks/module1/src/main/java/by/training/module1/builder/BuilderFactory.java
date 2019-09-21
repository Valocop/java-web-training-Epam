package by.training.module1.builder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BuilderFactory {
    private static final Logger LOGGER = LogManager.getLogger();

    public static Builder getBuilder(String type) {
        Builder builder = null;

        if (type.equalsIgnoreCase("amber")) {
            builder = new AmberBuilder();
            LOGGER.info("AmberBuilder was created by type [" + type + "]");
        } else {
            if (type.equalsIgnoreCase("pearl")) {
                builder = new PearlBuilder();
                LOGGER.info("PearlBuilder was created by type [" + type + "]");
            } else {
                LOGGER.info("Impossible create Builder by type [" + type + "]");
            }
        }
        return builder;
    }

}
