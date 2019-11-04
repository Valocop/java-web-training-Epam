package by.training.module4.service;

import by.training.module4.entity.Ship;

import java.util.Comparator;
import java.util.List;

public interface Service<T> {
    long add(T model);
    void update(T model);
    void remove(T model);
    List<T> sort(Comparator<Ship> comparator);
    T getById(long id);
    List<T> getAll();
}
