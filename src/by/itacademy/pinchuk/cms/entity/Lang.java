package by.itacademy.pinchuk.cms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Locale;

@AllArgsConstructor
@Getter
public enum Lang {

    EN("Английский", "EN", new Locale("en", "US")),
    RU("Русский", "RU", new Locale("ru", "RU"));

    private String name;
    private String isoCode;
    private Locale locale;
}
