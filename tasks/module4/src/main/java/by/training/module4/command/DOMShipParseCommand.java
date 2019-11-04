package by.training.module4.command;

import by.training.module4.builder.Builder;
import by.training.module4.builder.BuilderException;
import by.training.module4.entity.Ship;

import java.util.List;

public class DOMShipParseCommand implements Command<Ship> {
    private Builder<Ship> builder;

    public DOMShipParseCommand(Builder<Ship> builder) {
        this.builder = builder;
    }

    @Override
    public List<Ship> execute(String path) throws BuilderException {
        builder.buildList(path);
        return builder.getItems();
    }
}
