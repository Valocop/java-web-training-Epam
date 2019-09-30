package by.training.module2.composite;

import java.util.List;

public interface TextComposite extends TextLeaf {
    void addText(TextLeaf textLeaf);
    List<TextLeaf> getTexts();
}
