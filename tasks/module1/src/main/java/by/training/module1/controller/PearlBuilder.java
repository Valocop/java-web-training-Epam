package by.training.module1.controller;

import by.training.module1.entity.Decor;
import by.training.module1.entity.Pearl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class PearlBuilder implements Builder {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Decor build(Map<String, String> param) {
        double value = Double.parseDouble(param.get("value"));
        double weight = Double.parseDouble(param.get("weight"));
        int transparency = Integer.parseInt(param.get("transparency"));
        int color = 0;
        double height = 0;

        try {
            color = Integer.parseInt(param.get("color"));
            LOGGER.info("Set value of color [" + color + "]");
        } catch (NumberFormatException e) {
            LOGGER.info("Value of color incorrect.");
            LOGGER.info("Set color default value [" + color + "]");
        } catch (NullPointerException e) {
            LOGGER.info("[" + param.toString() + "] don't contain a color.");
            LOGGER.info("Set color default value [" + color + "]");
        }

        try {
            height = Double.parseDouble(param.get("height"));
            LOGGER.info("Set value of height [" + height + "]");
        } catch (NumberFormatException e) {
            LOGGER.info("Value of height incorrect.");
            LOGGER.info("Set height default value [" + height + "]");
        } catch (NullPointerException e) {
            LOGGER.info("[" + param.toString() + "] don't contain a height.");
            LOGGER.info("Set height default value [" + height + "]");
        }

        Pearl pearl = new Pearl(value, weight, transparency, color, height);
        LOGGER.info("Build entity Pearl [" + pearl.toString() + "]");
        return pearl;
    }
}
