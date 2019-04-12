package by.itacademy.pinchuk.cms.servlet;

import by.itacademy.pinchuk.cms.dto.UserDto;
import by.itacademy.pinchuk.cms.entity.AlertType;
import by.itacademy.pinchuk.cms.entity.Lang;
import by.itacademy.pinchuk.cms.service.UserService;
import by.itacademy.pinchuk.cms.util.LocaleUtil;
import by.itacademy.pinchuk.cms.util.RequestHelper;
import by.itacademy.pinchuk.cms.util.Template;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/app/login")
public class LoginServlet extends HttpServlet {

    public static final String BASE_VIEW = "login";
    public static final String LOGOUT_ACTION = "logout";
    private UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Lang language = Lang.valueOf(req.getSession().getAttribute("lang").toString());
        if (LOGOUT_ACTION.equals(req.getParameter("action"))) {
            req.getSession().removeAttribute("user");
            RequestHelper.addAlert(req, AlertType.INFO,
                    LocaleUtil.getMessage("action.logout", language.getLocale()));
            if (RequestHelper.isSetParam(req.getHeader("referer"))) {
                resp.sendRedirect(req.getHeader("referer"));
            } else {
                resp.sendRedirect("/app/" + MainServlet.BASE_VIEW);
            }
        } else {
            req.setAttribute("view", BASE_VIEW);
            req.setAttribute("viewPath", Template.getViewPath(BASE_VIEW));
            req.getServletContext().
                    getRequestDispatcher(Template.getTplIndex())
                    .forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = RequestHelper.getString(req.getParameter("username"));
        String password = RequestHelper.getString(req.getParameter("password"));
        Lang language = Lang.valueOf(req.getSession().getAttribute("lang").toString());
        Optional<UserDto> user = userService.login(username, password);
        if (user.isPresent()) {
            UserDto userDto = user.get();
            req.getSession().setAttribute("user", userDto);
            RequestHelper.addAlert(req, AlertType.SUCCESS,
                    LocaleUtil.getMessage("action.login.success", language.getLocale()) + userDto.getUsername());
            resp.sendRedirect("/app/" + MainServlet.BASE_VIEW);
        } else {
            RequestHelper.addAlert(req, AlertType.WARNING,
                    LocaleUtil.getMessage("action.login.danger", language.getLocale()));
            resp.sendRedirect("/app/login");
        }
    }
}
