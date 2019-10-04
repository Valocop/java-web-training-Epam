package by.training.module2.service;

import by.training.module2.composite.ModelComposite;
import by.training.module2.composite.ModelLeaf;
import by.training.module2.model.ModelType;

import java.util.Comparator;

public class ParagraphSortSpec implements SortSpecification<ModelLeaf> {
    private final ModelType TYPE = ModelType.PARAGRAPH;

    @Override
    public Comparator<ModelLeaf> getSort() {
        return Comparator.comparingInt(o -> ((ModelComposite) o).getCountOfLeaf());
    }

    @Override
    public ModelType getModelType() {
        return TYPE;
    }
}
