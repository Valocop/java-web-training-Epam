package by.training.machine.monitoring.machine;

import by.training.machine.monitoring.characteristic.CharacteristicDto;
import by.training.machine.monitoring.model.ModelDto;
import by.training.machine.monitoring.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MachineInfoDto {
    private MachineDto machineDto;
    private ModelDto modelDto;
    private CharacteristicDto characteristicDto;
    private List<MachineLogDto> machineLogsList;
    private List<MachineErrorDto> machineErrorsList;
    private List<UserDto> usersList;
}
