package by.itacademy.pinchuk.cms.action;

import by.itacademy.pinchuk.cms.dto.Dto;
import by.itacademy.pinchuk.cms.entity.Entity;
import by.itacademy.pinchuk.cms.service.Service;
import by.itacademy.pinchuk.cms.util.AppConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ActionGetAll extends BaseAction implements Action {

    private static final String TPL_LIST_SUFFIX = "tpl.list.suffix";

    public ActionGetAll(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public <E extends Entity> void execute(Service<E> service, String baseView) throws ServletException, IOException {
        List<? extends Dto> entitiesDto = service.getAllPublished();
        getRequest().setAttribute("entities", entitiesDto);
        forward(baseView + AppConfig.get(TPL_LIST_SUFFIX));
    }
}
