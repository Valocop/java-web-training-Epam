package by.training.machine.monitoring.machine;

import by.training.machine.monitoring.dao.CRUDDao;
import by.training.machine.monitoring.dao.DaoException;
import by.training.machine.monitoring.user.UserDto;

import java.util.List;
import java.util.Optional;

public interface MachineDao extends CRUDDao<MachineDto, Long> {
    List<MachineDto> getByManufacture(Long manufactureId) throws DaoException;
}
