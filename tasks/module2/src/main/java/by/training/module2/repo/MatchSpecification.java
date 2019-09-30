package by.training.module2.repo;

public interface MatchSpecification<T> {
    boolean match(T model);
}
