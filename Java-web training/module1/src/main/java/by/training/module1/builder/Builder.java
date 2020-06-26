package by.training.module1.builder;

import by.training.module1.entity.Decor;

import java.util.Map;

public interface Builder {
    Decor build(Map<String, String> params);
}
