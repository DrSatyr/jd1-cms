package by.itacademy.pinchuk.cms.action;

import by.itacademy.pinchuk.cms.entity.Entity;
import by.itacademy.pinchuk.cms.service.Service;
import by.itacademy.pinchuk.cms.util.AppConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ActionCreate extends BaseAction implements Action {

    public ActionCreate(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public <E extends Entity> void execute(Service<E> service, String baseView) throws ServletException, IOException {
        forward(baseView + AppConfig.get("tpl.create.suffix"));
    }
}
