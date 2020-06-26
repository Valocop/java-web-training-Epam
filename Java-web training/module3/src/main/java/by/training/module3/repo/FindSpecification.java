package by.training.module3.repo;

public interface FindSpecification<T> {
    boolean match(T model);
}
