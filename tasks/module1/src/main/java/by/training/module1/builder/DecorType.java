package by.training.module1.builder;

import java.util.Optional;
import java.util.stream.Stream;

public enum DecorType {
    AMBER,
    PEARL;

    public static Optional<DecorType> fromString(String type) {

        return Stream.of(DecorType.values())
                .filter(t -> t.name().equalsIgnoreCase(type))
                .findFirst();
    }
}
