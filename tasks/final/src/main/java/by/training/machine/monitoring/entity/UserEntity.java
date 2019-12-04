package by.training.machine.monitoring.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    private Long id;
    private String login;
    private String password;
    private String email;
    private String name;
    private String address;
    private String tel;
    private byte[] picture;
    private Date creationDate;
    private Date modificationDate;
}
