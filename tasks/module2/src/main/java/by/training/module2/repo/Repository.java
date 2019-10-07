package by.training.module2.repo;

import by.training.module2.entity.EntityType;

import java.util.List;

public interface Repository<T> {
    long add(T model);
    void remove(T model);
    List<T> getAll(EntityType type);
    T getById(long id, EntityType type);
}
