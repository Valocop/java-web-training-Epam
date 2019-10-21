package by.training.module3.builder;

import java.util.Optional;
import java.util.stream.Stream;

public enum MedicineEnum {
    MEDICINES("medicines"),
    MEDICINE("medicine"),
    MED_ID("med-id"),
    MED_NAME("med-name"),
    MED_TYPE("med-type"),
    MED_VERSION("med-version"),
    ANALOGS("analogs"),
    ANALOG("analog"),
    PHARMS("pharms"),
    PHARM("pharm"),
    PHARM_ID("pharm-id"),
    PHARM_NAME("pharm-name"),
    CERTIFICATE("certificate"),
    NUMBER("number"),
    ISSUE_DATE("issue-date"),
    SHELF_DATE("shelf-date"),
    REGISTR_ORGANIZATION("registr-organization"),
    PACKAGE("package"),
    PACKAGE_TYPE("package-type"),
    COUNT("count"),
    VALUE("value"),
    DOSAGE("dosage"),
    DOSA("dosa"),
    FREQUENCY("frequency");

    private String value;
    private MedicineEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static MedicineEnum getByString(String value) {
        Optional<MedicineEnum> optional = Stream.of(MedicineEnum.values())
                .filter(medicineEnum -> medicineEnum.getValue().equalsIgnoreCase(value))
                .findFirst();
        return optional.orElse(null);
    }
}
