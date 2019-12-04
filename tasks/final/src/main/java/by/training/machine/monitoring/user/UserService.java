package by.training.machine.monitoring.user;


import by.training.machine.monitoring.dao.DaoException;

import java.util.List;

public interface UserService {
    boolean loginUser(UserDto userDto);
    boolean registerUser(UserDto userDto) throws DaoException;
    boolean deleteUser(UserDto userDto) throws DaoException;
    List<UserDto> getAllUsers();
    UserDto getByLogin(String login) throws DaoException;
    List<UserRoleDto> getAllUsersWithRoles();
    boolean updateUser(UserDto userDto);
    boolean updateUser(UserDto userDto, String roleName) throws DaoException;
}
