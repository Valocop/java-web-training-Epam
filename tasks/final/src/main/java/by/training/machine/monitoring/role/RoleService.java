package by.training.machine.monitoring.role;

import by.training.machine.monitoring.dao.DaoException;
import by.training.machine.monitoring.service.ServiceException;
import by.training.machine.monitoring.user.UserDto;

import java.util.List;

public interface RoleService {
    List<RoleDto> getUserRoles(UserDto user) throws DaoException;
    void assignRoles(UserDto user, List<RoleDto> roles) throws ServiceException;
    void assignDefaultRoles(Long user) throws ServiceException;
    List<RoleDto> findAllRoles();
    boolean saveRole(RoleDto role);
    RoleDto getRole(Long id);
    boolean deleteAssignRoles(Long user);
    boolean updateAssignUserRole(Long userId, Long roleId);
    boolean updateAssignUserRole(Long userId, String roleName);
}
