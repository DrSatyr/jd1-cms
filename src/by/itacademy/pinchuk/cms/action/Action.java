package by.itacademy.pinchuk.cms.action;

import by.itacademy.pinchuk.cms.entity.Entity;
import by.itacademy.pinchuk.cms.service.Service;

import javax.servlet.ServletException;
import java.io.IOException;

public interface Action {

    String GET = "";
    String CREATE = "create";
    String UPDATE = "update";
    String DELETE = "delete";

    <E extends Entity> void execute(Service<E> service, String view) throws ServletException, IOException;
}