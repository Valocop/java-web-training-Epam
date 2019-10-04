package by.training.module2.model;

import by.training.module2.composite.ModelComposite;
import by.training.module2.composite.ModelLeaf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class ParagraphComposite implements ModelComposite {
    private static final Logger LOG = LogManager.getLogger();
    private final List<ModelLeaf> sentences = new LinkedList<>();

    @Override
    public void addLeaf(ModelLeaf modelLeaf) {
        sentences.add(modelLeaf);
    }

    @Override
    public List<ModelLeaf> getLeafes() {
        return sentences;
    }

    @Override
    public void removeLeaf(ModelLeaf modelLeaf) {
        sentences.remove(modelLeaf);
    }

    @Override
    public int getCountOfLeaf() {
        return sentences.size();
    }

    public List<ModelLeaf> getSentences() {
        return sentences;
    }

    @Override
    public String toString() {
        StringBuilder paragraphBuilder = new StringBuilder("\t");
        sentences.forEach(textLeaf -> paragraphBuilder.append(textLeaf.toString()));
        LOG.info("Paragraph is build.");
        return paragraphBuilder.toString();
    }

    @Override
    public ModelType getModelType() {
        return ModelType.PARAGRAPH;
    }
}
