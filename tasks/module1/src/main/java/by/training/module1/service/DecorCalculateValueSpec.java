package by.training.module1.service;

import by.training.module1.entity.Decor;

public class DecorCalculateValueSpec implements DecorCalculateSpecification<Decor> {

    @Override
    public Double calculate(Decor entity) {
        return entity.getValue();
    }
}
