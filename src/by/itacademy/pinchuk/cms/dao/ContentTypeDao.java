package by.itacademy.pinchuk.cms.dao;

import by.itacademy.pinchuk.cms.entity.ContentType;
import by.itacademy.pinchuk.cms.util.ConnectionManager;
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

import static by.itacademy.pinchuk.cms.util.DaoBuilder.buildContentTypeFromQuery;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContentTypeDao extends BaseDao<ContentType> implements Dao<ContentType> {

    public static final String FILTER_BY_NAME = "name";

    private static final ContentTypeDao INSTANCE = new ContentTypeDao();
    private static final String GET_ALL =
            "SELECT " +
                    "id AS content_type_id, " +
                    "name AS content_type_name, " +
                    "extra_field_types AS content_type_extra_field_types " +
                    "FROM app.content_type " +
                    "WHERE TRUE ";
    private static final String GET_FILTERED_BY_ID = "AND id = ? ";
    private static final String CREATE = "INSERT INTO app.content_type (name, extra_field_types) VALUES (?, ?);";
    private static final String UPDATE = "UPDATE app.content_type SET name = ?, extra_field_types = ? WHERE id = ?;";
    private static final String DELETE = "DELETE FROM app.content_type WHERE id = ?;";

    @SneakyThrows
    public Optional<ContentType> get(int id, boolean onlyPublished) {
        ContentType contentType = null;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL + GET_FILTERED_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                contentType = buildContentTypeFromQuery(resultSet);
            }
        }
        return Optional.ofNullable(contentType);
    }

    @SneakyThrows
    public List<ContentType> getAll(DaoFilter.Builder filter) {
        List<ContentType> contentTypes = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL + filter.build())) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                contentTypes.add(buildContentTypeFromQuery(resultSet));
            }
        }
        return contentTypes;
    }

    public List<ContentType> getAll() {
        return getAll(DaoFilter.builder());
    }

    @SneakyThrows
    public Optional<ContentType> create(ContentType contentType) {
        ContentType createdContentType = null;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE, RETURN_GENERATED_KEYS);) {
            preparedStatement.setString(1, contentType.getName());
            preparedStatement.setObject(2, contentType.getExtraFieldTypes());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                createdContentType = contentType;
                createdContentType.setId(generatedKeys.getInt("id"));
            }
        }
        return Optional.ofNullable(createdContentType);
    }

    @SneakyThrows
    public boolean update(ContentType contentType) {
        boolean result = false;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, contentType.getName());
            preparedStatement.setObject(2, contentType.getExtraFieldTypes());
            preparedStatement.setInt(3, contentType.getId());
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

    public static ContentTypeDao getInstance() {
        return INSTANCE;
    }
}
