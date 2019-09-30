package by.training.module2.service;

import java.util.Comparator;

public interface SortSpecification<T> {
    Comparator<T> getSort(SortType sortType);
}
