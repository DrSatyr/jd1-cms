package by.itacademy.pinchuk.cms.dao;

import by.itacademy.pinchuk.cms.entity.Comment;
import by.itacademy.pinchuk.cms.util.TestData;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CommentDaoTest {

    private final CommentDao instance = CommentDao.getInstance();

    @Test
    public void CheckGet() {
        Optional<Comment> actualComment = instance.get(TestData.EXISTING_COMMENT_ID);
        assertTrue(actualComment.isPresent());
    }

    @Test
    public void CheckGetAll() {
        List<Comment> comments = instance.getAll();
        assertTrue(comments.size() > 0);
    }

    @Test
    public void CheckCreate() {
        Optional<Comment> createdComment = instance.create(TestData.buildComment());
        assertTrue(createdComment.isPresent());
    }

    @Test
    public void CheckUpdate() {
        Comment comment = TestData.buildComment(TestData.EXISTING_COMMENT_ID);
        assertTrue(instance.update(comment));
    }

    @Test
    public void CheckDelete() {
        assertTrue(instance.delete(TestData.DELETE_COMMENT_ID));
    }

    @Test
    public void getInstance() {
        assertNotNull(CommentDao.getInstance());
    }
}