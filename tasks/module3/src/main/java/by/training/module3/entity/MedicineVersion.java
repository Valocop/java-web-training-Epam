package by.training.module3.entity;

import java.util.Optional;
import java.util.stream.Stream;

public enum MedicineVersion {
    PILL,
    CAPSULE,
    POWDER,
    DROPS,
    SYRUP;

    public static MedicineVersion getByString(String value) {
        Optional<MedicineVersion> optional = Stream.of(MedicineVersion.values())
                .filter(medicineVersion -> medicineVersion.name().equalsIgnoreCase(value))
                .findFirst();
        return optional.orElse(null);
    }
}
