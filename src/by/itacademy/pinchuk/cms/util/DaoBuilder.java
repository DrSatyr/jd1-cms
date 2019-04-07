package by.itacademy.pinchuk.cms.util;

import by.itacademy.pinchuk.cms.entity.Category;
import by.itacademy.pinchuk.cms.entity.CategoryTranslation;
import by.itacademy.pinchuk.cms.entity.Comment;
import by.itacademy.pinchuk.cms.entity.CommentStatus;
import by.itacademy.pinchuk.cms.entity.Content;
import by.itacademy.pinchuk.cms.entity.ContentTranslation;
import by.itacademy.pinchuk.cms.entity.ContentType;
import by.itacademy.pinchuk.cms.entity.Lang;
import by.itacademy.pinchuk.cms.entity.Tag;
import by.itacademy.pinchuk.cms.entity.User;
import lombok.experimental.UtilityClass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static by.itacademy.pinchuk.cms.util.DateHelper.convertToLocalDate;
import static by.itacademy.pinchuk.cms.util.DateHelper.convertToLocalDateTime;

@UtilityClass
public class DaoBuilder {

    public static Content buildContentFromQuery(ResultSet resultSet) throws SQLException {
        return Content.builder()
                .id(resultSet.getInt("content_id"))
                .contentType(buildContentTypeFromQuery(resultSet))
                .category(buildCategoryFromQuery(resultSet))
                .alias(resultSet.getString("content_alias"))
                .created((convertToLocalDate(resultSet.getDate("content_created"))))
                .createdBy(buildUserFromQuery(resultSet))
                .lastModified(convertToLocalDate(resultSet.getDate("content_last_modified")))
                .publishUp(convertToLocalDate(resultSet.getDate("content_publish_up")))
                .publishUp(convertToLocalDate(resultSet.getDate("content_publish_down")))
                .hits(resultSet.getInt("content_hits"))
                .active(resultSet.getBoolean("content_active"))
                .extraFields(resultSet.getString("content_extra_fields"))
                .image(resultSet.getString("content_image"))
                .translation(new HashMap<>())
                .build();
    }

    public static ContentTranslation buildContentTranslationFromQuery(ResultSet resultSet) throws SQLException {
        return ContentTranslation.builder()
                .name(resultSet.getString("content_translate_name"))
                .introText(resultSet.getString("content_translate_intro_text"))
                .fullText(resultSet.getString("content_translate_full_text"))
                .metaTitle(resultSet.getString("content_translate_meta_title"))
                .metaDescription(resultSet.getString("content_translate_meta_description"))
                .metaKeywords(resultSet.getString("content_translate_meta_keywords"))
                .build();
    }

    public static Comment buildCommentFromQuery (ResultSet resultSet) throws SQLException {
        return Comment.builder()
                .id(resultSet.getInt("comment_id"))
                .contentId(resultSet.getInt("comment_content_id"))
                .created(convertToLocalDateTime(resultSet.getTimestamp("comment_created")))
                .fullText(resultSet.getString("comment_full_text"))
                .status(CommentStatus.valueOf(resultSet.getString("comment_status")))
                .user(buildUserFromQuery(resultSet))
                .build();
    }

    public static Tag buildTagFromQuery(ResultSet resultSet) throws SQLException {
        return Tag.builder()
                .id(resultSet.getInt("tag_id"))
                .lang(Lang.valueOf(resultSet.getString("tag_lang")))
                .value(resultSet.getString("tag_value"))
                .build();
    }

    public static ContentType buildContentTypeFromQuery(ResultSet resultSet) throws SQLException {
        return ContentType.builder()
                .id(resultSet.getInt("content_type_id"))
                .name(resultSet.getString("content_type_name"))
                .extraFieldTypes(resultSet.getString("content_type_extra_field_types"))
                .build();
    }

    public static User buildUserFromQuery(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("user_id"))
                .username(resultSet.getString("user_username"))
                .email(resultSet.getString("user_email"))
                .phone(resultSet.getString("user_phone"))
                .password(resultSet.getString("user_password"))
                .active(resultSet.getBoolean("user_active"))
                .role(resultSet.getString("user_role"))
                .registerDate(convertToLocalDate(resultSet.getDate("user_register_date")))
                .birthDate(convertToLocalDate(resultSet.getDate("user_birth_date")))
                .name(resultSet.getString("user_name"))
                .surname(resultSet.getString("user_surname"))
                .build();
    }

    public static Category buildCategoryFromQuery(ResultSet resultSet) throws SQLException {
        return Category.builder()
                .id(resultSet.getInt("category_id"))
                .parentId(resultSet.getInt("category_parent_id"))
                .alias(resultSet.getString("category_alias"))
                .created(convertToLocalDate(resultSet.getDate("category_created")))
                .active(resultSet.getBoolean("category_active"))
                .translation(new HashMap<>())
                .build();
    }

    public static CategoryTranslation buildCategoryTranslationFromQuery(ResultSet resultSet) throws SQLException {
        return CategoryTranslation.builder()
                .name(resultSet.getString("category_translate_name"))
                .introText(resultSet.getString("category_translate_intro_text"))
                .metaTitle(resultSet.getString("category_translate_meta_title"))
                .metaDescription(resultSet.getString("category_translate_meta_description"))
                .metaKeywords(resultSet.getString("category_translate_meta_keywords"))
                .build();
    }
}
