package by.training.module4.command;

import by.training.module4.builder.BuilderException;

import java.util.List;

public interface Command<T> {
    List<T> execute(String path) throws BuilderException;
}
