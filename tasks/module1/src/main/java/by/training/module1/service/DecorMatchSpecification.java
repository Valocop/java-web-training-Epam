package by.training.module1.service;

public interface DecorMatchSpecification<T> {
    boolean match(T entity);

    default DecorMatchSpecification<T> and(DecorMatchSpecification<T> other) {
        return entity -> match(entity) && other.match(entity);
    }
}
