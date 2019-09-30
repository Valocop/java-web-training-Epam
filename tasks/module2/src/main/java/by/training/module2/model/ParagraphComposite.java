package by.training.module2.model;

import by.training.module2.composite.TextComposite;
import by.training.module2.composite.TextLeaf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class ParagraphComposite implements TextComposite {
    private static final Logger LOG = LogManager.getLogger();
    private final List<TextLeaf> sentences = new LinkedList<>();

    @Override
    public void addText(TextLeaf textLeaf) {
        sentences.add(textLeaf);
    }

    @Override
    public List<TextLeaf> getTexts() {
        return sentences;
    }

    @Override
    public String getText() {
        StringBuilder paragraphBuilder = new StringBuilder("\t");
        sentences.forEach(textLeaf -> paragraphBuilder.append(textLeaf.getText()));
        LOG.info(paragraphBuilder.toString());
        return paragraphBuilder.toString();
    }
}
