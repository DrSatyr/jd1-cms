package by.itacademy.pinchuk.cms.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Content implements Entity {

    private Integer id;
    private ContentType contentType;
    private Category category;
    private String alias;
    private LocalDate created;
    private User createdBy;
    private LocalDate lastModified;
    private LocalDate publishUp;
    private LocalDate publishDown;
    private Integer hits;
    private boolean active;
    private String extraFields;
    private String image;
    private Map<Lang, ContentTranslation> translation;
}
