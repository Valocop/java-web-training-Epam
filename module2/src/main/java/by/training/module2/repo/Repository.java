package by.training.module2.repo;

import by.training.module2.entity.EntityType;

import java.util.List;

public interface Repository<T> {
    long create(T model);
    void update(T model);
    void remove(T model);
    List<T> getAll(EntityType type);
    T read(long id, EntityType type);
    List<T> find(FindSpecification<T> spec, EntityType type);
}
