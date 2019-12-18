package by.training.machine.monitoring.manufacture;

import java.util.Optional;

public interface ManufactureService {
    boolean saveManufacture(ManufactureDto modelDto);
    Optional<ManufactureDto> getManufacture(Long Id);
    Optional<ManufactureDto> getManufactureByUserId(Long userId);
    boolean deleteManufactureByUserId(Long userId);
    boolean deleteManufacture(ManufactureDto manufactureDto);
}
