package by.training.module2.service;

import by.training.module2.model.ModelType;

import java.util.Comparator;

public interface SortSpecification<T> {
    Comparator<T> getSort();
    ModelType getModelType();
}
