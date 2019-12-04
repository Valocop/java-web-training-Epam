package by.training.machine.monitoring.machine;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MachineDto {
    private long id;
    private String model;
    private Double price;

}
