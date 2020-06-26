package by.training.module1.service;

import by.training.module1.repo.MatchSpecification;

import java.util.List;

public interface Service<T> {
    boolean add(T entity);
    boolean remove(T entity);
    double calcDouble(CalculateDoubleSpecification<T> spec);
    List<T> sort(SortSpecification<T> spec);
    List<T> getAll();
    List<T> find(MatchSpecification<T> spec);
}
