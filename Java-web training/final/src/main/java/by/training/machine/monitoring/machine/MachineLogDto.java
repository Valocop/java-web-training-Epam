package by.training.machine.monitoring.machine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MachineLogDto {
    private Long id;
    private Date date;
    private Double fuelLevel;
    private Double oilPressure;
    private Double oilLevel;
    private Double coolantTemp;
    private Long machineId;
}
