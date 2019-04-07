package by.itacademy.pinchuk.cms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Lang {

    EN("Английский", "EN"),
    RU("Русский", "RU");

    private String name;
    private String isoCode;
}
