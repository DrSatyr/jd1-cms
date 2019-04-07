package by.itacademy.pinchuk.cms.action;

import by.itacademy.pinchuk.cms.dto.Dto;
import by.itacademy.pinchuk.cms.entity.Entity;
import by.itacademy.pinchuk.cms.service.Service;
import by.itacademy.pinchuk.cms.util.AppConfig;
import by.itacademy.pinchuk.cms.util.ReqParamUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class ActionUpdate extends BaseAction implements Action {

    private Integer id;

    public ActionUpdate(HttpServletRequest req, HttpServletResponse resp) {
        super(req, resp);
        this.id = ReqParamUtils.getInt(req.getParameter("id"));
    }

    @Override
    public <E extends Entity> void execute(Service<E> service, String baseView) throws IOException, ServletException {
        if (Objects.nonNull(id)) {
            Optional<? extends Dto> entityDto = service.get(id);
            if (entityDto.isPresent()) {
                getRequest().setAttribute("entity", entityDto.get());
                getRequest().setAttribute("view", baseView + AppConfig.get("tpl.edit.suffix"));
                forward(baseView + AppConfig.get("tpl.edit.suffix"));
            } else {
                getResponse().sendRedirect("/app/404");
            }
        }
    }
}
