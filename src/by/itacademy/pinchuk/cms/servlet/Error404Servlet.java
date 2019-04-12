package by.itacademy.pinchuk.cms.servlet;

import by.itacademy.pinchuk.cms.util.Template;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/app/404", name = "Error404Servlet")
public class Error404Servlet extends HttpServlet {

    public static final String BASE_VIEW = "404";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("viewPath", Template.getViewPath(BASE_VIEW));
        req.setAttribute("view", BASE_VIEW);
        req.getRequestDispatcher(Template.getTplIndex())
                .forward(req, resp);
    }
}
