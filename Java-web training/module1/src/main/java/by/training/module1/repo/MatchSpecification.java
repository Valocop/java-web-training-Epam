package by.training.module1.repo;

public interface MatchSpecification<T> {
    boolean match(T entity);

    default MatchSpecification<T> and(MatchSpecification<T> other) {
        return entity -> match(entity) && other.match(entity);
    }
}
