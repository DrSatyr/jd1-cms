package by.itacademy.pinchuk.cms.dao;

import by.itacademy.pinchuk.cms.entity.Lang;
import by.itacademy.pinchuk.cms.entity.Tag;
import by.itacademy.pinchuk.cms.util.ConnectionManager;
import by.itacademy.pinchuk.cms.util.DaoBuilder;
import by.itacademy.pinchuk.cms.util.DaoFilter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class    TagDao extends BaseDao<Tag> implements Dao<Tag> {

    public static final String FILTER_BY_LANG = "t.lang";
    public static final String FILTER_BY_VALUE = "t.value";

    private static final TagDao INSTANCE = new TagDao();
    private static final String GET_ALL =
            "SELECT " +
                    "t.id AS tag_id, " +
                    "t.lang AS tag_lang, " +
                    "t.value AS tag_value " +
                    "FROM app.tag t " +
                    "WHERE TRUE ";
    private static final String GET_FILTERED_BY_ID = "AND id = ?;";
    private static final String GET_TAGS_BY_CONTENT_ID_AND_LANG =
            "SELECT " +
                    "t.id AS tag_id, " +
                    "t.lang AS tag_lang, " +
                    "t.value AS tag_value " +
                    "FROM app.content_tag ct " +
                    "JOIN app.tag t ON ct.tags_id = t.id " +
                    "WHERE ct.content_id = ? AND t.lang = ? ";
    private static final String CREATE = "INSERT INTO app.tag (lang, value) VALUES (?, ?);";
    private static final String UPDATE = "UPDATE app.tag SET lang = ?, value = ? WHERE id = ?;";
    private static final String DELETE = "DELETE FROM app.tag WHERE id = ?;";

    @SneakyThrows
    public Optional<Tag> get(int id, boolean onlyPublished) {
        Tag tag = null;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL + GET_FILTERED_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                tag = DaoBuilder.buildTagFromQuery(resultSet);
            }
        }
        return Optional.ofNullable(tag);
    }

    // TODO: 31.03.2019 Нет теста для этого метода.
    @SneakyThrows
    public List<Tag> getRelatedToContent(int contentId, Lang currentLang) {
        List<Tag> tags = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_TAGS_BY_CONTENT_ID_AND_LANG)) {
            preparedStatement.setInt(1, contentId);
            preparedStatement.setString(2, currentLang.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tags.add(DaoBuilder.buildTagFromQuery(resultSet));
            }
        }
        return tags;
    }

    @SneakyThrows
    public List<Tag> getAll(DaoFilter.Builder filter) {
        List<Tag> tags = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL + filter.build())) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tags.add(DaoBuilder.buildTagFromQuery(resultSet));
            }
        }
        return tags;
    }

    @SneakyThrows
    public List<Tag> getAll() {
        return getAll(DaoFilter.builder());
    }

    @SneakyThrows
    public Optional<Tag> create(Tag tag) {
        Tag createdTag = null;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE, RETURN_GENERATED_KEYS);) {
            preparedStatement.setString(1, tag.getLang().name());
            preparedStatement.setString(2, tag.getValue());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                createdTag = tag;
                createdTag.setId(generatedKeys.getInt("id"));
            }
        }
        return Optional.ofNullable(createdTag);
    }

    @SneakyThrows
    public boolean update(Tag tag) {
        boolean result = false;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, tag.getLang().name());
            preparedStatement.setString(2, tag.getValue());
            preparedStatement.setInt(3, tag.getId());
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

    public static TagDao getInstance() {
        return INSTANCE;
    }
}