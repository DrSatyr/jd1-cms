package by.itacademy.pinchuk.cms.action;

import by.itacademy.pinchuk.cms.entity.Entity;
import by.itacademy.pinchuk.cms.service.Service;

import javax.servlet.ServletException;
import java.io.IOException;

public interface Action {

    String CREATE_ACTION = "create";
    String UPDATE_ACTION = "update";
    String DELETE_ACTION = "delete";

    <E extends Entity> void execute(Service<E> service, String view) throws ServletException, IOException;
}