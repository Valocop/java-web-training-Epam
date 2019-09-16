package by.training.module1.service;

import by.training.module1.entity.Decor;
import com.sun.istack.internal.NotNull;

import java.util.Comparator;

public class DecorSortByValueAndWeightSpec implements DecorSortSpecification<Decor> {
    private SortType sortType;

    public DecorSortByValueAndWeightSpec(@NotNull SortType sortType) {
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
                    return null;
        }
    }
}
