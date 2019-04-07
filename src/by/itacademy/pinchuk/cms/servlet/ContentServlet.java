package by.itacademy.pinchuk.cms.servlet;

import by.itacademy.pinchuk.cms.action.Action;
import by.itacademy.pinchuk.cms.action.ActionFabric;
import by.itacademy.pinchuk.cms.dao.CategoryDao;
import by.itacademy.pinchuk.cms.dao.ContentTypeDao;
import by.itacademy.pinchuk.cms.dto.CategoryDto;
import by.itacademy.pinchuk.cms.dto.CommentDto;
import by.itacademy.pinchuk.cms.dto.ContentTypeDto;
import by.itacademy.pinchuk.cms.dto.TagDto;
import by.itacademy.pinchuk.cms.entity.Category;
import by.itacademy.pinchuk.cms.entity.Content;
import by.itacademy.pinchuk.cms.entity.ContentTranslation;
import by.itacademy.pinchuk.cms.entity.ContentType;
import by.itacademy.pinchuk.cms.entity.Lang;
import by.itacademy.pinchuk.cms.entity.User;
import by.itacademy.pinchuk.cms.service.CategoryService;
import by.itacademy.pinchuk.cms.service.CommentService;
import by.itacademy.pinchuk.cms.service.ContentService;
import by.itacademy.pinchuk.cms.service.ContentTypeService;
import by.itacademy.pinchuk.cms.service.Service;
import by.itacademy.pinchuk.cms.service.TagService;
import by.itacademy.pinchuk.cms.util.ReqParamUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@WebServlet("/app/content")
public class ContentServlet extends HttpServlet {

    private static final String BASE_VIEW = "content";
    private Service<Content> contentService = ContentService.getInstance();
    private TagService tagService = TagService.getInstance();
    private CommentService commentService = CommentService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestId = req.getParameter("id");
        String requestAction = req.getParameter("action");
        Lang lang = Lang.valueOf(req.getSession().getAttribute("lang").toString());

        if (ReqParamUtils.notSet(requestAction) && ReqParamUtils.isValidInt(requestId)) {
            Integer id = Integer.valueOf(requestId);
            List<CommentDto> comments = commentService.getRelatedToContent(id);
            req.setAttribute("comments", comments);
            List<TagDto> relatedTags = tagService.getRelatedToContent(id, lang);
            req.setAttribute("tags", relatedTags);
        }

        if (Action.CREATE_ACTION.equals(requestAction) || Action.UPDATE_ACTION.equals(requestAction)) {
            List<CategoryDto> categories = CategoryService.getInstance().getAll();
            req.setAttribute("allCategories", categories);
            List<ContentTypeDto> contentTypes = ContentTypeService.getInstance().getAll();
            req.setAttribute("contentTypes", contentTypes);
        }

        ActionFabric.get(req, resp).execute(contentService, BASE_VIEW);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String requestAction = ReqParamUtils.getString(req.getParameter("action"));

        Map<Lang, ContentTranslation> translation = new HashMap<>();
        for (Lang lang : Lang.values()) {
            ContentTranslation contentTranslation = ContentTranslation.builder()
                    .name(ReqParamUtils.getString(req.getParameter("name" + lang.name())))
                    .introText(ReqParamUtils.getString(req.getParameter("introText" + lang.name())))
                    .fullText(ReqParamUtils.getString(req.getParameter("fullText" + lang.name())))
                    .metaTitle(ReqParamUtils.getString(req.getParameter("metaTitle" + lang.name())))
                    .metaDescription(ReqParamUtils.getString(req.getParameter("metaDescription" + lang.name())))
                    .metaKeywords(ReqParamUtils.getString(req.getParameter("metaKeywords" + lang.name())))
                    .build();
            translation.put(lang, contentTranslation);
        }

        Integer categoryId = ReqParamUtils.getInt(req.getParameter("CategoryId"));
        Category category = Objects.nonNull(categoryId) ?
                CategoryDao.getInstance().get(categoryId).orElse(null)
                : null;

        Integer contentTypeId = ReqParamUtils.getInt(req.getParameter("contentTypeId"));
        ContentType contentType = Objects.nonNull(contentTypeId)
                ? ContentTypeDao.getInstance().get(contentTypeId).orElse(null)
                : null;

        Content content = Content.builder()
                .id(ReqParamUtils.getInt(req.getParameter("id")))
                .category(category)
                .contentType(contentType)
                .active(ReqParamUtils.getBoolean(req.getParameter("active")))
                .hits(ReqParamUtils.getInt(req.getParameter("hits")))
                .alias(ReqParamUtils.getString(req.getParameter("alias")))
                .created(Objects.nonNull(ReqParamUtils.getDate(req.getParameter("created")))
                        ? ReqParamUtils.getDate(req.getParameter("created"))
                        : LocalDate.now())
                .createdBy(user)
                .translation(translation)
                .build();

        if (Action.CREATE_ACTION.equals(requestAction)) {
            Optional<Content> createdContent = ContentService.getInstance().create(content);
            if (createdContent.isPresent()) {
                resp.sendRedirect("/app/" + BASE_VIEW + "?id=" + createdContent.get().getId());
            }
        } else if (Action.UPDATE_ACTION.equals(requestAction)) {
            if (ContentService.getInstance().update(content)) {
                resp.sendRedirect("/app/" + BASE_VIEW + "?id=" + content.getId());
            }
        } else {
            resp.sendRedirect("/app/404");
        }
    }
}

