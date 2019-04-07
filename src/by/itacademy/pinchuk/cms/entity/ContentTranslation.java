package by.itacademy.pinchuk.cms.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentTranslation {
    private String name;
    private String introText;
    private String fullText;
    private String metaTitle;
    private String metaDescription;
    private String metaKeywords;
}
