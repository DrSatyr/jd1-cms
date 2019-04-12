package by.itacademy.pinchuk.cms.action;

import by.itacademy.pinchuk.cms.entity.AlertType;
import by.itacademy.pinchuk.cms.entity.Entity;
import by.itacademy.pinchuk.cms.entity.Lang;
import by.itacademy.pinchuk.cms.service.Service;
import by.itacademy.pinchuk.cms.util.LocaleUtil;
import by.itacademy.pinchuk.cms.util.RequestHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class ActionDelete extends BaseAction implements Action {

    private Integer id;

    public ActionDelete(HttpServletRequest req, HttpServletResponse resp) {
        super(req, resp);
        this.id = RequestHelper.getInt(req.getParameter("id"));
    }

    @Override
    public <E extends Entity> void execute(Service<E> service, String baseView) throws ServletException, IOException {
        Lang language = Lang.valueOf(getRequest().getSession().getAttribute("lang").toString());
        if (Objects.nonNull(id)) {
            boolean result = service.delete(id);
            if (result) {
                RequestHelper.addAlert(getRequest(), AlertType.SUCCESS,
                        LocaleUtil.getMessage("action.delete.success", language.getLocale()));
                getResponse().sendRedirect("/app/" + baseView);
            } else {
                RequestHelper.addAlert(getRequest(), AlertType.DANGER,
                        LocaleUtil.getMessage("action.delete.danger", language.getLocale()));
                getResponse().sendRedirect("/app/404");
            }
        }
    }
}
