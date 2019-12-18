package by.training.machine.monitoring.characteristic;

import by.training.machine.monitoring.dao.CRUDDao;
import by.training.machine.monitoring.dao.DaoException;

import java.util.List;

public interface CharacteristicDao extends CRUDDao<CharacteristicDto, Long> {
    List<CharacteristicDto> getByManufactureId(Long manufactureId) throws DaoException;
    boolean deleteByManufactureId(Long manufactureId) throws DaoException;
}
