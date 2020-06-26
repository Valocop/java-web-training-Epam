package by.training.machine.monitoring.machine;

import by.training.machine.monitoring.dao.CRUDDao;
import by.training.machine.monitoring.dao.DaoException;

import java.util.List;

public interface MachineLogDao extends CRUDDao<MachineLogDto, Long> {
    List<MachineLogDto> getMachineLogByMachineId(Long machineId) throws DaoException;
    boolean delByMachineId(Long machineId) throws DaoException;
}