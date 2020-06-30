package by.training.module1.builder;

import by.training.module1.entity.Decor;
import by.training.module1.entity.Pearl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class PearlBuilder extends EntityBuilder {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected Decor buildEntity(Map<String, String> params, double value, double weight, int transp) {
        int color = Integer.parseInt(params.get("color"));
        double height = Double.parseDouble(params.get("height"));
        Pearl pearl = new Pearl(value, weight, transp, color, height);
        LOGGER.info("Build entity Pearl [" + pearl.toString() + "]");
        return pearl;
    }
}
