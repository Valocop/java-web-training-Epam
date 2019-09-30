package by.training.module2.repo;

import by.training.module2.composite.TextLeaf;
import by.training.module2.model.ParagraphComposite;

public class GetParagraphSpec implements GetSpecification<TextLeaf> {

    @Override
    public boolean get(TextLeaf model) {
        return model.getClass() == ParagraphComposite.class;
    }
}
