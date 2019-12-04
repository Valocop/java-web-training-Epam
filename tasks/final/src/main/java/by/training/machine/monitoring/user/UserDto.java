package by.training.machine.monitoring.user;

import lombok.*;

import java.sql.Blob;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UserDto {
    private Long id;
    private String login;
    private String password;
    private String email;
    private String name;
    private String address;
    private String tel;
    private byte[] picture;
}