package by.training.module2.service;

public interface MatchSpecification<T> {
    boolean match(T model);
}
