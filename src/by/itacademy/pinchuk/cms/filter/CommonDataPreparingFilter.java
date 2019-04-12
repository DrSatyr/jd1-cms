package by.itacademy.pinchuk.cms.filter;

import by.itacademy.pinchuk.cms.dto.CategoryDto;
import by.itacademy.pinchuk.cms.service.CategoryService;
import by.itacademy.pinchuk.cms.util.AppConfig;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebFilter(filterName = "filter2")
public class CommonDataPreparingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        if (Objects.isNull(req.getSession().getAttribute("lang"))) {
            req.getSession().setAttribute("lang", AppConfig.get("app.default.lang"));
            req.getSession().setAttribute("locale", AppConfig.get("app.default.locale"));
        }

        List<CategoryDto> topMenu = CategoryService.getInstance().getAllPublished();
        servletRequest.setAttribute("topMenu", topMenu);

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
