package by.training.module2.model;

import by.training.module2.composite.ModelLeaf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WordLeaf implements ModelLeaf {
    private static final Logger LOG = LogManager.getLogger();
    private String word;

    public WordLeaf(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    @Override
    public String toString() {
        LOG.info("Word is build.");
        return word + " ";
    }

    @Override
    public ModelType getModelType() {
        return ModelType.WORD;
    }
}
