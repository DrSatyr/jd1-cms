package by.itacademy.pinchuk.cms.dto;

import by.itacademy.pinchuk.cms.entity.CategoryTranslation;
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
public class CategoryDto implements Dto{

    private Integer id;
    private Integer parentId;
    private String alias;
    // TODO: 31.03.2019 Заменить на строку используя преобразование
    private LocalDate created;
    private boolean active;
    private Map<Lang, CategoryTranslation> translation;
}
