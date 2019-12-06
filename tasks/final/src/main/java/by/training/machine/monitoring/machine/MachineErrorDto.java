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
public class MachineErrorDto {
    private Long id;
    private Date date;
    private String errorCode;
    private Long machineId;
}
