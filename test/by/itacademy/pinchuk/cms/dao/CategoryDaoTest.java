package by.itacademy.pinchuk.cms.dao;

import by.itacademy.pinchuk.cms.entity.Category;
import by.itacademy.pinchuk.cms.util.TestData;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CategoryDaoTest {

    private final CategoryDao instance = CategoryDao.getInstance();

    @Test
    public void CheckGet() {
        Optional<Category> actualCategory = instance.get(TestData.EXISTING_CATEGORY_ID);
        assertTrue(actualCategory.isPresent());
    }

    @Test
    public void CheckGetAll() {
        List<Category> categories = instance.getAll();
        assertTrue(categories.size() > 0);
    }

    @Test
    public void CheckCreate() {
        Optional<Category> createdCategory = instance.create(TestData.buildCategory());
        assertTrue(createdCategory.isPresent());
    }

    @Test
    public void CheckUpdate() {
        Category category = TestData.buildCategory(TestData.EXISTING_CATEGORY_ID);
        assertTrue(instance.update(category));
    }

    @Test
    public void CheckDelete() {
        assertTrue(instance.delete(TestData.DELETE_CATEGORY_ID));
    }

    @Test
    public void getInstance() {
        assertNotNull(CategoryDao.getInstance());
    }
}