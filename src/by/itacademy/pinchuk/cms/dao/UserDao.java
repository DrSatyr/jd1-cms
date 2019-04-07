package by.itacademy.pinchuk.cms.dao;

import by.itacademy.pinchuk.cms.entity.User;
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
public class UserDao extends BaseDao<User> implements Dao<User>{

    public static final String FILTER_BY_ID = "id";
    public static final String FILTER_BY_ACTIVE = "active";
    public static final String FILTER_BY_ROLE = "role";
    public static final String FILTER_BY_REGISTER_DATE = "register_date";
    public static final String FILTER_BY_BIRTH_DATE = "birth_date";
    public static final String FILTER_BY_NAME = "name";
    public static final String FILTER_BY_SURNAME = "surname";

    private static final UserDao INSTANCE = new UserDao();
    private static final String GET_ALL =
            "SELECT " +
                    "id AS user_id, " +
                    "username AS user_username, " +
                    "email AS user_email, " +
                    "phone AS user_phone, " +
                    "password AS user_password, " +
                    "active AS user_active, " +
                    "role AS user_role, " +
                    "register_date AS user_register_date, " +
                    "birth_date AS user_birth_date, " +
                    "name AS user_name, " +
                    "surname AS user_surname " +
                    "FROM app.user " +
                    "WHERE TRUE ";
    private static final String GET_FILTERED_BY_ID = "AND id = ? ";
    private static final String GET_FILTERED_BY_ID_AND_PUBLISHED = GET_FILTERED_BY_ID + "AND active = 'TRUE' ";
    private static final String DELETE = "DELETE FROM app.user WHERE id = ?";
    private static final String CREATE = "INSERT INTO app.user " +
            "(username, email, phone, password, active, role, register_date, birth_date, name, surname) " +
            "VALUES " +
            "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE =
            "UPDATE app.user SET " +
                    "username = ?, " +
                    "email = ?, " +
                    "phone = ?, " +
                    "password = ?, " +
                    "active = ?, " +
                    "role = ?, " +
                    "register_date = ?, " +
                    "birth_date = ?, " +
                    "name = ?, " +
                    "surname = ? " +
                    "WHERE " +
                    "id = ?;";

    @SneakyThrows
    public Optional<User> get(int id, boolean onlyPublished) {
        User user = null;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL
                     + (onlyPublished ? GET_FILTERED_BY_ID_AND_PUBLISHED : GET_FILTERED_BY_ID))) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = DaoBuilder.buildUserFromQuery(resultSet);
            }
        }
        return Optional.ofNullable(user);
    }

    @SneakyThrows
    public List<User> getAll(DaoFilter.Builder filter) {
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL + filter.build())) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(DaoBuilder.buildUserFromQuery(resultSet));
            }
        }
        return users;
    }

    public List<User> getAll() {
        return getAll(DaoFilter.builder());
    }

    @SneakyThrows
    public Optional<User> create(User user) {
        User createdUser = null;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE, RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPhone());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setBoolean(5, user.getActive());
            preparedStatement.setString(6, user.getRole());
            preparedStatement.setDate(7, (java.sql.Date.valueOf(user.getRegisterDate())));
            preparedStatement.setDate(8, (java.sql.Date.valueOf(user.getBirthDate())));
            preparedStatement.setString(9, user.getName());
            preparedStatement.setString(10, user.getSurname());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                createdUser = user;
                createdUser.setId(generatedKeys.getInt("id"));
            }
        }
        return Optional.ofNullable(createdUser);
    }

    @SneakyThrows
    public boolean update(User user) {
        boolean result = false;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPhone());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setBoolean(5, user.getActive());
            preparedStatement.setString(6, user.getRole());
            preparedStatement.setDate(7, (java.sql.Date.valueOf(user.getRegisterDate())));
            preparedStatement.setDate(8, (java.sql.Date.valueOf(user.getBirthDate())));
            preparedStatement.setString(9, user.getName());
            preparedStatement.setString(10, user.getSurname());
            preparedStatement.setInt(11, user.getId());

            int affectedRows = preparedStatement.executeUpdate();
            result = affectedRows > 0;
        }
        return result;
    }

    @SneakyThrows
    public boolean delete(int id) {
        boolean result = false;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, id);
            if (preparedStatement.executeUpdate() > 0) {
                result = true;
            }
        }
        return result;
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
