package by.itacademy.pinchuk.cms.dao;

import by.itacademy.pinchuk.cms.entity.Tag;
import by.itacademy.pinchuk.cms.util.TestData;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TagDaoTest {

    private final TagDao instance = TagDao.getInstance();

    @Test
    public void CheckGet() {
        Optional<Tag> actualTag = instance.get(TestData.EXISTING_TAG_ID);
        assertTrue(actualTag.isPresent());
    }

    @Test
    public void CheckGetAll() {
        List<Tag> tags = instance.getAll();
        assertTrue(tags.size() > 0);
    }

    @Test
    public void CheckCreate() {
        Optional<Tag> createdTag = instance.create(TestData.buildTag());
        assertTrue(createdTag.isPresent());
    }

    @Test
    public void CheckUpdate() {
        Tag tag = TestData.buildTag(TestData.EXISTING_TAG_ID);
        assertTrue(instance.update(tag));
    }

    @Test
    public void CheckDelete() {
        assertTrue(instance.delete(TestData.DELETE_TAG_ID));
    }

    @Test
    public void getInstance() {
        assertNotNull(TagDao.getInstance());
    }
}