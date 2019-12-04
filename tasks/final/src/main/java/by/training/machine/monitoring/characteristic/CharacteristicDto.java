package by.training.machine.monitoring.characteristic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CharacteristicDto {
    private Long id;
    private Double price;
    private String power;
    private String fuelType;
    private String engineVolume;
    private String transmission;
    private Long manufactureId;
}
