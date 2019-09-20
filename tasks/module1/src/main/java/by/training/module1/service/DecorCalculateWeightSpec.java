package by.training.module1.service;

import by.training.module1.entity.Decor;

public class DecorCalculateWeightSpec implements DecorCalculateSpecification<Decor> {

    @Override
    public Double getValue(Decor entity) {
        return entity.getWeight();
    }
}
