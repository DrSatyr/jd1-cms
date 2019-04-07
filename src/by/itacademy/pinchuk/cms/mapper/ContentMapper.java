package by.itacademy.pinchuk.cms.mapper;

import by.itacademy.pinchuk.cms.dao.UserDao;
import by.itacademy.pinchuk.cms.dto.ContentDto;
import by.itacademy.pinchuk.cms.entity.Content;
import by.itacademy.pinchuk.cms.entity.User;

import java.util.Optional;

public class ContentMapper implements Mapper<Content, ContentDto> {

    private static final ContentMapper INSTANCE = new ContentMapper();

    @Override
    public ContentDto entityToDto(Content entity) {
        return ContentDto.builder()
                .id(entity.getId())
                .active(entity.isActive())
                .contentType(entity.getContentType())
                .category(entity.getCategory())
                .alias(entity.getAlias())
                // TODO: 31.03.2019 Использовать утилиту для преобразование даты в строку
                .created(entity.getCreated())
                .userId(entity.getCreatedBy().getId())
                .userName(entity.getCreatedBy().getName())
                .userSurname(entity.getCreatedBy().getSurname())
                .hits(entity.getHits())
                .image(entity.getImage())
                .translation(entity.getTranslation())
                .build();
    }

    @Override
    public Content dtoToEntity(ContentDto dto) {
        Optional<User> user = UserDao.getInstance().get(dto.getUserId());

        return Content.builder()
                .id(dto.getId())
                .active(dto.isActive())
                .contentType(dto.getContentType())
                .category(dto.getCategory())
                .alias(dto.getAlias())
                // TODO: 31.03.2019 Использовать утилиту для преодбразования строки в дату
                .created(dto.getCreated())
                .createdBy(user.orElse(null))
                .hits(dto.getHits())
                .image(dto.getImage())
                .translation(dto.getTranslation())
                .build();
    }

    public static ContentMapper getInstance() {
        return INSTANCE;
    }
}
