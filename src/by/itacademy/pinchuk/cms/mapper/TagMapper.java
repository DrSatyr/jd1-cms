package by.itacademy.pinchuk.cms.mapper;

import by.itacademy.pinchuk.cms.dto.TagDto;
import by.itacademy.pinchuk.cms.entity.Lang;
import by.itacademy.pinchuk.cms.entity.Tag;

public class TagMapper implements Mapper<Tag, TagDto> {

    private static final TagMapper INSTANCE = new TagMapper();

    @Override
    public TagDto entityToDto(Tag entity) {
        return TagDto.builder()
                .id(entity.getId())
                .lang(entity.getLang().name())
                .value(entity.getValue())
                .build();
    }

    @Override
    public Tag dtoToEntity(TagDto dao) {
        return Tag.builder()
                .id(dao.getId())
                .lang(Lang.valueOf(dao.getLang()))
                .value(dao.getValue())
                .build();
    }

    public static TagMapper getInstance() {
        return INSTANCE;
    }
}
