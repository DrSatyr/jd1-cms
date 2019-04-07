package by.itacademy.pinchuk.cms.mapper;

import by.itacademy.pinchuk.cms.dto.ContentListDto;
import by.itacademy.pinchuk.cms.entity.Content;

public class ContentListMapper implements Mapper<Content, ContentListDto> {

    private static final ContentListMapper INSTANCE = new ContentListMapper();

    @Override
    public ContentListDto entityToDto(Content entity) {
        return ContentListDto.builder()
                .id(entity.getId())
                .createdBy(entity.getCreatedBy())
                .category(entity.getCategory())
                // TODO: 31.03.2019 Использовать утилиту для преобразование даты в строку
                .created(entity.getCreated())
                .translation(entity.getTranslation())
                .build();
    }

    @Override
    public Content dtoToEntity(ContentListDto dto) {
        return Content.builder()
                .id(dto.getId())
                .createdBy(dto.getCreatedBy())
                .category(dto.getCategory())
                // TODO: 31.03.2019 Использовать утилиту для преобразование строки в дату
                .created(dto.getCreated())
                .build();
    }

    public static ContentListMapper getInstance() {
        return INSTANCE;
    }
}
