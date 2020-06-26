package by.training.module3.command;

import by.training.module3.builder.BuilderException;

import java.util.List;

public interface Command<T> {
    List<T> execute(String path) throws BuilderException;
}
