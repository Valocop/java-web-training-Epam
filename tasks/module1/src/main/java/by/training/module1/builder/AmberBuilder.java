package by.training.module1.builder;

import by.training.module1.entity.Amber;
import by.training.module1.entity.Decor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class AmberBuilder extends EntityBuilder {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected Decor buildEntity(Map<String, String> params, double value, double weight, int transp) {
        double age = Double.parseDouble(params.get("age"));
        double size = Double.parseDouble(params.get("size"));
        Amber amber = new Amber(value, weight, transp, age, size);
        LOGGER.info("Build entity Amber [" + amber.toString() + "]");
        return amber;
    }
}
