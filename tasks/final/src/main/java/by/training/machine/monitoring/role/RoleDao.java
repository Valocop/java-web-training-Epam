package by.training.machine.monitoring.role;

import by.training.machine.monitoring.dao.CRUDDao;
import by.training.machine.monitoring.dao.DaoException;

import java.sql.SQLException;
import java.util.List;

public interface RoleDao extends CRUDDao<RoleDto, Long> {
    void assignDefaultRoles(Long userId) throws SQLException, DaoException;
    void assignRole(Long roleId, Long userId) throws SQLException, DaoException;
    List<RoleDto> getUserRoles(Long userId) throws DaoException;
    boolean deleteAssignRoles(Long userId) throws DaoException;
}
