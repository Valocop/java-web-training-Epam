package by.training.machine.monitoring.machine;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface MachineService {
    boolean saveMachine(MachineDto machineDto);
    List<MachineInfoDto> getMachineInfo(Long manufactureId);
    boolean delMachineByManufacture(Long machineId, Long manufactureId);
    Optional<MachineInfoDto> getMachineInfoByMachineId(Long machineId);
}