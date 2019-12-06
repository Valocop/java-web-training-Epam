package by.training.machine.monitoring.model;

import by.training.machine.monitoring.dao.CRUDDao;
import by.training.machine.monitoring.dao.DaoException;

import java.util.List;

public interface ModelDao extends CRUDDao<ModelDto, Long> {
    List<ModelDto> getModelByManufactureId(Long manufactureId) throws DaoException;
}
