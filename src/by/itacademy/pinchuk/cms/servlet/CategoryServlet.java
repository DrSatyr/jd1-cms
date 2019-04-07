package by.itacademy.pinchuk.cms.servlet;

import by.itacademy.pinchuk.cms.action.Action;
import by.itacademy.pinchuk.cms.action.ActionFabric;
import by.itacademy.pinchuk.cms.dto.CategoryDto;
import by.itacademy.pinchuk.cms.dto.ContentListDto;
import by.itacademy.pinchuk.cms.entity.Category;
import by.itacademy.pinchuk.cms.entity.CategoryTranslation;
import by.itacademy.pinchuk.cms.entity.Lang;
import by.itacademy.pinchuk.cms.entity.User;
import by.itacademy.pinchuk.cms.service.CategoryService;
import by.itacademy.pinchuk.cms.service.ContentService;
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

@WebServlet("/app/category")
public class CategoryServlet extends HttpServlet {

    private static final String BASE_VIEW = "category";
    private CategoryService categoryService = CategoryService.getInstance();
    private ContentService contentService = ContentService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String requestId = req.getParameter("id");
        String requestAction = req.getParameter("action");
        Lang lang = Lang.valueOf(req.getSession().getAttribute("lang").toString());

        if (ReqParamUtils.notSet(requestAction) && ReqParamUtils.isValidInt(requestId)) {
            List<ContentListDto> categoryContents = contentService.getAllPublished(Integer.valueOf(requestId));
            req.setAttribute("categoryContents", categoryContents);
        }

        if (Action.CREATE_ACTION.equals(requestAction) || Action.UPDATE_ACTION.equals(requestAction)) {
            List<CategoryDto> categories = CategoryService.getInstance().getAll();
            req.setAttribute("allCategories", categories);
        }

        ActionFabric.get(req, resp).execute(categoryService, BASE_VIEW);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestAction = ReqParamUtils.getString(req.getParameter("action"));
        User user = (User) req.getSession().getAttribute("user");

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
                .id(ReqParamUtils.getInt(req.getParameter("id")))
                .parentId(ReqParamUtils.getInt(req.getParameter("parentId")))
                .active(ReqParamUtils.getBoolean(req.getParameter("active")))
                .alias(ReqParamUtils.getString(req.getParameter("alias")))
                .created(Objects.nonNull(ReqParamUtils.getDate(req.getParameter("created")))
                        ? ReqParamUtils.getDate(req.getParameter("created"))
                        : LocalDate.now())
                .translation(translation)
                .build();

        if (Action.CREATE_ACTION.equals(requestAction)) {
            Optional<Category> createdCategory = CategoryService.getInstance().create(category);
            if (createdCategory.isPresent()) {
                resp.sendRedirect("/app/" + BASE_VIEW + "?id=" + createdCategory.get().getId());
            }
        } else if (Action.UPDATE_ACTION.equals(requestAction)) {
            if (CategoryService.getInstance().update(category)) {
                resp.sendRedirect("/app/" + BASE_VIEW + "?id=" + category.getId());
            }
        } else {
            resp.sendRedirect("/app/404");
        }
    }
}
