package by.training.module1.service;

import java.util.Comparator;

public interface SortSpecification<T> {
    Comparator<T> getSort();
}
