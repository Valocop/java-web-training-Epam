package by.training.machine.monitoring.user;

import by.training.machine.monitoring.entity.UserEntity;

public class UserUtil {

    public static UserEntity fromDto(UserDto userDto) {
        return UserEntity.builder()
                .id(userDto.getId())
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .name(userDto.getName())
                .address(userDto.getAddress())
                .tel(userDto.getTel())
                .build();
    }

    public static UserDto fromEntity(UserEntity userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .login(userEntity.getLogin())
                .password(userEntity.getPassword())
                .email(userEntity.getEmail())
                .name(userEntity.getName())
                .address(userEntity.getAddress())
                .tel(userEntity.getTel())
                .build();
    }
}
