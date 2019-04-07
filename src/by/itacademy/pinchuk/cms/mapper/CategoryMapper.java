package by.itacademy.pinchuk.cms.mapper;

import by.itacademy.pinchuk.cms.dto.CategoryDto;
import by.itacademy.pinchuk.cms.entity.Category;

public class CategoryMapper implements Mapper<Category, CategoryDto> {

    private static final CategoryMapper INSTANCE = new CategoryMapper();

    @Override
    public CategoryDto entityToDto(Category entity) {
        return CategoryDto.builder()
                .id(entity.getId())
                .parentId(entity.getParentId())
                .alias(entity.getAlias())
                .created(entity.getCreated())
                .active(entity.isActive())
                .translation(entity.getTranslation())
                .build();
    }

    @Override
    public Category dtoToEntity(CategoryDto dto) {
        return Category.builder()
                .id(dto.getId())
                .parentId(dto.getParentId())
                .alias(dto.getAlias())
                .created(dto.getCreated())
                .active(dto.isActive())
                .translation(dto.getTranslation())
                .build();
    }

    public static CategoryMapper getInstance() {
        return INSTANCE;
    }
}
