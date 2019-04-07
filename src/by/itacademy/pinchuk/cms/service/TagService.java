package by.itacademy.pinchuk.cms.service;

import by.itacademy.pinchuk.cms.dao.Dao;
import by.itacademy.pinchuk.cms.dao.TagDao;
import by.itacademy.pinchuk.cms.dto.TagDto;
import by.itacademy.pinchuk.cms.entity.Lang;
import by.itacademy.pinchuk.cms.entity.Tag;
import by.itacademy.pinchuk.cms.mapper.Mapper;
import by.itacademy.pinchuk.cms.mapper.TagMapper;
import by.itacademy.pinchuk.cms.util.DaoFilter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TagService implements Service<Tag> {

    private static final TagService INSTANCE = new TagService();
    private Dao<Tag> dao = TagDao.getInstance();
    private Mapper<Tag, TagDto> tagMapper = TagMapper.getInstance();

    public Optional<TagDto> get(int id, boolean onlyPublished) {
        return dao.get(id, onlyPublished).map(tagMapper::entityToDto);
    }

    public Optional<TagDto> get(int id) {
        return get(id, false);
    }

    public List<TagDto> getRelatedToContent(int id, Lang currentLang) {
        return TagDao.getInstance().getRelatedToContent(id, currentLang).stream()
                .map(tagMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public List<TagDto> getAll(DaoFilter.Builder filter) {
        return dao.getAll(filter).stream()
                .map(tagMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public List<TagDto> getAll() {
        return getAll(DaoFilter.builder());
    }

    public List<TagDto> getAllPublished() {
        return getAll();
    }

    public Optional<Tag> create(Tag tag) {
        return dao.create(tag);
    }

    public boolean update(Tag tag) {
        return dao.update(tag);
    }

    public boolean delete(int id) {
        return dao.delete(id);
    }

    public static TagService getInstance() {
        return INSTANCE;
    }
}
