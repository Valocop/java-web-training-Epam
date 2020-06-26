package by.training.module1.repo;

import java.util.List;

public interface Repository<T> {
    boolean add(T entity);
    boolean remove(T entity);
    List<T> find(MatchSpecification<T> spec);
    List<T> getAll();
}
