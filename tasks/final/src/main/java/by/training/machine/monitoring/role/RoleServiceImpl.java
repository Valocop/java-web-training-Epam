package by.training.machine.monitoring.role;

import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.dao.DaoException;
import by.training.machine.monitoring.dao.TransactionSupport;
import by.training.machine.monitoring.dao.Transactional;
import by.training.machine.monitoring.manufacture.ManufactureDto;
import by.training.machine.monitoring.manufacture.ManufactureService;
import by.training.machine.monitoring.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Bean
@Log4j
@TransactionSupport
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private ManufactureService manufactureService;
    private RoleDao roleDao;

    @Override
    public List<RoleDto> getUserRoles(UserDto user) throws DaoException {
        return roleDao.getUserRoles(user.getId());
    }

    @Override
    @Transactional
    public void assignRoles(UserDto user, List<RoleDto> roles) throws DaoException {
        try {
            for (RoleDto role : roles) {
                roleDao.assignRole(user.getId(), role.getId());
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to assign role", e);
        }
    }

    @Override
    public void assignDefaultRoles(Long userId) throws DaoException {
        try {
            roleDao.assignDefaultRoles(userId);
        } catch (SQLException e) {
            throw new DaoException("Failed to assign default roles", e);
        }
    }

    @Override
    public List<RoleDto> findAllRoles() {
        try {
            return roleDao.findAll();
        } catch (DaoException e) {
            log.error("Failed to read all roles", e);
            return new ArrayList<>();
        }
    }

    @Override
    public boolean saveRole(RoleDto role) {
        try {
            return roleDao.save(role) != null;
        } catch (DaoException e) {
            log.error("Failed to save role", e);
            return false;
        }
    }

    @Override
    public RoleDto getRole(Long id) {
        try {
            return roleDao.getById(id);
        } catch (DaoException e) {
            log.error("Failed to read role: " + id, e);
            return null;
        }
    }

    @Override
    public boolean deleteAssignRoles(Long user) {
        try {
            return roleDao.deleteAssignRoles(user);
        } catch (DaoException e) {
            log.error("Failed to delete assign roles", e);
            return false;
        }
    }

    @Override
    public boolean updateAssignUserRole(Long userId, Long roleId) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Transactional
    @Override
    public boolean updateAssignUserRole(Long userId, String roleName) {
        try {
            Optional<RoleDto> roleDto = roleDao.findAll().stream()
                    .filter(role -> role.getRoleName().equalsIgnoreCase(roleName))
                    .findFirst();
            if (roleDto.isPresent()) {
                roleDao.deleteAssignRoles(userId);
                roleDao.assignRole(roleDto.get().getId(), userId);
                return true;
            }
            return false;
        } catch (DaoException | SQLException e) {
            log.error("Failed to update assign role " + roleName, e);
            return false;
        }
    }
}
