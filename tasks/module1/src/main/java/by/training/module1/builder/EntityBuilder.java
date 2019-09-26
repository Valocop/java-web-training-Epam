package by.training.module1.builder;

import by.training.module1.entity.Decor;

import java.util.Map;

public abstract class EntityBuilder implements Builder {

    @Override
    public Decor build(Map<String, String> params) {
        double value = Double.parseDouble(params.get("value"));
        double weight = Double.parseDouble(params.get("weight"));
        int transparency = Integer.parseInt(params.get("transparency"));
        return buildEntity(params, value, weight, transparency);
    }

    protected abstract Decor buildEntity(Map<String, String> params, double value, double weight, int transp);
}
