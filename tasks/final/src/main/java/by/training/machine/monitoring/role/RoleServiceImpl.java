package by.training.machine.monitoring.role;

import by.training.machine.monitoring.app.ApplicationConstant;
import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.dao.DaoException;
import by.training.machine.monitoring.dao.TransactionSupport;
import by.training.machine.monitoring.dao.Transactional;
import by.training.machine.monitoring.manufacture.ManufactureDao;
import by.training.machine.monitoring.manufacture.ManufactureDto;
import by.training.machine.monitoring.service.ServiceException;
import by.training.machine.monitoring.user.UserDao;
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
    private RoleDao roleDao;
    private UserDao userDao;
    private ManufactureDao manufactureDao;

    @Override
    public List<RoleDto> getUserRoles(UserDto user) {
        try {
            return roleDao.getUserRoles(user.getId());
        } catch (DaoException e) {
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public void assignRoles(UserDto user, List<RoleDto> roles) throws ServiceException {
        try {
            for (RoleDto role : roles) {
                roleDao.assignRole(user.getId(), role.getId());
            }
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Failed to assign role", e);
        }
    }

    @Override
    public void assignDefaultRoles(Long userId) throws ServiceException {
        try {
            roleDao.assignDefaultRoles(userId);
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Failed to assign default roles", e);
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
            Optional<RoleDto> roleDto = roleDao.findAll()
                    .stream()
                    .filter(role -> role.getRoleName().equalsIgnoreCase(roleName))
                    .findFirst();
            if (roleDto.isPresent()) {
                roleDao.deleteAssignRoles(userId);
                roleDao.assignRole(roleDto.get().getId(), userId);
                if (roleName.trim().equalsIgnoreCase(ApplicationConstant.MANUFACTURER_ROLE)) {
                    saveManufacture(userId);
                }
                return true;
            }
            return false;
        } catch (DaoException | SQLException e) {
            log.error("Failed to update assign role " + roleName, e);
            return false;
        }
    }

    private boolean saveManufacture(Long userId) {
        try {
            UserDto userDto = userDao.getById(userId);
            if (userDto != null) {
                Optional<ManufactureDto> manufactureOptional = manufactureDao.getByUserId(userDto.getId());
                if (!manufactureOptional.isPresent()) {
                    ManufactureDto manufactureDto = ManufactureDto.builder()
                            .userId(userId)
                            .name(userDto.getName())
                            .build();
                    manufactureDao.save(manufactureDto);
                }
                return true;
            } else {
                return false;
            }
        } catch (DaoException e) {
            log.error("Failed to save manufacture");
            return false;
        }
    }
}
