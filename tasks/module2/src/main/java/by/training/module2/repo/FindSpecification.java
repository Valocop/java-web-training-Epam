package by.training.module2.repo;

public interface FindSpecification<T> {
    boolean match(T model);
}
