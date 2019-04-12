package by.itacademy.pinchuk.cms.util;

import by.itacademy.pinchuk.cms.entity.Category;
import by.itacademy.pinchuk.cms.entity.Comment;
import by.itacademy.pinchuk.cms.entity.CommentStatus;
import by.itacademy.pinchuk.cms.entity.Content;
import by.itacademy.pinchuk.cms.entity.ContentType;
import by.itacademy.pinchuk.cms.entity.Lang;
import by.itacademy.pinchuk.cms.entity.Tag;
import by.itacademy.pinchuk.cms.entity.User;
import by.itacademy.pinchuk.cms.entity.UserRole;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;

@UtilityClass
public class TestData {

    // AppConfig testing data
    public static final String VALID_DB_URL_KEY = "db.url";

    // UserDao testing data
    public static final int EXISTING_USER_ID = 1;
    public static final int DELETE_USER_ID = 2;
    public static final String USER_VALID_USERNAME = "TestUserName";
    public static final String USER_VALID_EMAIL = "test@test.by";
    public static final String USER_VALID_PHONE = "+375447949471";
    public static final String USER_VALID_PASSWORD = "test-pass";
    public static final Boolean USER_VALID_ACTIVE = true;
    public static final String USER_VALID_ROLE = "administrator";
    public static final LocalDate USER_VALID_REGISTER_DATE = LocalDate.now();
    public static final LocalDate USER_VALID_BIRTH_DATE = LocalDate.parse("1985-01-01");
    public static final String USER_VALID_NAME = "TestName";
    public static final String USER_VALID_SURNAME = "TestSurname";

    // TagDao testing data
    public static final int EXISTING_TAG_ID = 1;
    public static final int DELETE_TAG_ID = 2;
    public static final Lang TAG_VALID_LANG = Lang.RU;
    public static final String TAG_VALID_VALUE = "testTeg1";

    // ContentTypeDao testing data
    public static final int EXISTING_CONTENT_TYPE_ID = 1;
    public static final int DELETE_CONTENT_TYPE_ID = 2;
    public static final String VALID_CONTENT_TYPE_NAME = "TestContentType";
    public static final String VALID_CONTENT_TYPE_EXTRA_FIELDS = null;

    // CommentDao testing data
    public static final int EXISTING_COMMENT_ID = 1;
    public static final int DELETE_COMMENT_ID = 2;
    public static final int COMMENT_VALID_CONTENT_ID = 1;
    public static final LocalDateTime COMMENT_VALID_CREATED = LocalDateTime.now();
    public static final String COMMENT_FULL_TEXT = "Test comment full text";
    public static final CommentStatus COMMENT_STATUS = CommentStatus.PUBLISHED;

    // CategoryDao testing data
    public static final int EXISTING_CATEGORY_ID = 1;
    public static final int DELETE_CATEGORY_ID = 2;
    public static final int CATEGORY_VALID_PARENT_ID = 0;
    public static final String CATEGORY_VALID_ALIAS = "TestCategoryAlias";
    public static final LocalDate CATEGORY_VALID_CREATED = LocalDate.now();
    public static final boolean CATEGORY_VALID_ACTIVE = true;
    public static final Lang CATEGORY_VALID_LANG = Lang.RU;
    public static final String CATEGORY_VALID_NAME = "TestCategoryName";
    public static final String CATEGORY_VALID_INTRO_TEXT = "TestCategoryIntroText";
    public static final String CATEGORY_VALID_META_TITLE = "TestCategoryMetaTitle";
    public static final String CATEGORY_VALID_META_DESCRIPTION = "TestCategoryMetaDescription";
    public static final String CATEGORY_VALID_META_KEYWORDS = "TestCategoryKeyword1, TestCategoryKeyword2";

    // Content testing data
    public static final int EXISTING_CONTENT_ID = 1;
    public static final int DELETE_CONTENT_ID = 2;
    public static final String CONTENT_VALID_ALIAS = "TestContentAlias";
    public static final LocalDate CONTENT_VALID_CREATED = LocalDate.now();
    public static final LocalDate CONTENT_VALID_LAST_MODIFIED = LocalDate.now();
    public static final LocalDate CONTENT_VALID_PUBLISH_UP = LocalDate.now();
    public static final LocalDate CONTENT_VALID_PUBLISH_DOWN = LocalDate.now();
    public static final int CONTENT_VALID_HITS = 0;
    public static final boolean CONTENT_VALID_ACTIVE = true;
    public static final String CONTENT_VALID_EXTRA_FIELDS = null;
    public static final String CONTENT_VALID_IMAGE = null;
    public static final String CONTENT_VALID_NAME = "TestContentName";
    public static final String CONTENT_VALID_INTRO_TEXT = "TestContentIntroText";
    public static final String CONTENT_VALID_FULL_TEXT = "TestContentFullText";
    public static final String CONTENT_VALID_META_TITLE = "TestContentMetaTitle";
    public static final String CONTENT_VALID_META_DESCRIPTION = "TestContentMetaDescription";
    public static final String CONTENT_VALID_META_KEYWORDS = "TestContentMetaKeyWords";
    private static final Lang CONTENT_VALID_LANG = Lang.RU;

    public static User buildUser() {
        return User.builder()
                .username(USER_VALID_USERNAME)
                .email(USER_VALID_EMAIL)
                .phone(USER_VALID_PHONE)
                .password(USER_VALID_PASSWORD)
                .active(USER_VALID_ACTIVE)
                .role(UserRole.valueOf(USER_VALID_ROLE))
                .registerDate(USER_VALID_REGISTER_DATE)
                .birthDate(USER_VALID_BIRTH_DATE)
                .name(USER_VALID_NAME)
                .surname(USER_VALID_SURNAME)
                .build();
    }

    public User buildUser(int id) {
        User user = buildUser();
        user.setId(id);
        return user;
    }

    public Tag buildTag() {
        return Tag.builder()
                .value(TAG_VALID_VALUE)
                .lang(TAG_VALID_LANG)
                .build();
    }

    public Tag buildTag(int id) {
        Tag tag = buildTag();
        tag.setId(id);
        return tag;
    }

    public ContentType buildContentType() {
        return ContentType.builder()
                .name(VALID_CONTENT_TYPE_NAME)
                .extraFieldTypes(VALID_CONTENT_TYPE_EXTRA_FIELDS)
                .build();
    }

    public ContentType buildContentType(int id) {
        ContentType contentType = buildContentType();
        contentType.setId(id);
        return contentType;
    }

    public static Comment buildComment() {
        return Comment.builder()
                .contentId(COMMENT_VALID_CONTENT_ID)
                .user(buildUser(EXISTING_USER_ID))
                .created(COMMENT_VALID_CREATED)
                .fullText(COMMENT_FULL_TEXT)
                .status(COMMENT_STATUS)
                .build();
    }

    public static Comment buildComment(int id) {
        Comment contentType = buildComment();
        contentType.setId(id);
        return contentType;
    }

    public static Category buildCategory() {
        return Category.builder()
                .parentId(CATEGORY_VALID_PARENT_ID)
                .alias(CATEGORY_VALID_ALIAS)
                .created(CATEGORY_VALID_CREATED)
                .active(CATEGORY_VALID_ACTIVE)
                // TODO: 02.04.2019 Надо добавить построение переводов!
                .build();
    }

    public static Category buildCategory(int id) {
        Category category = buildCategory();
        category.setId(id);
        return category;
    }

    public static Content buildContent() {
        return Content.builder()
                .id(EXISTING_CONTENT_ID)
                .contentType(buildContentType(EXISTING_CONTENT_ID))
                .category(buildCategory(EXISTING_CATEGORY_ID))
                .alias(CONTENT_VALID_ALIAS)
                .created(CONTENT_VALID_CREATED)
                .createdBy(buildUser(EXISTING_USER_ID))
                .lastModified(CONTENT_VALID_LAST_MODIFIED)
                .publishUp(CONTENT_VALID_PUBLISH_UP)
                .publishDown(CONTENT_VALID_PUBLISH_DOWN)
                .hits(CONTENT_VALID_HITS)
                .active(CONTENT_VALID_ACTIVE)
                .extraFields(CONTENT_VALID_EXTRA_FIELDS)
                .image(CONTENT_VALID_IMAGE)
                // TODO: 02.04.2019 Надо добавить построение переводов!
                .build();
    }

    public static Content buildContent(int id) {
        Content content = buildContent();
        content.setId(id);
        return content;
    }
}
