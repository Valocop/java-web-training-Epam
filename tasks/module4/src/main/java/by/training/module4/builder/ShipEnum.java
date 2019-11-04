package by.training.module4.builder;

import java.util.Optional;
import java.util.stream.Stream;

public enum ShipEnum {
    SHIP("ship"),
    SHIP_ID("ship-id"),
    SHIP_NAME("ship-name"),
    CAPACITY("capacity"),
    CONTAINERS("containers"),
    CONTAINER("container"),
    CONTAINER_ID("container-id"),
    CONTAINER_NAME("container-name");

    private String value;

    private ShipEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ShipEnum getByString(String value) {
        Optional<ShipEnum> optional = Stream.of(ShipEnum.values())
                .filter(medicineEnum -> medicineEnum.getValue().equalsIgnoreCase(value))
                .findFirst();
        return optional.orElse(null);
    }
}
