package by.itacademy.pinchuk.cms.servlet;

import by.itacademy.pinchuk.cms.dto.CategoryWithContentsDto;
import by.itacademy.pinchuk.cms.dto.ContentListDto;
import by.itacademy.pinchuk.cms.service.CategoryService;
import by.itacademy.pinchuk.cms.service.ContentService;
import by.itacademy.pinchuk.cms.util.AppConfig;
import by.itacademy.pinchuk.cms.util.Template;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns={"/app/main", "/app"})
public class MainServlet extends HttpServlet {

    public static final String BASE_VIEW = "main";
    private CategoryService categoryService = CategoryService.getInstance();
    private ContentService contentService = ContentService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ContentListDto> mainNews = contentService.getLatestPublished(1);
        req.setAttribute("mainNews", mainNews);
        Integer limit = Integer.valueOf(AppConfig.get("news.limit.in.block"));
        List<CategoryWithContentsDto> categoriesWithContent = categoryService.getAllPublishedWithContent(limit);
        req.setAttribute("categoriesWithContent", categoriesWithContent);

        req.setAttribute("view", BASE_VIEW);
        req.setAttribute("viewPath", Template.getViewPath(BASE_VIEW));
        req.getRequestDispatcher(Template.getTplIndex())
                .forward(req, resp);
    }
}
