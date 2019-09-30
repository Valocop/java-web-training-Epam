package by.training.module2.model;

import by.training.module2.composite.TextLeaf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WordLeaf implements TextLeaf {
    private static final Logger LOG = LogManager.getLogger();
    private String word;
    private String ending;

    public WordLeaf(String word) {
        this.word = word;
    }

    @Override
    public String getText() {
        LOG.info(word + ending);
        return word + ending;
    }
}
