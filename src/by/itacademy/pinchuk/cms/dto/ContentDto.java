package by.itacademy.pinchuk.cms.dto;

import by.itacademy.pinchuk.cms.entity.Category;
import by.itacademy.pinchuk.cms.entity.ContentTranslation;
import by.itacademy.pinchuk.cms.entity.ContentType;
import by.itacademy.pinchuk.cms.entity.Lang;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentDto implements Dto {

    private Integer id;
    private boolean active;
    private ContentType contentType;
    private Category category;
    private String alias;
    // TODO: 31.03.2019 Заменить на строку используя преобразование
    private LocalDate created;
    private Integer userId;
    private String userName;
    private String userSurname;
    private Integer hits;
    private String image;
    private Map<Lang, ContentTranslation> translation;
}
