package by.itacademy.pinchuk.cms.service;

import by.itacademy.pinchuk.cms.dao.Dao;
import by.itacademy.pinchuk.cms.dao.UserDao;
import by.itacademy.pinchuk.cms.dto.UserDto;
import by.itacademy.pinchuk.cms.entity.User;
import by.itacademy.pinchuk.cms.mapper.Mapper;
import by.itacademy.pinchuk.cms.mapper.UserMapper;
import by.itacademy.pinchuk.cms.util.DaoFilter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService implements Service<User> {

    private static final UserService INSTANCE = new UserService();
    private Dao<User> dao = UserDao.getInstance();
    private Mapper<User, UserDto> userMapper = UserMapper.getInstance();

    public Optional<UserDto> get(int id, boolean onlyPublished) {
        return dao.get(id, onlyPublished).map(userMapper::entityToDto);
    }

    public Optional<UserDto> get(int id) {
        return get(id, false);
    }

    public List<UserDto> getAll(DaoFilter.Builder filter) {
        return dao.getAll(filter).stream()
                .map(userMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> getAll() {
        return getAll(DaoFilter.builder());
    }

    public List<UserDto> getAllPublished() {
        return getAll();
    }

    public Optional<User> create(User user) {
        return dao.create(user);
    }

    public boolean update(User user) {
        return dao.update(user);
    }

    public boolean updatePassword(Integer id, String password) {
         return UserDao.getInstance().updatePassword(id, password);
    }

    public boolean delete(int id) {
        return dao.delete(id);
    }

    public Optional<UserDto> login(String username, String password) {
        UserDto user = null;
        DaoFilter.Builder filter = DaoFilter.builder()
                .addCondition(UserDao.FILTER_BY_USERNAME, DaoFilter.MatchType.EQ, username)
                .addLogic(DaoFilter.Logic.AND)
                .addCondition(UserDao.FILTER_BY_ACTIVE, DaoFilter.MatchType.EQ, "TRUE");
        List<UserDto> users = getAll(filter);
        if (users.size() > 0) {
           UserDto receivedUser = users.get(0);
           user = receivedUser.getPassword().equals(password) ? receivedUser : null;
        }
        return Optional.ofNullable(user);
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
