package by.training.module1.builder;

import by.training.module1.entity.Amber;
import by.training.module1.entity.Decor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class AmberBuilder extends Builder {
    private static final Logger LOGGER = LogManager.getLogger();
    private Map<String, String> param;

    public AmberBuilder(Map<String, String> param) {
        super(param);
        this.param = param;
    }

    @Override
    public Decor build() {
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

        Amber amber = new Amber(getValue(), getWeight(), getTransparency(), age, size);
        LOGGER.info("Build entity Amber [" + amber.toString() + "]");
        return amber;
    }
}
