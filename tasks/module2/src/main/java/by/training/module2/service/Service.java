package by.training.module2.service;

import by.training.module2.entity.Entity;
import by.training.module2.entity.EntityType;

import java.util.Comparator;
import java.util.List;

public interface Service<T> {
    long add(T model);
    void remove(T model);
    List<T> sort(Comparator<Entity> comparator, EntityType type);
    T getById(long id, EntityType type);
    List<T> getAll(EntityType type);
}
