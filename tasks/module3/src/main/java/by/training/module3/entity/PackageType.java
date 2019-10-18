package by.training.module3.entity;

import java.util.Optional;
import java.util.stream.Stream;

public enum PackageType {
    BOX,
    BANK,
    BOTTLE,
    PLATE,
    SACHET;

    public static PackageType getByString(String value) {
        Optional<PackageType> optional = Stream.of(PackageType.values())
                .filter(packageType -> packageType.name().equalsIgnoreCase(value))
                .findFirst();
        return optional.orElse(null);
    }
}
