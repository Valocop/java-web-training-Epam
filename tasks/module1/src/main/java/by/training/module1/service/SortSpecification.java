package by.training.module1.service;

import java.util.Comparator;

public abstract class SortSpecification<T> {
    private SortType sortType;

    public SortSpecification(SortType sortType) {
        this.sortType = sortType;
    }

    public abstract Comparator<T> getSort();

    protected Comparator<T> orderComparator(Comparator<T> comparator) {
        switch (sortType) {
            case INCREASE:
                return comparator;
            case DECREASE:
                return comparator.reversed();
            default:
                return comparator;
        }
    }
}
