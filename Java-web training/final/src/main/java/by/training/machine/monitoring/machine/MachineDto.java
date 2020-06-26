package by.training.machine.monitoring.machine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MachineDto {
    private Long id;
    private String uniqNumber;
    private Long modelId;
    private Long characteristicId;
    private Long manufactureId;
}
