package by.itacademy.pinchuk.cms.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment implements Entity {

    private Integer id;
    private Integer contentId;
    private User user;
    private LocalDateTime created;
    private String fullText;
    private CommentStatus status;
}
