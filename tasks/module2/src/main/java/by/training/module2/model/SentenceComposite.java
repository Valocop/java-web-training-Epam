package by.training.module2.model;

import by.training.module2.composite.TextComposite;
import by.training.module2.composite.TextLeaf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class SentenceComposite implements TextComposite {
    private static final Logger LOG = LogManager.getLogger();
    private final List<TextLeaf> words = new LinkedList<>();

    @Override
    public void addText(TextLeaf textLeaf) {
        this.words.add(textLeaf);
    }

    @Override
    public List<TextLeaf> getTexts() {
        return this.words;
    }

    @Override
    public String getText() {
        StringBuilder sentenceBuild = new StringBuilder();
        words.forEach(textLeaf -> sentenceBuild.append(textLeaf.getText()));
        LOG.info(sentenceBuild.toString());
        return sentenceBuild.toString();
    }
}
