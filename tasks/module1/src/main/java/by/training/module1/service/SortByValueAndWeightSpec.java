package by.training.module1.service;

import by.training.module1.entity.Decor;
import java.util.Comparator;

public class SortByValueAndWeightSpec implements SortSpecification<Decor> {
    private SortType sortType;

    public SortByValueAndWeightSpec(SortType sortType) {
        this.sortType = sortType;
    }

    @Override
    public Comparator<Decor> getSort() {
        Comparator<Decor> decorComparator = Comparator.comparing(Decor::getValue).thenComparing(Decor::getWeight);
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
