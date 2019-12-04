package by.training.machine.monitoring.manufacture;

public interface ManufactureService {
    boolean saveManufacture(ManufactureDto modelDto);
    ManufactureDto getManufacture(Long userId);
}
