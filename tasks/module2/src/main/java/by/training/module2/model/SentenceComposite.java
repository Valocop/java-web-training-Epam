package by.training.module2.model;

import by.training.module2.composite.ModelComposite;
import by.training.module2.composite.ModelLeaf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class SentenceComposite implements ModelComposite {
    private static final Logger LOG = LogManager.getLogger();
    private final List<ModelLeaf> words = new LinkedList<>();

    @Override
    public void addLeaf(ModelLeaf modelLeaf) {
        words.add(modelLeaf);
    }

    @Override
    public List<ModelLeaf> getLeafes() {
        return words;
    }

    @Override
    public void removeLeaf(ModelLeaf modelLeaf) {
        words.remove(modelLeaf);
    }

    @Override
    public int getCountOfLeaf() {
        return words.size();
    }

    public List<ModelLeaf> getWords() {
        return words;
    }

    @Override
    public String toString() {
        StringBuilder sentenceBuild = new StringBuilder();
        words.forEach(textLeaf -> sentenceBuild.append(textLeaf.toString()));
        sentenceBuild.append(System.lineSeparator());
        LOG.info("Sentence is build.");
        return sentenceBuild.toString();
    }

    @Override
    public ModelType getModelType() {
        return ModelType.SENTENCE;
    }
}
