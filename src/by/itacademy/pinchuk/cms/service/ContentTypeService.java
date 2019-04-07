package by.itacademy.pinchuk.cms.service;

import by.itacademy.pinchuk.cms.dao.ContentTypeDao;
import by.itacademy.pinchuk.cms.dao.Dao;
import by.itacademy.pinchuk.cms.dto.ContentTypeDto;
import by.itacademy.pinchuk.cms.entity.ContentType;
import by.itacademy.pinchuk.cms.mapper.ContentTypeMapper;
import by.itacademy.pinchuk.cms.util.DaoFilter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContentTypeService implements Service<ContentType> {

    private static final ContentTypeService INSTANCE = new ContentTypeService();
    private Dao<ContentType> dao = ContentTypeDao.getInstance();
    private ContentTypeMapper contentTypeMapper = ContentTypeMapper.getInstance();

    public Optional<ContentTypeDto> get(int id, boolean onlyPublished) {
        return dao.get(id, onlyPublished).map(contentTypeMapper::entityToDto);
    }

    public Optional<ContentTypeDto> get(int id) {
        return get(id, false);
    }

    public List<ContentTypeDto> getAll(DaoFilter.Builder filter) {
        return dao.getAll(filter).stream()
                .map(contentTypeMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public List<ContentTypeDto> getAll() {
        return getAll(DaoFilter.builder());
    }

    public List<ContentTypeDto> getAllPublished() {
        return getAll();
    }

    public Optional<ContentType> create(ContentType contentType) {
        return dao.create(contentType);
    }

    public boolean update(ContentType contentType) {
        return dao.update(contentType);
    }

    public boolean delete(int id) {
        return dao.delete(id);
    }

    public static ContentTypeService getInstance() {
        return INSTANCE;
    }
}
