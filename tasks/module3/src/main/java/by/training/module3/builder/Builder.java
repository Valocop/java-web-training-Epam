package by.training.module3.builder;

import by.training.module3.entity.Medicine;

import java.util.ArrayList;
import java.util.List;

public abstract class Builder {
    protected List<Medicine> medicines = new ArrayList<>();

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public abstract void buildListMedicines(String fileNme) throws BuilderException;
}
