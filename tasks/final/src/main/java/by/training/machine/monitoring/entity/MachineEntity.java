package by.training.machine.monitoring.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MachineEntity {
    private Long id;
    private String uniqCode;
    private ModelEntity modelEntity;
    private CharacteristicEntity characteristicEntity;
}
