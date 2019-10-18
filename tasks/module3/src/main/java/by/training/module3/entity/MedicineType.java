package by.training.module3.entity;

import java.util.Optional;
import java.util.stream.Stream;

public enum MedicineType {
    PAIN_MEDICATION,
    ANTIBIOTIC,
    VITAMIN,
    SEDATIVE,
    VACCINE,
    PSYCHOTROPIC;

    public static MedicineType getByString(String value) {
        Optional<MedicineType> optional = Stream.of(MedicineType.values())
                .filter(medicineType -> medicineType.name().equalsIgnoreCase(value))
                .findFirst();
        return optional.orElse(null);
    }
}
