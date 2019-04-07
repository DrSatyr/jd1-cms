package by.itacademy.pinchuk.cms.dao;

import by.itacademy.pinchuk.cms.entity.Comment;
import by.itacademy.pinchuk.cms.util.ConnectionManager;
import by.itacademy.pinchuk.cms.util.DaoBuilder;
import by.itacademy.pinchuk.cms.util.DaoFilter;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class CommentDao extends BaseDao<Comment> implements Dao<Comment> {

    public static final String FILTER_BY_USER_ID = "c.user_id";
    public static final String FILTER_BY_STATUS = "c.status";
    public static final String FILTER_BY_CONTENT_ID = "c.content_id";

    private static final CommentDao INSTANCE = new CommentDao();
    private static final String GET_ALL =
            "SELECT " +
                    "c.id AS comment_id," +
                    "c.content_id AS comment_content_id," +
                    "c.created AS comment_created," +
                    "c.full_text AS comment_full_text," +
                    "c.status AS comment_status," +
                    "u.id AS user_id," +
                    "u.username AS user_username," +
                    "u.email AS user_email," +
                    "u.phone AS user_phone," +
                    "u.password AS user_password," +
                    "u.active AS user_active," +
                    "u.role AS user_role," +
                    "u.register_date AS user_register_date," +
                    "u.birth_date AS user_birth_date," +
                    "u.name AS user_name," +
                    "u.surname AS user_surname " +
                    "FROM app.comment c " +
                    "JOIN app.user u " +
                    "ON u.id = c.user_id " +
                    "WHERE TRUE ";
    private static final String GET_BY_ID = "AND c.id = ? ";
    private static final String GET_BY_ID_AND_PUBLISHED = GET_BY_ID + "AND status = 'PUBLISHED' ";
    private static final String CREATE =
            "INSERT INTO app.comment " +
                    "(content_id, user_id, created, full_text, status) " +
                    "VALUES (?, ?, ?, ?, ?);";
    private static final String UPDATE =
            "UPDATE app.comment SET " +
                    "content_id = ?, " +
                    "user_id = ?, " +
                    "created = ?, " +
                    "full_text = ?, " +
                    "status = ? " +
                    "WHERE id = ?;";
    private static final String DELETE = "DELETE FROM app.comment WHERE id = ?;";

    @SneakyThrows
    public Optional<Comment> get(int id, boolean onlyPublished) {
        Comment comment = null;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL
                     + (onlyPublished ? GET_BY_ID_AND_PUBLISHED : GET_BY_ID))) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                comment = DaoBuilder.buildCommentFromQuery(resultSet);
            }
        }
        return Optional.ofNullable(comment);
    }

    @SneakyThrows
    public List<Comment> getAll(DaoFilter.Builder filter) {
        List<Comment> comments = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL + filter.build())) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                comments.add(DaoBuilder.buildCommentFromQuery(resultSet));
            }
        }
        return comments;
    }

    public List<Comment> getAll() {
        return getAll(DaoFilter.builder());
    }

    @SneakyThrows
    public Optional<Comment> create(Comment comment) {
        Comment createdComment = null;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE, RETURN_GENERATED_KEYS);) {
            preparedStatement.setInt(1, comment.getContentId());
            preparedStatement.setInt(2, comment.getUser().getId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(comment.getCreated()));
            preparedStatement.setString(4, comment.getFullText());
            preparedStatement.setString(5, comment.getStatus().name());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                createdComment = comment;
                createdComment.setId(generatedKeys.getInt("id"));
            }
        }
        return Optional.ofNullable(createdComment);
    }

    @SneakyThrows
    public boolean update(Comment comment) {
        boolean result = false;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setInt(1, comment.getContentId());
            preparedStatement.setInt(2, comment.getUser().getId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(comment.getCreated()));
            preparedStatement.setString(4, comment.getFullText());
            preparedStatement.setString(5, comment.getStatus().name());
            preparedStatement.setInt(6, comment.getId());
            if (preparedStatement.executeUpdate() > 0) {
                result = true;
            }
        }
        return result;
    }

    @SneakyThrows
    @SuppressWarnings("Duplicates")
    public boolean delete(int id) {
        boolean result = false;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                result = true;
            }
            return result;
        }
    }

    public static CommentDao getInstance() {
        return INSTANCE;
    }
}
