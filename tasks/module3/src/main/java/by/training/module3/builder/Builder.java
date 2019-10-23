package by.training.module3.builder;

import java.util.ArrayList;
import java.util.List;

public abstract class Builder<T> {
    protected List<T> medicines = new ArrayList<>();

    public List<T> getMedicines() {
        return medicines;
    }

    public abstract void buildList(String fileNme) throws BuilderException;
}
