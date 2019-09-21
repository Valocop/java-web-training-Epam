package by.training.module1.service;

import by.training.module1.entity.Decor;

import java.util.Comparator;

public class SortByWeightSpec implements SortSpecification<Decor> {
    private SortType sortType;

    public SortByWeightSpec(SortType sortType) {
        this.sortType = sortType;
    }

    @Override
    public Comparator<Decor> getSort() {
        Comparator<Decor> decorComparator = new Comparator<Decor>() {
            @Override
            public int compare(Decor o1, Decor o2) {
                return Double.compare(o1.getWeight(), o2.getWeight());
            }
        };

        switch (sortType) {
            case INCREASE:
                return decorComparator;
            case DECREASE:
                return decorComparator.reversed();
                default:
                    return decorComparator;
        }
    }
}
