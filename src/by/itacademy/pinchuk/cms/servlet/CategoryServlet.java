package by.itacademy.pinchuk.cms.servlet;

import by.itacademy.pinchuk.cms.action.Action;
import by.itacademy.pinchuk.cms.action.ActionFabric;
import by.itacademy.pinchuk.cms.dto.CategoryDto;
import by.itacademy.pinchuk.cms.dto.CategoryWithContentsDto;
import by.itacademy.pinchuk.cms.dto.ContentListDto;
import by.itacademy.pinchuk.cms.entity.AlertType;
import by.itacademy.pinchuk.cms.entity.Category;
import by.itacademy.pinchuk.cms.entity.CategoryTranslation;
import by.itacademy.pinchuk.cms.entity.Lang;
import by.itacademy.pinchuk.cms.service.CategoryService;
import by.itacademy.pinchuk.cms.service.ContentService;
import by.itacademy.pinchuk.cms.util.AppConfig;
import by.itacademy.pinchuk.cms.util.LocaleUtil;
import by.itacademy.pinchuk.cms.util.RequestHelper;

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

@WebServlet("/app/category")
public class CategoryServlet extends HttpServlet {

    public static final String BASE_VIEW = "category";
    private CategoryService categoryService = CategoryService.getInstance();
    private ContentService contentService = ContentService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (RequestHelper.isEntityView(req)) {
            List<ContentListDto> categoryContents = contentService.getAllPublished(Integer.valueOf(req.getParameter("id")));
            req.setAttribute("categoryContents", categoryContents);
        }

        if (RequestHelper.isListView(req)) {
            Integer limit = Integer.valueOf(AppConfig.get("news.limit.on.page"));
            List<CategoryWithContentsDto> categoriesWithContent = categoryService.getAllPublishedWithContent(limit);
            req.setAttribute("categoriesWithContent", categoriesWithContent);
        }

        if (RequestHelper.isEditView(req)) {
            List<CategoryDto> categories = CategoryService.getInstance().getAll();
            req.setAttribute("allCategories", categories);
        }
        ActionFabric.get(req, resp).execute(categoryService, BASE_VIEW);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestAction = RequestHelper.getString(req.getParameter("action"));
        Lang language = Lang.valueOf(req.getSession().getAttribute("lang").toString());

        Map<Lang, CategoryTranslation> translation = new HashMap<>();
        for (Lang lang : Lang.values()) {
            CategoryTranslation categoryTranslation = CategoryTranslation.builder()
                    .name(req.getParameter("name" + lang.name()))
                    .introText(req.getParameter("introText" + lang.name()))
                    .metaTitle(req.getParameter("metaTitle" + lang.name()))
                    .metaDescription(req.getParameter("metaDescription" + lang.name()))
                    .metaKeywords(req.getParameter("metaKeywords" + lang.name()))
                    .build();
            translation.put(lang, categoryTranslation);
        }

        Category category = Category.builder()
                .id(RequestHelper.getInt(req.getParameter("id")))
                .parentId(RequestHelper.getInt(req.getParameter("parentId")))
                .active(RequestHelper.getBoolean(req.getParameter("active")))
                .alias(RequestHelper.getString(req.getParameter("alias")))
                .created(Objects.nonNull(RequestHelper.getDate(req.getParameter("created")))
                        ? RequestHelper.getDate(req.getParameter("created"))
                        : LocalDate.now())
                .translation(translation)
                .build();

        // TODO: 08.04.2019 Вынести в файлы локализации
        switch (requestAction) {
            case Action.CREATE:
                Optional<Category> createdCategory = CategoryService.getInstance().create(category);
                if (createdCategory.isPresent()) {
                    RequestHelper.addAlert(req, AlertType.SUCCESS,
                            LocaleUtil.getMessage("action.create.success", language.getLocale()));
                    resp.sendRedirect("/app/" + BASE_VIEW + "?id=" + createdCategory.get().getId());
                } else {
                    RequestHelper.addAlert(req, AlertType.DANGER,
                            LocaleUtil.getMessage("action.create.danger", language.getLocale()));
                    resp.sendRedirect("/app/" + BASE_VIEW);
                }
                break;
            case Action.UPDATE:
                if (CategoryService.getInstance().update(category)) {
                    RequestHelper.addAlert(req, AlertType.SUCCESS,
                            LocaleUtil.getMessage("action.update.success", language.getLocale()));
                    resp.sendRedirect("/app/" + BASE_VIEW + "?id=" + category.getId());
                } else {
                    RequestHelper.addAlert(req, AlertType.DANGER,
                            LocaleUtil.getMessage("action.update.danger", language.getLocale()));
                    resp.sendRedirect("/app/" + BASE_VIEW);
                }
                break;
            default:
                resp.sendRedirect("/app/" + Error404Servlet.BASE_VIEW);
                break;
        }
    }
}
