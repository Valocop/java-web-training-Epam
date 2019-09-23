package by.training.module1.service;

import by.training.module1.entity.Decor;

import java.util.Comparator;

public class SortByValueSpec extends SortSpecification<Decor> {

    public SortByValueSpec(SortType sortType) {
        super(sortType);
    }

    @Override
    public Comparator<Decor> getSort() {
        Comparator<Decor> decorComparator = new Comparator<Decor>() {
            @Override
            public int compare(Decor o1, Decor o2) {
                return Double.compare(o1.getValue(), o2.getValue());
            }
        };

        return orderComparator(decorComparator);
    }
}
