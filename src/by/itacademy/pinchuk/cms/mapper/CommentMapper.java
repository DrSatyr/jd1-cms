package by.itacademy.pinchuk.cms.mapper;

import by.itacademy.pinchuk.cms.dto.CommentDto;
import by.itacademy.pinchuk.cms.entity.Comment;
import by.itacademy.pinchuk.cms.entity.CommentStatus;

public class CommentMapper implements Mapper<Comment, CommentDto> {

    private static final CommentMapper INSTANCE = new CommentMapper();

    @Override
    public CommentDto entityToDto(Comment entity) {
        return CommentDto.builder()
                .id(entity.getId())
                // TODO: 31.03.2019 Использовать утилиту для преобразование даты в строку
                .contentId(entity.getContentId())
                .user(entity.getUser())
                .created(entity.getCreated())
                .fullText(entity.getFullText())
                .status(entity.getStatus().getName())
                .build();
    }

    @Override
    public Comment dtoToEntity(CommentDto dao) {
        return Comment.builder()
                .id(dao.getId())
                .contentId(dao.getContentId())
                .user(dao.getUser())
                // TODO: 31.03.2019 Использовать утилиту для преобразование строки в дату
                .created(dao.getCreated())
                .fullText(dao.getFullText())
                .status(CommentStatus.valueOf(dao.getStatus()))
                .build();
    }

    public static CommentMapper getInstance() {
        return INSTANCE;
    }
}
