package by.training.module2.repo;

import java.util.List;

public interface Repository<T> {
    boolean add(T model);
    boolean remove(T model);
    List<T> find(MatchSpecification<T> spec);
    List<T> getAll(GetSpecification spec);
}
