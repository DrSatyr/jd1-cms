package by.itacademy.pinchuk.cms.dao;

import by.itacademy.pinchuk.cms.entity.ContentType;
import by.itacademy.pinchuk.cms.util.TestData;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ContentTypeDaoTest {

    private final ContentTypeDao instance = ContentTypeDao.getInstance();

    @Test
    public void CheckGet() {
        Optional<ContentType> actualContentType = instance.get(TestData.EXISTING_CONTENT_TYPE_ID);
        assertTrue(actualContentType.isPresent());
    }

    @Test
    public void CheckGetAll() {
        List<ContentType> contentTypes = instance.getAll();
        assertTrue(contentTypes.size() > 0);
    }

    @Test
    public void CheckCreate() {
        Optional<ContentType> createdContentType = instance.create(TestData.buildContentType());
        assertTrue(createdContentType.isPresent());
    }

    @Test
    public void CheckUpdate() {
        ContentType contentType = TestData.buildContentType(TestData.EXISTING_CONTENT_TYPE_ID);
        assertTrue(instance.update(contentType));
    }

    @Test
    public void CheckDelete() {
        assertTrue(instance.delete(TestData.DELETE_CONTENT_TYPE_ID));
    }

    @Test
    public void getInstance() {
        assertNotNull(ContentTypeDao.getInstance());
    }
}