package by.itacademy.pinchuk.cms.dao;

import by.itacademy.pinchuk.cms.entity.Category;
import by.itacademy.pinchuk.cms.entity.Content;
import by.itacademy.pinchuk.cms.entity.ContentTranslation;
import by.itacademy.pinchuk.cms.entity.Lang;
import by.itacademy.pinchuk.cms.util.ConnectionManager;
import by.itacademy.pinchuk.cms.util.DaoBuilder;
import by.itacademy.pinchuk.cms.util.DaoFilter;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.itacademy.pinchuk.cms.util.DateHelper.convertToSqlDate;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class ContentDao extends BaseDao<Content> implements Dao<Content> {

    public static final String FILTER_BY_ID = "cont.id";
    public static final String FILTER_BY_CONTENT_TYPE_ID = "cont.content_type_id";
    public static final String FILTER_BY_CATEGORY_ID = "cont.category_id";
    public static final String FILTER_BY_CREATED = "cont.created";
    public static final String FILTER_BY_CREATED_BY = "cont.created_by";
    public static final String FILTER_BY_HITS = "cont.hits";
    public static final String FILTER_BY_ACTIVE = "cont.active";
    public static final String FILTER_BY_LANG = "cont_tr.lang";
    public static final String ORDERING_BY_CREATED = "cont.created";
    public static final String ORDERING_BY_ID = "cont.id";

    private static final ContentDao INSTANCE = new ContentDao();
    private static final String GET_ALL =
            "SELECT cont.id AS c_id," +
                    "cont.id                     AS content_id, " +
                    "cont.content_type_id        AS content_content_type_id, " +
                    "cont.category_id            AS content_category_id, " +
                    "cont.alias                  AS content_alias, " +
                    "cont.created                AS content_created, " +
                    "cont.created_by             AS content_created_by, " +
                    "cont.last_modified          AS content_last_modified, " +
                    "cont.publish_up             AS content_publish_up, " +
                    "cont.publish_down           AS content_publish_down, " +
                    "cont.hits                   AS content_hits, " +
                    "cont.active                 AS content_active, " +
                    "cont.extra_fields           AS content_extra_fields, " +
                    "cont.image                  AS content_image, " +
                    "cont_tr.lang                AS content_translate_lang, " +
                    "cont_tr.name                AS content_translate_name, " +
                    "cont_tr.intro_text          AS content_translate_intro_text, " +
                    "cont_tr.full_text           AS content_translate_full_text, " +
                    "cont_tr.meta_title          AS content_translate_meta_title, " +
                    "cont_tr.meta_description    AS content_translate_meta_description, " +
                    "cont_tr.meta_keywords       AS content_translate_meta_keywords, " +
                    "u.id                        AS user_id, " +
                    "u.username                  AS user_username, " +
                    "u.email                     AS user_email, " +
                    "u.phone                     AS user_phone, " +
                    "u.password                  AS user_password, " +
                    "u.active                    AS user_active, " +
                    "u.role                      AS user_role, " +
                    "u.register_date             AS user_register_date, " +
                    "u.birth_date                AS user_birth_date, " +
                    "u.name                      AS user_name, " +
                    "u.surname                   AS user_surname, " +
                    "cat.id                      AS category_id, " +
                    "cat.parent_id               AS category_parent_id, " +
                    "cat.alias                   AS category_alias, " +
                    "cat.created                 AS category_created, " +
                    "cat.active                  AS category_active," +
                    "cat_tr.lang                 AS category_translate_lang," +
                    "cat_tr.category_id          AS category_translate_category_id," +
                    "cat_tr.name                 AS category_translate_name," +
                    "cat_tr.intro_text           AS category_translate_intro_text," +
                    "cat_tr.meta_keywords        AS category_translate_meta_keywords," +
                    "cat_tr.meta_description     AS category_translate_meta_description," +
                    "cat_tr.meta_title           AS category_translate_meta_title, " +
                    "cont_type.id                AS content_type_id, " +
                    "cont_type.name              AS content_type_name, " +
                    "cont_type.extra_field_types AS content_type_extra_field_types " +
                    "FROM app.content cont " +
                    "LEFT JOIN app.content_translate cont_tr ON cont_tr.content_id = cont.id " +
                    "LEFT JOIN app.user u ON cont.created_by = u.id " +
                    "LEFT JOIN app.category cat ON cont.category_id = cat.id " +
                    "LEFT JOIN app.category_translate cat_tr " +
                    "ON cont.category_id = cat_tr.category_id AND cat_tr.lang = cont_tr.lang " +
                    "LEFT JOIN app.content_type cont_type ON cont.content_type_id = cont_type.id " +
                    "WHERE TRUE ";

    private static final String GET_FILTERED_BY_ID = "AND cont.id = ? ";
    private static final String GET_FILTERED_BY_ID_AND_PUBLISHED = GET_FILTERED_BY_ID +
            "AND cont.active = 'TRUE'";
    private static final String CREATE =
            "INSERT INTO app.content " +
                    "(content_type_id, category_id, alias, created, created_by, last_modified, " +
                    "publish_up, publish_down,  active, extra_fields, image) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

    private static final String CREATE_TRANSLATE =
            "INSERT INTO app.content_translate " +
                    "(content_id, lang, name, intro_text, full_text, meta_title, meta_description, meta_keywords) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";

    private static final String UPDATE =
            "UPDATE app.content SET " +
                    "content_type_id = ?, " +
                    "category_id = ?, " +
                    "alias = ?, " +
                    "created = ?, " +
                    "created_by = ?, " +
                    "last_modified = ?, " +
                    "publish_up = ?, " +
                    "publish_down = ?, " +
                    "hits = ?, " +
                    "active = ?, " +
                    "extra_fields = ? " +
                    "WHERE id = ? ";

    private static final String UPDATE_IMAGE =
            "UPDATE app.content SET " +
                    "image = ? " +
                    "WHERE id = ? ";

    private static final String UPDATE_TRANSLATE =
            "UPDATE app.content_translate SET " +
                    "lang = ?," +
                    "name = ?," +
                    "intro_text = ?," +
                    "full_text = ?," +
                    "meta_title = ?," +
                    "meta_description = ?," +
                    "meta_keywords = ? " +
                    "WHERE content_id = ? AND lang = ?";

    private static final String DELETE = "DELETE FROM app.content WHERE id = ?";

    @SneakyThrows
    public Optional<Content> get(int id, boolean onlyPublished) {
        Content content = null;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL
                     + (onlyPublished ? GET_FILTERED_BY_ID_AND_PUBLISHED : GET_FILTERED_BY_ID))) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Lang lang = Lang.valueOf(resultSet.getString("content_translate_lang"));
                if (resultSet.isFirst()) {
                    content = DaoBuilder.buildContentFromQuery(resultSet);
                    Optional<Category> category = CategoryDao.getInstance().get(resultSet.getInt("content_category_id"));
                    content.setCategory(category.orElse(null));
                }
                content.getTranslation().put(lang, DaoBuilder.buildContentTranslationFromQuery(resultSet));
            }
        }
        return Optional.ofNullable(content);
    }

    @SneakyThrows
    public List<Content> getAll(DaoFilter.Builder filter) {
        List<Content> contents = new ArrayList<>();
        filter.setLimit(filter.getLimit() * Lang.values().length);
        String stringFilter = filter.addOrdering(ORDERING_BY_ID).build();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL + stringFilter)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            int currentContentId = 0;
            while (resultSet.next()) {
                if (currentContentId != resultSet.getInt("content_id")) {
                    Content content = DaoBuilder.buildContentFromQuery(resultSet);
                    currentContentId = content.getId();
                    Optional<Category> category = CategoryDao.getInstance().get(resultSet.getInt("content_category_id"));
                    content.setCategory(category.orElse(null));
                    contents.add(content);
                }
                Lang lang = Lang.valueOf(resultSet.getString("content_translate_lang"));
                ContentTranslation translation = DaoBuilder.buildContentTranslationFromQuery(resultSet);
                contents.get(contents.size() - 1).getTranslation().put(lang, translation);
            }
        }
        return contents;
    }

    public List<Content> getAll() {
        return getAll(DaoFilter.builder());
    }

    @SneakyThrows
    public Optional<Content> create(Content content) {
        Content createdContent = null;
        Connection connection = ConnectionManager.get();
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement statement1 = connection.prepareStatement(CREATE, RETURN_GENERATED_KEYS);
                 PreparedStatement statement2 = connection.prepareStatement(CREATE_TRANSLATE)) {

                statement1.setInt(1, content.getContentType().getId());
                statement1.setInt(2, content.getCategory().getId());
                statement1.setString(3, content.getAlias());
                statement1.setDate(4, convertToSqlDate(content.getCreated()));
                statement1.setInt(5, content.getCreatedBy().getId());
                statement1.setDate(6, convertToSqlDate(content.getLastModified()));
                statement1.setDate(7, convertToSqlDate(content.getPublishDown()));
                statement1.setDate(8, convertToSqlDate(content.getPublishDown()));
                statement1.setBoolean(9, content.isActive());
                statement1.setObject(10, content.getExtraFields());
                statement1.setString(11, content.getImage());
                statement1.executeUpdate();

                ResultSet generatedKeys = statement1.getGeneratedKeys();
                if (generatedKeys.next()) {
                    createdContent = content;
                    createdContent.setId(generatedKeys.getInt("id"));
                    Map<Lang, ContentTranslation> translation = content.getTranslation();

                    for (Map.Entry<Lang, ContentTranslation> entry : translation.entrySet()) {
                        statement2.setInt(1, createdContent.getId());
                        statement2.setString(2, entry.getKey().name());
                        statement2.setString(3, entry.getValue().getName());
                        statement2.setString(4, entry.getValue().getIntroText());
                        statement2.setString(5, entry.getValue().getFullText());
                        statement2.setString(6, entry.getValue().getMetaTitle());
                        statement2.setString(7, entry.getValue().getMetaDescription());
                        statement2.setString(8, entry.getValue().getMetaKeywords());
                        statement2.executeUpdate();
                    }
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                createdContent = null;
                throw new SQLException(e);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e) {
                throw new SQLException(e);
            }
        }
        return Optional.ofNullable(createdContent);
    }

    @SneakyThrows
    public boolean update(Content content) {
        boolean result = false;
        Connection connection = ConnectionManager.get();
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement statement1 = connection.prepareStatement(UPDATE);
                 PreparedStatement statement2 = connection.prepareStatement(UPDATE_TRANSLATE)) {

                statement1.setInt(1, content.getContentType().getId());
                statement1.setInt(2, content.getCategory().getId());
                statement1.setString(3, content.getAlias());
                statement1.setDate(4, convertToSqlDate(content.getCreated()));
                statement1.setInt(5, content.getCreatedBy().getId());
                statement1.setDate(6, convertToSqlDate(content.getLastModified()));
                statement1.setDate(7, convertToSqlDate(content.getPublishUp()));
                statement1.setDate(8, convertToSqlDate(content.getPublishDown()));
                statement1.setInt(9, content.getHits());
                statement1.setBoolean(10, content.isActive());
                statement1.setObject(11, content.getExtraFields());
                statement1.setInt(12, content.getId());

                if (statement1.executeUpdate() > 0) {
                    Map<Lang, ContentTranslation> translation = content.getTranslation();

                    for (Map.Entry<Lang, ContentTranslation> entry : translation.entrySet()) {
                        statement2.setString(1, entry.getKey().name());
                        statement2.setString(2, entry.getValue().getName());
                        statement2.setString(3, entry.getValue().getIntroText());
                        statement2.setString(4, entry.getValue().getFullText());
                        statement2.setString(5, entry.getValue().getMetaTitle());
                        statement2.setString(6, entry.getValue().getMetaDescription());
                        statement2.setString(7, entry.getValue().getMetaKeywords());
                        statement2.setInt(8, content.getId());
                        statement2.setString(9, entry.getKey().name());
                        result = statement2.executeUpdate() > 0;
                    }

                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                result = false;
                throw new SQLException(e);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e) {
                throw new SQLException(e);
            }
        }
        return result;
    }

    @SneakyThrows
    public boolean updateImage(Content content) {
        boolean result = false;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_IMAGE)) {
            preparedStatement.setString(1, content.getImage());
            preparedStatement.setInt(2, content.getId());
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

    public static ContentDao getInstance() {
        return INSTANCE;
    }
}
