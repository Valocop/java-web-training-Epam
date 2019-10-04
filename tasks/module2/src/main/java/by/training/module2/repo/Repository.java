package by.training.module2.repo;

import by.training.module2.model.ModelType;

import java.util.List;

public interface Repository<T> {
    void add(T model);
    void remove(T model);
    List<T> getByType(ModelType type);
    List<T> getAll();
}
