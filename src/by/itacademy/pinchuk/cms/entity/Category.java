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
public class Category implements Entity {

    private Integer id;
    private Integer parentId;
    private String alias;
    private LocalDate created;
    private boolean active;
    private Map<Lang, CategoryTranslation> translation;

    public String getName(Lang lang) {
        return translation.get(lang).getName();
    }
}
