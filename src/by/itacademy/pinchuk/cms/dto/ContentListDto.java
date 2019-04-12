package by.itacademy.pinchuk.cms.dto;

import by.itacademy.pinchuk.cms.entity.Category;
import by.itacademy.pinchuk.cms.entity.ContentTranslation;
import by.itacademy.pinchuk.cms.entity.Lang;
import by.itacademy.pinchuk.cms.entity.User;
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
public class ContentListDto implements Dto {

    private Integer id;
    private Category category;
    private LocalDate created;
    private User createdBy;
    private String image;
    private Map<Lang, ContentTranslation> translation;
}
