package by.itacademy.pinchuk.cms.servlet;

import by.itacademy.pinchuk.cms.action.Action;
import by.itacademy.pinchuk.cms.action.ActionFabric;
import by.itacademy.pinchuk.cms.entity.AlertType;
import by.itacademy.pinchuk.cms.entity.Lang;
import by.itacademy.pinchuk.cms.entity.User;
import by.itacademy.pinchuk.cms.entity.UserRole;
import by.itacademy.pinchuk.cms.service.UserService;
import by.itacademy.pinchuk.cms.util.LocaleUtil;
import by.itacademy.pinchuk.cms.util.RequestHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@WebServlet("/app/user")
public class UserServlet extends HttpServlet {

    public static final String BASE_VIEW = "user";
    public static final String UPDATE_PASS_ACTION = "update-pass";
    public static final UserRole DEFAULT_USER_ROLE = UserRole.USER;
    private UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ActionFabric.get(req, resp).execute(userService, BASE_VIEW);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestAction = RequestHelper.getString(req.getParameter("action"));
        Lang lang = Lang.valueOf(req.getSession().getAttribute("lang").toString());

        User user = User.builder()
                .id(RequestHelper.getId(req.getParameter("id")))
                .username(RequestHelper.getString(req.getParameter("username")))
                .email(RequestHelper.getString(req.getParameter("email")))
                .phone(RequestHelper.getString(req.getParameter("phone")))
                .password(RequestHelper.getString(req.getParameter("password")))
                .active(RequestHelper.getBoolean(req.getParameter("active")))
                .role(RequestHelper.isValidUserRole(req.getParameter("role"))
                        ? UserRole.valueOf(req.getParameter("role"))
                        : DEFAULT_USER_ROLE)
                .registerDate(LocalDate.now())
                .birthDate(RequestHelper.getDate(req.getParameter("birthDate")))
                .name(RequestHelper.getString(req.getParameter("name")))
                .surname(RequestHelper.getString(req.getParameter("surname")))
                .build();

        // TODO: 08.04.2019 Вынести ошибки в файлы локализации
        switch (requestAction) {
            case Action.CREATE:
                Optional<User> createdUser = userService.create(user);
                if (createdUser.isPresent()) {
                    RequestHelper.addAlert(req, AlertType.SUCCESS,
                            LocaleUtil.getMessage("action.create.success", lang.getLocale()));
                    resp.sendRedirect("/app/" + BASE_VIEW + "?id=" + createdUser.get().getId());
                } else {
                    RequestHelper.addAlert(req, AlertType.DANGER,
                            LocaleUtil.getMessage("action.create.danger", lang.getLocale()));
                    resp.sendRedirect("/app/" + BASE_VIEW);
                }
                break;
            case Action.UPDATE:
                if (userService.update(user)) {
                    RequestHelper.addAlert(req, AlertType.SUCCESS,
                            LocaleUtil.getMessage("action.update.success", lang.getLocale()));
                    resp.sendRedirect("/app/" + BASE_VIEW + "?id=" + user.getId());
                } else {
                    RequestHelper.addAlert(req, AlertType.DANGER,
                            LocaleUtil.getMessage("action.update.danger", lang.getLocale()));
                    resp.sendRedirect("/app/" + BASE_VIEW);
                }
                break;
            case UPDATE_PASS_ACTION:
                Integer requestId = RequestHelper.getId(req.getParameter("id"));
                String password = RequestHelper.getString(req.getParameter("password"));
                if (Objects.nonNull(requestId) && userService.updatePassword(requestId, password)) {
                    RequestHelper.addAlert(req, AlertType.SUCCESS,
                            LocaleUtil.getMessage("action.update-pass.success", lang.getLocale()));
                    resp.sendRedirect("/app/" + BASE_VIEW + "?id=" + requestId);
                } else {
                    RequestHelper.addAlert(req, AlertType.DANGER,
                            LocaleUtil.getMessage("action.update-pass.danger", lang.getLocale()));
                    resp.sendRedirect("/app/" + BASE_VIEW);
                }
                break;
            default:
                resp.sendRedirect("/app/" + Error404Servlet.BASE_VIEW);
                break;
        }
    }
}
