package by.training.module1.service;

import java.util.Comparator;

public interface DecorSortSpecification<T> {
    Comparator<T> getSort();
}
