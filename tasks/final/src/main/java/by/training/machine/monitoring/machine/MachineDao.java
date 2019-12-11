package by.training.machine.monitoring.machine;

import by.training.machine.monitoring.dao.CRUDDao;
import by.training.machine.monitoring.dao.DaoException;

import java.util.List;

public interface MachineDao extends CRUDDao<MachineDto, Long> {
    List<MachineDto> getByManufacture(Long manufactureId) throws DaoException;
    boolean delByManufactureAndMachine(Long manufactureId, Long machineId) throws DaoException;
    boolean delAssignUserMachine(Long machineId) throws DaoException;
}
