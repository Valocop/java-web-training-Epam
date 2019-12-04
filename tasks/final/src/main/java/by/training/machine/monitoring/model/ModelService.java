package by.training.machine.monitoring.model;

import by.training.machine.monitoring.dao.DaoException;
import by.training.machine.monitoring.manufacture.ManufactureDto;

public interface ModelService {
    boolean saveModel(ModelDto modelDto);
    ModelDto getModel(Long userId) throws DaoException;
    boolean assignModelManufacture(Long userId, String nameUser);
    ManufactureDto getManufactureByUserId(Long userId);
}
