package by.training.module1.builder;

import by.training.module1.entity.Decor;

import java.util.Map;

public abstract class Builder {
    private Map<String, String> param;

    public Builder(Map<String, String> param){
        this.param = param;
    }

    protected double getValue() {
        return Double.parseDouble(param.get("value"));
    }

    protected double getWeight() {
        return Double.parseDouble(param.get("weight"));
    }

    protected int getTransparency() {
        return Integer.parseInt(param.get("transparency"));
    }

    abstract public Decor build();
}
