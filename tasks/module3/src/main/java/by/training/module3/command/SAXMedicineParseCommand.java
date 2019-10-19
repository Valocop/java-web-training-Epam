package by.training.module3.command;

import by.training.module3.builder.Builder;
import by.training.module3.builder.BuilderException;
import by.training.module3.entity.Medicine;

import java.util.List;

public class SAXMedicineParseCommand implements Command<Medicine> {
    private Builder builder;

    public SAXMedicineParseCommand(Builder builder) {
        this.builder = builder;
    }

    @Override
    public List<Medicine> execute(String path) throws BuilderException {
        builder.buildListMedicines(path);
        return builder.getMedicines();
    }
}
