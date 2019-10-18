package by.training.module3.service;

import by.training.module3.entity.Medicine;
import by.training.module3.repo.FindSpecification;

import java.util.Comparator;
import java.util.List;

public interface Service<T> {
    long add(T model);
    void update(T model);
    void remove(T model);
    List<T> sort(Comparator<Medicine> comparator);
    List<T> find(FindSpecification<T> spec);
    T getById(long id);
    List<T> getAll();
}
