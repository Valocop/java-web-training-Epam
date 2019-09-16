package by.training.module1.service;

import by.training.module1.entity.Decor;
import com.sun.istack.internal.NotNull;

import java.util.Comparator;

public class DecorSortByValueSpec implements DecorSortSpecification<Decor> {
    private SortType sortType;

    public DecorSortByValueSpec(@NotNull SortType sortType) {
        this.sortType = sortType;
    }

    @Override
    public Comparator<Decor> getSort() {
        Comparator<Decor> decorComparator = new Comparator<Decor>() {
            @Override
            public int compare(Decor o1, Decor o2) {
                return Double.compare(o1.getValue(), o2.getValue());
            }
        };

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
