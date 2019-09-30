package by.training.module2.service;

import by.training.module2.repo.MatchSpecification;

import java.util.List;

public interface Service<T> {
    boolean add(T model);
    boolean remove(T model);
    List<T> sort();
    List<T> find(MatchSpecification<T> spec);
}
