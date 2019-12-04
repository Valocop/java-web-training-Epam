package by.training.machine.monitoring.user;

import by.training.machine.monitoring.SecurityService;
import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.dao.DaoException;
import by.training.machine.monitoring.dao.TransactionSupport;
import by.training.machine.monitoring.dao.Transactional;
import by.training.machine.monitoring.role.RoleService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Bean
@Log4j
@AllArgsConstructor
@TransactionSupport
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private RoleService roleService;

    @Override
    public boolean loginUser(UserDto userDto) {
        Optional<UserDto> byLogin;
        try {
            byLogin = userDao.findByLogin(userDto.getLogin());
        } catch (DaoException e) {
            log.error("Failed to read user", e);
            byLogin = Optional.empty();
        }
        return byLogin.filter(dto -> dto.getPassword().equals(DigestUtils.md5Hex(userDto.getPassword()))).isPresent();
    }

    @Override
    @Transactional
    public boolean registerUser(UserDto userDto) throws DaoException {
        Optional<UserDto> findUser = Optional.empty();
        findUser = userDao.findByLogin(userDto.getLogin());
        if (!findUser.isPresent()) {
            String securityPas = DigestUtils.md5Hex(userDto.getPassword());
            userDto.setPassword(securityPas);
            Long saved = userDao.save(userDto);
            roleService.assignDefaultRoles(saved);
            return saved > 0;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteUser(UserDto userDto) throws DaoException {
        if (roleService.deleteAssignRoles(userDto.getId())) {
            if (userDao.delete(userDto)) {
                SecurityService.getInstance().deleteSession(userDto.getId());
                return true;
            }
        }
        return false;
    }

    @Override
    public List<UserDto> getAllUsers() {
        try {
            return userDao.findAll();
        } catch (DaoException e) {
            log.error("Failed to read users", e);
            return new ArrayList<>();
        }
    }

    @Override
    public UserDto getByLogin(String login) throws DaoException {
        Optional<UserDto> findUser = Optional.empty();
        findUser = userDao.findByLogin(login);
        return findUser.orElse(null);
    }

    @Override
    @Transactional
    public List<UserRoleDto> getAllUsersWithRoles() {
        List<UserRoleDto> userRoleDtos = new ArrayList<>();
        try {
            List<UserDto> userDtos = userDao.findAll();
            for (UserDto userDto : userDtos) {
                UserRoleDto userRoleDto = UserRoleDto.builder()
                        .id(userDto.getId())
                        .login(userDto.getLogin())
                        .password(userDto.getPassword())
                        .email(userDto.getEmail())
                        .name(userDto.getName())
                        .address(userDto.getAddress())
                        .tel(userDto.getTel())
                        .picture(userDto.getPicture())
                        .roleDtoList(roleService.getUserRoles(userDto))
                        .build();
                userRoleDtos.add(userRoleDto);
            }
        } catch (DaoException e) {
            log.error("Failed to read users", e);
            return new ArrayList<>();
        }
        return userRoleDtos;
    }

    @Override
    public boolean updateUser(UserDto userDto) {
        try {
            return userDao.update(userDto);
        } catch (DaoException e) {
            log.error("Failed to update users", e);
            return false;
        }
    }

    @Transactional
    @Override
    public boolean updateUser(UserDto userDto, String roleName) throws DaoException {
        return userDao.update(userDto) && roleService.updateAssignUserRole(userDto.getId(), roleName);
    }
}
