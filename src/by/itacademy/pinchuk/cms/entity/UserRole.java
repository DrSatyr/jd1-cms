package by.itacademy.pinchuk.cms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {

    ADMINISTRATOR("administrator"),
    EDITOR("editor"),
    USER("user");

    private String roleName;
}
