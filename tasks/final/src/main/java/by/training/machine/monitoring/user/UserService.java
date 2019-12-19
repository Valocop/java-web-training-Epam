package by.training.machine.monitoring.user;


import by.training.machine.monitoring.machine.MachineInfoDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean loginUser(UserDto userDto);
    boolean registerUser(UserDto userDto);
    boolean deleteUser(UserDto userDto);
    List<UserDto> getAllUsers();
    UserDto getByLogin(String login);
    List<UserRoleDto> getAllUsersWithRoles();
    boolean updateUser(UserDto userDto);
    boolean updateUser(UserDto userDto, String roleName);
    List<UserDto> getUsersByMachineId(Long machineId);
    Optional<UserDto> getUserById(Long userId);
    boolean registerMachine(Long userId, String uniqNumber);
    List<MachineInfoDto> getAssignMachines(Long userId);
    boolean deleteAssignUserMachine(Long userId, Long machineId);
}
