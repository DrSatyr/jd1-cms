package by.itacademy.pinchuk.cms.filter;

import by.itacademy.pinchuk.cms.dao.UserDao;
import by.itacademy.pinchuk.cms.dto.CategoryDto;
import by.itacademy.pinchuk.cms.entity.User;
import by.itacademy.pinchuk.cms.service.CategoryService;
import by.itacademy.pinchuk.cms.util.AppConfig;
import by.itacademy.pinchuk.cms.util.ReqParamUtils;

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
import java.util.Optional;

@WebFilter(filterName = "filter2", urlPatterns = "/app/*")
public class CommonDataPreparingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String lang = req.getParameter("lang");
        lang = ReqParamUtils.isValidLang(lang) ? lang : AppConfig.get("common.default.lang");
        req.getSession().setAttribute("lang", lang);

        List<CategoryDto> topMenu = CategoryService.getInstance().getAllPublished();
        servletRequest.setAttribute("topMenu", topMenu);

        Optional<User> user = UserDao.getInstance().get(1);
        req.getSession().setAttribute("user", user.orElse(null));

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
