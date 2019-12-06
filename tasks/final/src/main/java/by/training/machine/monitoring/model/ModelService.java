package by.training.machine.monitoring.model;

import by.training.machine.monitoring.dao.DaoException;
import by.training.machine.monitoring.manufacture.ManufactureDto;

import java.util.List;
import java.util.Optional;

public interface ModelService {
    boolean saveModel(ModelDto modelDto);
    ModelDto getModel(Long modelId) throws DaoException;
    List<ModelDto> getModelByUserId(Long userId);
    boolean assignModelManufacture(Long userId, String nameUser);
    List<ModelDto> getModelByManufacture(Long manufactureId);
}
