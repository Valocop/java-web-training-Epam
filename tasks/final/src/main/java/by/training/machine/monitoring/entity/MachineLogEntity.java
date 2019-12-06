package by.training.machine.monitoring.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MachineLogEntity {
    private Long id;
    private Date date;
    private Double fuelLevel;
    private Double oilPressure;
    private Double oilLevel;
    private Double coolantTemp;
    private Long machineId;
}
