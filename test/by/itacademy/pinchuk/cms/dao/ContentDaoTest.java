package by.itacademy.pinchuk.cms.dao;

import by.itacademy.pinchuk.cms.entity.Content;
import by.itacademy.pinchuk.cms.util.TestData;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ContentDaoTest {

    private final ContentDao instance = ContentDao.getInstance();

    @Test
    public void CheckGet() {
        Optional<Content> actualContent = instance.get(TestData.EXISTING_CONTENT_ID);
        assertTrue(actualContent.isPresent());
    }

    @Test
    public void CheckGetAll() {
        List<Content> contents = instance.getAll();
        assertTrue(contents.size() > 0);
    }

    @Test
    public void CheckCreate() {
        Optional<Content> createdContent = instance.create(TestData.buildContent());
        assertTrue(createdContent.isPresent());
    }

    @Test
    public void CheckUpdate() {
        Content content = TestData.buildContent(TestData.EXISTING_CATEGORY_ID);
        assertTrue(instance.update(content));
    }

    @Test
    public void CheckDelete() {
        assertTrue(instance.delete(TestData.DELETE_CONTENT_ID));
    }

    @Test
    public void getInstance() {
        assertNotNull(ContentDao.getInstance());
    }
}