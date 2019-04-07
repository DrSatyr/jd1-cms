package by.itacademy.pinchuk.cms.action;

import by.itacademy.pinchuk.cms.entity.Entity;
import by.itacademy.pinchuk.cms.service.Service;
import by.itacademy.pinchuk.cms.util.ReqParamUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class ActionDelete extends BaseAction implements Action {

    private Integer id;

    public ActionDelete(HttpServletRequest req, HttpServletResponse resp) {
        super(req, resp);
        this.id = ReqParamUtils.getInt(req.getParameter("id"));
    }

    @Override
    public <E extends Entity> void execute(Service<E> service, String baseView) throws ServletException, IOException {
        if (Objects.nonNull(id)) {
            boolean result = service.delete(id);
            if (result) {
                getResponse().sendRedirect("/app/" + baseView);
            } else {
                getResponse().sendRedirect("/app/404");
            }
        }
    }
}
