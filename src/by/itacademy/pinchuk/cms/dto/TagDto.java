package by.itacademy.pinchuk.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagDto implements Dto {

    private Integer id;
    private String lang;
    private String value;
}
