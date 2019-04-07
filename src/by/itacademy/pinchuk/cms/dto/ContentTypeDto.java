package by.itacademy.pinchuk.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentTypeDto implements Dto {

    private Integer id;
    private String name;
    private String extraFieldTypes;
}
