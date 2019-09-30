package by.training.module2.service;

import by.training.module2.composite.TextComposite;
import by.training.module2.composite.TextLeaf;

import java.util.Comparator;

public class ParagraphSortSpec implements SortSpecification<TextLeaf> {

    @Override
    public Comparator<TextLeaf> getSort(SortType sortType) {
        Comparator<TextLeaf> comparator = new Comparator<TextLeaf>() {
            @Override
            public int compare(TextLeaf o1, TextLeaf o2) {

            }
        }
        return null;
    }
}
