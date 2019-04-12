package by.itacademy.pinchuk.cms.servlet;

import by.itacademy.pinchuk.cms.action.Action;
import by.itacademy.pinchuk.cms.action.ActionFabric;
import by.itacademy.pinchuk.cms.dao.CategoryDao;
import by.itacademy.pinchuk.cms.dao.ContentTypeDao;
import by.itacademy.pinchuk.cms.dto.CategoryDto;
import by.itacademy.pinchuk.cms.dto.CommentDto;
import by.itacademy.pinchuk.cms.dto.ContentTypeDto;
import by.itacademy.pinchuk.cms.dto.TagDto;
import by.itacademy.pinchuk.cms.dto.UserDto;
import by.itacademy.pinchuk.cms.entity.AlertType;
import by.itacademy.pinchuk.cms.entity.Category;
import by.itacademy.pinchuk.cms.entity.Content;
import by.itacademy.pinchuk.cms.entity.ContentTranslation;
import by.itacademy.pinchuk.cms.entity.ContentType;
import by.itacademy.pinchuk.cms.entity.Lang;
import by.itacademy.pinchuk.cms.mapper.UserMapper;
import by.itacademy.pinchuk.cms.service.CategoryService;
import by.itacademy.pinchuk.cms.service.CommentService;
import by.itacademy.pinchuk.cms.service.ContentService;
import by.itacademy.pinchuk.cms.service.ContentTypeService;
import by.itacademy.pinchuk.cms.service.Service;
import by.itacademy.pinchuk.cms.service.TagService;
import by.itacademy.pinchuk.cms.util.LocaleUtil;
import by.itacademy.pinchuk.cms.util.RequestHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
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
@MultipartConfig
public class ContentServlet extends HttpServlet {

    public static final String BASE_VIEW = "content";
    public static final String UPDATE_IMAGE_ACTION = "update-image";
    private Service<Content> contentService = ContentService.getInstance();
    private TagService tagService = TagService.getInstance();
    private CommentService commentService = CommentService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (RequestHelper.isEntityView(req)) {
            Integer id = Integer.valueOf(req.getParameter("id"));
            List<CommentDto> comments = commentService.getRelatedToContent(id);
            req.setAttribute("comments", comments);
            Lang lang = Lang.valueOf(req.getSession().getAttribute("lang").toString());
            List<TagDto> relatedTags = tagService.getRelatedToContent(id, lang);
            req.setAttribute("tags", relatedTags);
        }

        if (RequestHelper.isEditView(req)) {
            List<CategoryDto> categories = CategoryService.getInstance().getAll();
            req.setAttribute("allCategories", categories);
            List<ContentTypeDto> contentTypes = ContentTypeService.getInstance().getAll();
            req.setAttribute("contentTypes", contentTypes);
        }
        ActionFabric.get(req, resp).execute(contentService, BASE_VIEW);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDto user = (UserDto) req.getSession().getAttribute("user");
        String requestAction = RequestHelper.getString(req.getParameter("action"));
        Lang language = Lang.valueOf(req.getSession().getAttribute("lang").toString());

        Map<Lang, ContentTranslation> translation = new HashMap<>();
        for (Lang lang : Lang.values()) {
            ContentTranslation contentTranslation = ContentTranslation.builder()
                    .name(RequestHelper.getString(req.getParameter("name" + lang.name())))
                    .introText(RequestHelper.getString(req.getParameter("introText" + lang.name())))
                    .fullText(RequestHelper.getString(req.getParameter("fullText" + lang.name())))
                    .metaTitle(RequestHelper.getString(req.getParameter("metaTitle" + lang.name())))
                    .metaDescription(RequestHelper.getString(req.getParameter("metaDescription" + lang.name())))
                    .metaKeywords(RequestHelper.getString(req.getParameter("metaKeywords" + lang.name())))
                    .build();
            translation.put(lang, contentTranslation);
        }

        Integer categoryId = RequestHelper.getInt(req.getParameter("CategoryId"));
        Category category = Objects.nonNull(categoryId) ?
                CategoryDao.getInstance().get(categoryId).orElse(null)
                : null;

        Integer contentTypeId = RequestHelper.getInt(req.getParameter("contentTypeId"));
        ContentType contentType = Objects.nonNull(contentTypeId)
                ? ContentTypeDao.getInstance().get(contentTypeId).orElse(null)
                : null;

        Content content = Content.builder()
                .id(RequestHelper.getInt(req.getParameter("id")))
                .image(RequestHelper.saveImage(req, "image", BASE_VIEW))
                .category(category)
                .contentType(contentType)
                .active(RequestHelper.getBoolean(req.getParameter("active")))
                .hits(RequestHelper.getInt(req.getParameter("hits")))
                .alias(RequestHelper.getString(req.getParameter("alias")))
                .created(Objects.nonNull(RequestHelper.getDate(req.getParameter("created")))
                        ? RequestHelper.getDate(req.getParameter("created"))
                        : LocalDate.now())
                .createdBy(UserMapper.getInstance().dtoToEntity(user))
                .translation(translation)
                .build();


        // TODO: 08.04.2019 Вынести в файлы локализации сообщения об ошибках
        switch (requestAction) {
            case Action.CREATE:
                Optional<Content> createdContent = ContentService.getInstance().create(content);
                if (createdContent.isPresent()) {
                    RequestHelper.addAlert(req, AlertType.SUCCESS,
                            LocaleUtil.getMessage("action.create.success", language.getLocale()));
                    resp.sendRedirect("/app/" + BASE_VIEW + "?id=" + createdContent.get().getId());
                } else {
                    RequestHelper.addAlert(req, AlertType.DANGER,
                            LocaleUtil.getMessage("action.create.danger", language.getLocale()));
                    resp.sendRedirect("/app/" + BASE_VIEW);
                }
                break;
            case Action.UPDATE:
                if (ContentService.getInstance().update(content)) {
                    RequestHelper.addAlert(req, AlertType.SUCCESS,
                            LocaleUtil.getMessage("action.update.success", language.getLocale()));
                    resp.sendRedirect("/app/" + BASE_VIEW + "?id=" + content.getId());
                } else {
                    RequestHelper.addAlert(req, AlertType.DANGER,
                            LocaleUtil.getMessage("action.update.danger", language.getLocale()));
                    resp.sendRedirect("/app/" + BASE_VIEW);
                }
                break;
            case UPDATE_IMAGE_ACTION:
                Integer requestId = RequestHelper.getId(req.getParameter("id"));
                if (ContentService.getInstance().updateImage(content)) {
                    // TODO: 12.04.2019 Нужен еще метод в сервисе, для удаления старой картинки
                    RequestHelper.addAlert(req, AlertType.SUCCESS,
                            LocaleUtil.getMessage("action.update-image.success", language.getLocale()));
                    resp.sendRedirect("/app/" + BASE_VIEW + "?id=" + content.getId());
                } else {
                    // TODO: 12.04.2019 при неудачном обновлении - нужно тоже удалить загруженную картинку
                    RequestHelper.addAlert(req, AlertType.DANGER,
                            LocaleUtil.getMessage("action.update-image.danger", language.getLocale()));
                    resp.sendRedirect("/app/" + BASE_VIEW);
                }
                break;
            default:
                resp.sendRedirect("/app/" + Error404Servlet.BASE_VIEW);
                break;
        }
    }
}

