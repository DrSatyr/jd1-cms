package by.itacademy.pinchuk.cms.servlet;

import by.itacademy.pinchuk.cms.entity.Lang;
import by.itacademy.pinchuk.cms.util.RequestHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app/locale")
public class LocaleServlet extends HttpServlet {

    public static final String BASE_VIEW = "locale";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lang = req.getParameter("lang");
        if (RequestHelper.isValidLang(lang)) {
            req.getSession().setAttribute("lang", Lang.valueOf(lang));
            req.getSession().setAttribute("locale", Lang.valueOf(lang).getLocale());
            resp.sendRedirect(req.getHeader("referer"));
        }
    }
}
