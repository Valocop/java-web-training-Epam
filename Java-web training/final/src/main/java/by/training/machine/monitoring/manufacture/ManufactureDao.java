package by.training.machine.monitoring.manufacture;

import by.training.machine.monitoring.dao.CRUDDao;
import by.training.machine.monitoring.dao.DaoException;
import by.training.machine.monitoring.dao.DaoSqlException;

import java.util.Optional;

public interface ManufactureDao extends CRUDDao<ManufactureDto, Long> {
    Optional<ManufactureDto> getByUserId(Long userId) throws DaoSqlException, DaoException;
}
