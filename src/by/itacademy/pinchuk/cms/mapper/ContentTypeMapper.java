package by.itacademy.pinchuk.cms.mapper;

import by.itacademy.pinchuk.cms.dto.ContentTypeDto;
import by.itacademy.pinchuk.cms.entity.ContentType;

public class ContentTypeMapper implements Mapper<ContentType, ContentTypeDto> {

    private static final ContentTypeMapper INSTANCE = new ContentTypeMapper();

    @Override
    public ContentTypeDto entityToDto(ContentType entity) {
        return ContentTypeDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .extraFieldTypes(entity.getExtraFieldTypes())
                .build();
    }

    @Override
    public ContentType dtoToEntity(ContentTypeDto dto) {
        return ContentType.builder()
                .id(dto.getId())
                .name(dto.getName())
                .extraFieldTypes(dto.getExtraFieldTypes())
                .build();
    }

    public static ContentTypeMapper getInstance() {
        return INSTANCE;
    }
}
