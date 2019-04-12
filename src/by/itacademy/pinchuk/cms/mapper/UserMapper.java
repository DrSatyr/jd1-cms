package by.itacademy.pinchuk.cms.mapper;

import by.itacademy.pinchuk.cms.dto.UserDto;
import by.itacademy.pinchuk.cms.entity.User;
import by.itacademy.pinchuk.cms.entity.UserRole;

public class UserMapper implements Mapper<User, UserDto> {

    private static final UserMapper INSTANCE = new UserMapper();

    @Override
    public UserDto entityToDto(User entity) {
        return UserDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .password(entity.getPassword())
                .active(entity.isActive())
                .role(entity.getRole().name())
                .registerDate(entity.getRegisterDate())
                .birthDate(entity.getBirthDate())
                .name(entity.getName())
                .surname(entity.getSurname())
                .build();
    }

    @Override
    public User dtoToEntity(UserDto dto) {
        return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .password(dto.getPassword())
                .active(dto.isActive())
                .role(UserRole.valueOf(dto.getRole()))
                .registerDate(dto.getRegisterDate())
                .birthDate(dto.getBirthDate())
                .name(dto.getName())
                .surname(dto.getSurname())
                .build();
    }

    public static UserMapper getInstance() {
        return INSTANCE;
    }
}
