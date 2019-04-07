package by.itacademy.pinchuk.cms.service;

import by.itacademy.pinchuk.cms.dao.CommentDao;
import by.itacademy.pinchuk.cms.dao.Dao;
import by.itacademy.pinchuk.cms.dto.CommentDto;
import by.itacademy.pinchuk.cms.entity.Comment;
import by.itacademy.pinchuk.cms.entity.CommentStatus;
import by.itacademy.pinchuk.cms.mapper.CommentMapper;
import by.itacademy.pinchuk.cms.util.DaoFilter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentService implements Service<Comment> {

    private static final CommentService INSTANCE = new CommentService();
    private Dao<Comment> dao = CommentDao.getInstance();
    private CommentMapper commentMapper = CommentMapper.getInstance();

    public Optional<CommentDto> get(int id, boolean onlyPublished) {
        return dao.get(id, onlyPublished).map(commentMapper::entityToDto);
    }

    public Optional<CommentDto> get(int id) {
        return get(id, false);
    }

    public List<CommentDto> getAll(DaoFilter.Builder filter) {
        return dao.getAll(filter).stream()
                .map(commentMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public List<CommentDto> getAll() {
        return getAll(DaoFilter.builder());
    }

    public List<CommentDto> getAllPublished() {
        DaoFilter.Builder filter = DaoFilter.builder()
                .addCondition(CommentDao.FILTER_BY_STATUS, DaoFilter.MatchType.EQ, "PUBLISHED");
        return getAll(filter);
    }

    public List<CommentDto> getRelatedToContent(int id) {
        DaoFilter.Builder commentFilter = DaoFilter.builder()
                .addCondition(CommentDao.FILTER_BY_CONTENT_ID, DaoFilter.MatchType.EQ, String.valueOf(id))
                .addLogic(DaoFilter.Logic.AND)
                .addCondition(CommentDao.FILTER_BY_STATUS, DaoFilter.MatchType.EQ, CommentStatus.PUBLISHED.name());
        return getAll(commentFilter);
    }

    public Optional<Comment> create(Comment comment) {
        return dao.create(comment);
    }

    public boolean update(Comment comment) {
        return dao.update(comment);
    }

    public boolean delete(int id) {
        return dao.delete(id);
    }

    public static CommentService getInstance() {
        return INSTANCE;
    }
}
