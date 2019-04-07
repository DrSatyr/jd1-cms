package by.itacademy.pinchuk.cms.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tag implements Entity {

    private Integer id;
    private Lang lang;
    private String value;
}
