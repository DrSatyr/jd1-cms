package by.itacademy.pinchuk.cms.service;

import by.itacademy.pinchuk.cms.dao.ContentDao;
import by.itacademy.pinchuk.cms.dao.Dao;
import by.itacademy.pinchuk.cms.dto.ContentDto;
import by.itacademy.pinchuk.cms.dto.ContentListDto;
import by.itacademy.pinchuk.cms.entity.Content;
import by.itacademy.pinchuk.cms.mapper.ContentListMapper;
import by.itacademy.pinchuk.cms.mapper.ContentMapper;
import by.itacademy.pinchuk.cms.util.DaoFilter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContentService implements Service<Content> {

    private static final ContentService INSTANCE = new ContentService();
    private Dao<Content> dao = ContentDao.getInstance();
    private ContentListMapper contentListMapper = ContentListMapper.getInstance();
    private ContentMapper contentMapper = ContentMapper.getInstance();

    public Optional<ContentDto> get(int id, boolean onlyPublished) {
        return dao.get(id, onlyPublished).map(contentMapper::entityToDto);
    }

    public Optional<ContentDto> get(int id) {
        return get(id, false);
    }

    public List<ContentListDto> getAll(DaoFilter.Builder filter) {
        return dao.getAll(filter).stream()
                .map(contentListMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public List<ContentListDto> getAll() {
        return getAll(DaoFilter.builder());
    }

    public List<ContentListDto> getAllPublished() {
        DaoFilter.Builder filter = DaoFilter.builder()
                .addCondition(ContentDao.FILTER_BY_ACTIVE, DaoFilter.MatchType.EQ, "TRUE");
        return getAll(filter);
    }

    public List<ContentListDto> getAllPublished(int categoryId) {
        DaoFilter.Builder filter = DaoFilter.builder()
                .addCondition(ContentDao.FILTER_BY_ACTIVE, DaoFilter.MatchType.EQ, "TRUE")
                .addLogic(DaoFilter.Logic.AND)
                .addCondition(ContentDao.FILTER_BY_CATEGORY_ID, DaoFilter.MatchType.EQ, String.valueOf(categoryId));
        return getAll(filter);
    }

    public List<ContentListDto> getLatestPublished(int limit, int formCategory) {
        DaoFilter.Builder filter = DaoFilter.builder()
                .addCondition(ContentDao.FILTER_BY_CATEGORY_ID, DaoFilter.MatchType.EQ, String.valueOf(formCategory))
                .addOrdering(ContentDao.ORDERING_BY_CREATED, DaoFilter.Ordering.DESC)
                .setLimit(limit);
        return getAll(filter);
    }

    public List<ContentListDto> getLatestPublished(int limit) {
        DaoFilter.Builder filter = DaoFilter.builder()
                .addOrdering(ContentDao.ORDERING_BY_CREATED, DaoFilter.Ordering.DESC)
                .setLimit(limit);
        return getAll(filter);
    }

    public Optional<Content> create(Content content) {
        return dao.create(content);
    }

    public boolean update(Content content) {
        return dao.update(content);
    }

    public boolean delete(int id) {
        return dao.delete(id);
    }

    public static ContentService getInstance() {
        return INSTANCE;
    }
}
