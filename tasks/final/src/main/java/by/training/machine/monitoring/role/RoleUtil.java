package by.training.machine.monitoring.role;

import by.training.machine.monitoring.entity.UserRole;

public class RoleUtil {

    public static UserRole fromDto(RoleDto roleDto) {
        return UserRole.builder()
                .id(roleDto.getId())
                .roleName(roleDto.getRoleName())
                .defaultRole(roleDto.isSystem())
                .build();
    }

    public static RoleDto fromUserRole(UserRole userRole) {
        return RoleDto.builder()
                .id(userRole.getId())
                .roleName(userRole.getRoleName())
                .system(userRole.isDefaultRole())
                .build();
    }
}
