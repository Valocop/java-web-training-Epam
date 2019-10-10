package by.training.module2.chain;

import by.training.module2.composite.ModelLeaf;
import by.training.module2.model.WordLeaf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WordParser extends ModelParser {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public ModelLeaf parseText(String text) {
        LOG.info("Create word leaf.");
        return new WordLeaf(text);
    }
}
