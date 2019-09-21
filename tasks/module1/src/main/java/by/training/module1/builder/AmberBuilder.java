package by.training.module1.builder;

import by.training.module1.entity.Amber;
import by.training.module1.entity.Decor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class AmberBuilder implements Builder {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Decor build(Map<String, String> param) {
        double value = Double.parseDouble(param.get("value"));
        double weight = Double.parseDouble(param.get("weight"));
        int transparency = Integer.parseInt(param.get("transparency"));
        double age = 0;
        double size = 0;

        try {
            age = Double.parseDouble(param.get("age"));
            LOGGER.info("Set value of age [" + age + "]");
        } catch (NumberFormatException e) {
            LOGGER.info("Value of age incorrect.");
            LOGGER.info("Set age default value [" + age + "]");
        } catch (NullPointerException e) {
            LOGGER.info("[" + param.toString() + "] don't contain an age.");
            LOGGER.info("Set age default value [" + age + "]");
        }

        try {
            size = Double.parseDouble(param.get("size"));
            LOGGER.info("Set value of size [" + size + "]");
        } catch (NumberFormatException e) {
            LOGGER.info("Value of size incorrect.");
            LOGGER.info("Set size default value [" + size + "]");
        } catch (NullPointerException e) {
            LOGGER.info("[" + param.toString() + "] don't contain a size.");
            LOGGER.info("Set size default value [" + size + "]");
        }

        Amber amber = new Amber(value, weight, transparency, age, size);
        LOGGER.info("Build entity Amber [" + amber.toString() + "]");
        return amber;
    }
}
