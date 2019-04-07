package by.itacademy.pinchuk.cms.dao;

import by.itacademy.pinchuk.cms.entity.User;
import by.itacademy.pinchuk.cms.util.TestData;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UserDaoTest {

    private final UserDao instance = UserDao.getInstance();

    @Before
    public void prepareTestingData() {
        // TODO: 21.03.2019 Make data preparing more beautiful with this method!
    }

    @Test
    public void checkCreate() {
        User testUser = TestData.buildUser();
        testUser.setUsername("Create" + testUser.getName());
        testUser.setEmail("Create" + testUser.getEmail());
        Optional<User> optionalUser = instance.create(testUser);
        assertTrue(optionalUser.isPresent());
        assertNotNull(optionalUser.map(User::getId).orElse(null));
    }

    @Test
    public void checkGet() {
        Optional<User> actualUser = instance.get(TestData.EXISTING_USER_ID);
        assertTrue(actualUser.isPresent());
    }

    @Test
    public void CheckGetAll() {
        List<User> users = instance.getAll();
        assertTrue(users.size() > 0);
    }

    @Test
    public void checkUpdate() {
        User testUser = TestData.buildUser(TestData.EXISTING_USER_ID);
        testUser.setUsername("Update" + testUser.getName());
        testUser.setEmail("Update" + testUser.getEmail());
        assertTrue(instance.update(testUser));
    }

    @Test
    public void checkDelete() {
        assertTrue(instance.delete(TestData.DELETE_USER_ID));
    }

    @Test
    public void checkGetInstance() {
        assertNotNull(UserDao.getInstance());
    }
}