package by.training.module1.service;

import java.util.List;

public interface DecorRepository<T> {
    boolean add(T decor);
    boolean remove(T decor);
    Double calculate(DecorCalculateSpecification<T> spec) throws ServiceException;
    List<T> find(DecorMatchSpecification<T> spec) throws ServiceException;
    List<T> sort(DecorSortSpecification<T> spec) throws ServiceException;
    List<T> getAll();
}
