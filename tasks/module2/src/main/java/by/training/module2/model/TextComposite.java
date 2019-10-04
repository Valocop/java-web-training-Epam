package by.training.module2.model;

import by.training.module2.composite.ModelComposite;
import by.training.module2.composite.ModelLeaf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class TextComposite implements ModelComposite {
    private static final Logger LOG = LogManager.getLogger();
    private final List<ModelLeaf> paragraphs = new LinkedList<>();

    @Override
    public void addLeaf(ModelLeaf modelLeaf) {
        paragraphs.add(modelLeaf);
    }

    @Override
    public List<ModelLeaf> getLeafes() {
        return paragraphs;
    }

    @Override
    public void removeLeaf(ModelLeaf modelLeaf) {
        paragraphs.remove(modelLeaf);
    }

    @Override
    public int getCountOfLeaf() {
        return paragraphs.size();
    }

    public List<ModelLeaf> getParagraphs() {
        return paragraphs;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        paragraphs.forEach(modelLeaf -> builder.append(modelLeaf.toString()));
        LOG.info("Text is build.");
        return builder.toString();
    }

    @Override
    public ModelType getModelType() {
        return ModelType.TEXT;
    }
}
