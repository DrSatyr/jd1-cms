package by.itacademy.pinchuk.cms.dto;

import by.itacademy.pinchuk.cms.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto implements Dto {

    private Integer id;
    private Integer contentId;
    private User user;
    // TODO: 31.03.2019 Заменить на строку используя преобразование
    private LocalDateTime created;
    private String fullText;
    private String status;
}
