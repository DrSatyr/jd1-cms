package by.itacademy.pinchuk.cms.action;

import by.itacademy.pinchuk.cms.entity.Entity;
import by.itacademy.pinchuk.cms.service.Service;
import by.itacademy.pinchuk.cms.servlet.Error404Servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ActionUnknown extends BaseAction implements Action {

    public ActionUnknown(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public <E extends Entity> void execute(Service<E> service, String baseView) throws IOException {
        getResponse().sendRedirect("/app/" + Error404Servlet.BASE_VIEW);
    }
}
