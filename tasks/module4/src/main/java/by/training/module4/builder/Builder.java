package by.training.module4.builder;

import java.util.ArrayList;
import java.util.List;

public abstract class Builder<T> {
    protected List<T> items = new ArrayList<>();

    public List<T> getItems() {
        return items;
    }

    public abstract void buildList(String fileNme) throws BuilderException;
}
