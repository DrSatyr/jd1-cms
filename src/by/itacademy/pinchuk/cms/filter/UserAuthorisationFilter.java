package by.itacademy.pinchuk.cms.filter;

import by.itacademy.pinchuk.cms.dto.UserDto;
import by.itacademy.pinchuk.cms.entity.AlertType;
import by.itacademy.pinchuk.cms.entity.Lang;
import by.itacademy.pinchuk.cms.entity.UserRole;
import by.itacademy.pinchuk.cms.servlet.LoginServlet;
import by.itacademy.pinchuk.cms.util.Authorisation;
import by.itacademy.pinchuk.cms.util.LocaleUtil;
import by.itacademy.pinchuk.cms.util.RequestHelper;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebFilter(filterName = "filter3")
public class UserAuthorisationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        Lang language = Lang.valueOf(req.getSession().getAttribute("lang").toString());
        UserDto currentUser = (UserDto) req.getSession().getAttribute("user");
        UserRole currentRole = Objects.nonNull(currentUser) ? UserRole.valueOf(currentUser.getRole()) : UserRole.USER;
        String requestURI = req.getRequestURI();
        String action = RequestHelper.getString(req.getParameter("action"));

        if (Authorisation.isPermitted(currentRole, requestURI, action)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            RequestHelper.addAlert(req, AlertType.WARNING,
                    LocaleUtil.getMessage("site.authorisationerror", language.getLocale()) + requestURI + "-" + action);
            resp.sendRedirect("/app/" + LoginServlet.BASE_VIEW);
        }
        ;
    }
}
