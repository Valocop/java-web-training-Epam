package by.training.module1.service;

import by.training.module1.entity.Decor;
import java.util.Comparator;

public class SortByValueAndWeightSpec extends SortSpecification<Decor> {

    public SortByValueAndWeightSpec(SortType sortType) {
        super(sortType);
    }

    @Override
    public Comparator<Decor> getSort() {
        Comparator<Decor> decorComparator = Comparator.comparing(Decor::getValue).thenComparing(Decor::getWeight);
        return orderComparator(decorComparator);
    }
}
