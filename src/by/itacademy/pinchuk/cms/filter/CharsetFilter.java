package by.itacademy.pinchuk.cms.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebFilter(filterName ="filter1", urlPatterns = "/app/*")
public class CharsetFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
