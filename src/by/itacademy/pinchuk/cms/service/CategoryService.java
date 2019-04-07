package by.itacademy.pinchuk.cms.service;

import by.itacademy.pinchuk.cms.dao.CategoryDao;
import by.itacademy.pinchuk.cms.dao.Dao;
import by.itacademy.pinchuk.cms.dto.CategoryDto;
import by.itacademy.pinchuk.cms.dto.CategoryWithContentsDto;
import by.itacademy.pinchuk.cms.entity.Category;
import by.itacademy.pinchuk.cms.mapper.CategoryMapper;
import by.itacademy.pinchuk.cms.mapper.CategoryWithContentsMapper;
import by.itacademy.pinchuk.cms.util.DaoFilter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryService implements Service<Category> {

    private static final CategoryService INSTANCE = new CategoryService();
    private Dao<Category> dao = CategoryDao.getInstance();
    private CategoryMapper categoryMapper = CategoryMapper.getInstance();
    private CategoryWithContentsMapper categoryWithContentsMapper = CategoryWithContentsMapper.getInstance();

    public Optional<CategoryDto> get(int id, boolean onlyPublished) {
        return dao.get(id, onlyPublished).map(categoryMapper::entityToDto);
    }

    public Optional<CategoryDto> get(int id) {
        return get(id, false);
    }

    public List<CategoryDto> getAll(DaoFilter.Builder filter) {
        return dao.getAll(filter).stream()
                .map(categoryMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public List<CategoryDto> getAll() {
        return getAll(DaoFilter.builder());
    }

    public List<CategoryDto> getAllPublished() {
        DaoFilter.Builder filter = DaoFilter.builder()
                .addCondition(CategoryDao.FILTER_BY_ACTIVE, DaoFilter.MatchType.EQ, "TRUE");
        return getAll(filter);
    }

    // TODO: 07.04.2019 Возможно стоит перенести на уровень DAO что бы собирать всю информацию ондим запросом 
    public List<CategoryWithContentsDto> getAllPublishedWithContent(int contentLimit) {
        DaoFilter.Builder filter = DaoFilter.builder()
                .addCondition(CategoryDao.FILTER_BY_ACTIVE, DaoFilter.MatchType.EQ, "TRUE");
        List<CategoryWithContentsDto> categories = dao.getAll(filter).stream()
                .map(categoryWithContentsMapper::entityToDto)
                .collect(Collectors.toList());
        categories.forEach(it -> it.setContents(ContentService.getInstance().getLatestPublished(contentLimit, it.getId())));
        return categories;
    }

    public Optional<Category> create(Category category) {
        return dao.create(category);
    }

    public boolean update(Category category) {
        return dao.update(category);
    }

    public boolean delete(int id) {
        return dao.delete(id);
    }

    public static CategoryService getInstance() {
        return INSTANCE;
    }
}
