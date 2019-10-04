package by.training.module2.model;

import java.util.Optional;
import java.util.stream.Stream;

public enum ModelType {
    WORD,
    SENTENCE,
    PARAGRAPH,
    TEXT;

    public static Optional<ModelType> fromString(String type) {

        return Stream.of(ModelType.values())
                .filter(t -> t.name().equalsIgnoreCase(type))
                .findFirst();
    }
}
