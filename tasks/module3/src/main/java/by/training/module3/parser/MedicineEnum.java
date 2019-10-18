package by.training.module3.parser;

import java.util.Optional;
import java.util.stream.Stream;

public enum MedicineEnum {
    MEDICINES("medicines"),
    MEDICINE("medicine"),
    MED_ID("medID"),
    MED_NAME("medName"),
    MED_TYPE("medType"),
    MED_VERSION("medVersion"),
    ANALOGS("analogs"),
    ANALOG("analog"),
    PHARMS("pharms"),
    PHARM("pharm"),
    PHARM_ID("pharmID"),
    PHARM_NAME("pharmName"),
    CERTIFICATE("certificate"),
    NUMBER("number"),
    ISSUE_DATE("issueDate"),
    SHELF_DATE("shelfDate"),
    REGISTR_ORGANIZATION("registrOrganization"),
    PACKAGE("package"),
    PACKAGE_TYPE("packageType"),
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
