package by.training.module3.repo;

import java.util.List;

public interface Repository<T> {
    long create(T model);
    void update(T model);
    void remove(T model);
    List<T> getAll();
    T read(long id);
    List<T> find(FindSpecification<T> spec);
}
