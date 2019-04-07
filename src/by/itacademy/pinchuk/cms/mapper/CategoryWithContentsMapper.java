package by.itacademy.pinchuk.cms.mapper;

import by.itacademy.pinchuk.cms.dto.CategoryWithContentsDto;
import by.itacademy.pinchuk.cms.entity.Category;

public class CategoryWithContentsMapper implements Mapper<Category, CategoryWithContentsDto> {

    private static final CategoryWithContentsMapper INSTANCE = new CategoryWithContentsMapper();

    @Override
    public CategoryWithContentsDto entityToDto(Category entity) {
        return CategoryWithContentsDto.builder()
                .id(entity.getId())
                .parentId(entity.getParentId())
                .alias(entity.getAlias())
                .created(entity.getCreated())
                .active(entity.isActive())
                .translation(entity.getTranslation())
                .build();
    }

    @Override
    public Category dtoToEntity(CategoryWithContentsDto dto) {
        return Category.builder()
                .id(dto.getId())
                .parentId(dto.getParentId())
                .alias(dto.getAlias())
                .created(dto.getCreated())
                .active(dto.isActive())
                .translation(dto.getTranslation())
                .build();
    }

    public static CategoryWithContentsMapper getInstance() {
        return INSTANCE;
    }
}
