package by.itacademy.pinchuk.cms.action;

import by.itacademy.pinchuk.cms.util.Template;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
@Getter
public abstract class BaseAction {

    private HttpServletRequest request;
    private HttpServletResponse response;

    protected void forward(String view) throws ServletException, IOException {
        request.setAttribute("view", view);
        request.setAttribute("viewPath", Template.getViewPath(view));
        request.getRequestDispatcher(Template.getTplIndex()).forward(request, response);
    }

    protected void sendRedirect(String where) throws IOException {
        response.sendRedirect(where);
    }
}
