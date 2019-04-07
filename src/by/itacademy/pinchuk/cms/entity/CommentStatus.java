package by.itacademy.pinchuk.cms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommentStatus {

    // TODO: 20.03.2019 Вынести названия в файл локолизации (когда он будет)
    ON_MODERATION("На модерации"),
    PUBLISHED("Опубликован"),
    REJECTED("Отклонен");

    private String name;
}
