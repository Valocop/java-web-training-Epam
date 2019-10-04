package by.training.module2.service;

import java.util.List;

public interface Service<T> {
    void add(T model);
    void remove(T model);
    List<T> sort(SortSpecification<T> spec);
    List<T> find(MatchSpecification<T> spec);
}
