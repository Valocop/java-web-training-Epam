package by.training.machine.monitoring.user;

import by.training.machine.monitoring.role.RoleDto;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UserRoleDto {
    private Long id;
    private String login;
    private String password;
    private String email;
    private String name;
    private String address;
    private String tel;
    private byte[] picture;
    private List<RoleDto> roleDtoList;
}
