package by.training.module3.command;

import by.training.module3.builder.Builder;
import by.training.module3.builder.BuilderException;
import by.training.module3.entity.Medicine;

import java.util.List;

public class StAXMedicineParseCommand implements Command<Medicine> {
    private Builder<Medicine> builder;

    public StAXMedicineParseCommand(Builder<Medicine> builder) {
        this.builder = builder;
    }

    @Override
    public List<Medicine> execute(String path) throws BuilderException {
        builder.buildList(path);
        return builder.getMedicines();
    }
}
