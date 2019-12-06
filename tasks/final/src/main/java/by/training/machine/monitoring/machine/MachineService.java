package by.training.machine.monitoring.machine;

import java.util.List;

public interface MachineService {
    boolean saveMachine(MachineDto machineDto);
    List<MachineInfoDto> getMachineInfo(Long manufactureId);
}
