package by.itacademy.pinchuk.cms.dao;

import by.itacademy.pinchuk.cms.entity.Category;
import by.itacademy.pinchuk.cms.entity.CategoryTranslation;
import by.itacademy.pinchuk.cms.entity.Lang;
import by.itacademy.pinchuk.cms.util.ConnectionManager;
import by.itacademy.pinchuk.cms.util.DaoBuilder;
import by.itacademy.pinchuk.cms.util.DaoFilter;
import by.itacademy.pinchuk.cms.util.DateHelper;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class CategoryDao extends BaseDao<Category> implements Dao<Category> {

    public static final String FILTER_BY_PARENT_ID = "c.parent_id";
    public static final String FILTER_BY_CREATED = "c.created";
    public static final String FILTER_BY_ACTIVE = "c.active";
    public static final String FILTER_BY_LANG = "t.lang";
    public static final String ORDERING_BY_ID = "c.id";

    private static final CategoryDao INSTANCE = new CategoryDao();
    private static final String GET_ALL =
            "SELECT " +
                    "c.id AS category_id," +
                    "c.parent_id AS category_parent_id, " +
                    "c.alias AS category_alias, " +
                    "c.created AS category_created, " +
                    "c.active AS category_active, " +
                    "t.lang AS category_translate_lang, " +
                    "t.name AS category_translate_name, " +
                    "t.intro_text AS category_translate_intro_text, " +
                    "t.meta_title AS category_translate_meta_title, " +
                    "t.meta_description AS category_translate_meta_description, " +
                    "t.meta_keywords AS category_translate_meta_keywords " +
                    "FROM app.category c JOIN app.category_translate t " +
                    "ON t.category_id = c.id " +
                    "WHERE TRUE ";
    private static final String GET_ALL_ORDER_BY_ID = "ORDER BY category_id ";
    private static final String GET_FILTERED_BY_ID = " AND c.id = ? ";
    private static final String GET_FILTERED_BY_ID_AND_PUBLISHED = GET_FILTERED_BY_ID +
            "AND c.active = 'TRUE' ";
    private static final String CREATE =
            "INSERT INTO app.category " +
                    "(parent_id, alias, created, active) " +
                    "VALUES (?, ?, ?, ?) ";
    private static final String CREATE_TRANSLATE =
            "INSERT INTO app.category_translate " +
                    "(category_id, lang, name, intro_text, meta_title, meta_description, meta_keywords) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?) ";
    private static final String UPDATE =
            "UPDATE app.category SET " +
                    "parent_id = ?," +
                    "alias = ?," +
                    "created = ?," +
                    "active = ?" +
                    "WHERE id = ? ";
    private static final String UPDATE_TRANSLATE =
            "UPDATE app.category_translate SET " +
                    "lang = ?," +
                    "name = ?," +
                    "intro_text = ?," +
                    "meta_title = ?," +
                    "meta_description = ?," +
                    "meta_keywords = ? " +
                    "WHERE category_id = ? AND lang = ? ";
    private static final String DELETE = "DELETE FROM app.category WHERE id = ?";

    @SneakyThrows
    public Optional<Category> get(int id, boolean onlyPublished) {
        Category category = null;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL +
                     (onlyPublished ? GET_FILTERED_BY_ID_AND_PUBLISHED : GET_FILTERED_BY_ID))) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Lang lang = Lang.valueOf(resultSet.getString("category_translate_lang"));
                if (resultSet.isFirst()) {
                    category = DaoBuilder.buildCategoryFromQuery(resultSet);
                }
                category.getTranslation().put(lang, DaoBuilder.buildCategoryTranslationFromQuery(resultSet));
            }
        }
        return Optional.ofNullable(category);
    }

    @SneakyThrows
    public List<Category> getAll(DaoFilter.Builder filter) {
        filter.setLimit(filter.getLimit() * Lang.values().length);
        String stringFilter = filter.addOrdering(ORDERING_BY_ID).build();
        List<Category> categories = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL + stringFilter)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            int currentCategoryId = 0;
            while (resultSet.next()) {
                if (currentCategoryId != resultSet.getInt("category_id")) {
                    Category category = DaoBuilder.buildCategoryFromQuery(resultSet);
                    currentCategoryId = category.getId();
                    categories.add(category);
                }
                Lang lang = Lang.valueOf(resultSet.getString("category_translate_lang"));
                CategoryTranslation translation = DaoBuilder.buildCategoryTranslationFromQuery(resultSet);
                categories.get(categories.size() - 1).getTranslation().put(lang, translation);
            }
        }
        return categories;
    }

    public List<Category> getAll() {
        return getAll(DaoFilter.builder());
    }

    @SneakyThrows
    public Optional<Category> create(Category category) {
        Category createdCategory = null;
        Connection connection = ConnectionManager.get();
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement statement1 = connection.prepareStatement(CREATE, RETURN_GENERATED_KEYS);
                 PreparedStatement statement2 = connection.prepareStatement(CREATE_TRANSLATE)) {

                statement1.setInt(1, Objects.nonNull(category.getParentId())
                        ? category.getParentId()
                        : 0);
                statement1.setString(2, category.getAlias());
                statement1.setDate(3, DateHelper.convertToSqlDate(category.getCreated()));
                statement1.setBoolean(4, category.isActive());
                statement1.executeUpdate();

                ResultSet generatedKeys = statement1.getGeneratedKeys();
                if (generatedKeys.next()) {
                    createdCategory = category;
                    createdCategory.setId(generatedKeys.getInt("id"));
                    Map<Lang, CategoryTranslation> translation = category.getTranslation();

                    for (Map.Entry<Lang, CategoryTranslation> entry : translation.entrySet()) {
                        statement2.setInt(1, createdCategory.getId());
                        statement2.setString(2, entry.getKey().name());
                        statement2.setString(3, entry.getValue().getName());
                        statement2.setString(4, entry.getValue().getIntroText());
                        statement2.setString(5, entry.getValue().getMetaTitle());
                        statement2.setString(6, entry.getValue().getMetaDescription());
                        statement2.setString(7, entry.getValue().getMetaKeywords());
                        statement2.executeUpdate();
                    }
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                createdCategory = null;
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
        return Optional.ofNullable(createdCategory);
    }

    @SneakyThrows
    public boolean update(Category category) {
        boolean result = false;
        Connection connection = ConnectionManager.get();
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement statement1 = connection.prepareStatement(UPDATE);
                 PreparedStatement statement2 = connection.prepareStatement(UPDATE_TRANSLATE)) {

                statement1.setInt(1, Objects.nonNull(category.getParentId())
                        ? category.getParentId()
                        : 0);
                statement1.setString(2, category.getAlias());
                statement1.setDate(3, DateHelper.convertToSqlDate(category.getCreated()));
                statement1.setBoolean(4, category.isActive());
                statement1.setInt(5, category.getId());

                if (statement1.executeUpdate() > 0) {
                    Map<Lang, CategoryTranslation> translation = category.getTranslation();

                    for (Map.Entry<Lang, CategoryTranslation> entry : translation.entrySet()) {
                        statement2.setString(1, entry.getKey().name());
                        statement2.setString(2, entry.getValue().getName());
                        statement2.setString(3, entry.getValue().getIntroText());
                        statement2.setString(4, entry.getValue().getMetaTitle());
                        statement2.setString(5, entry.getValue().getMetaDescription());
                        statement2.setString(6, entry.getValue().getMetaKeywords());
                        statement2.setInt(7, category.getId());
                        statement2.setString(8, entry.getKey().name());
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

    public static CategoryDao getInstance() {
        return INSTANCE;
    }
}
