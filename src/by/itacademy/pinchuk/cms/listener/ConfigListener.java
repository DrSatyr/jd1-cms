package by.itacademy.pinchuk.cms.listener;

import by.itacademy.pinchuk.cms.entity.Lang;
import by.itacademy.pinchuk.cms.entity.UserRole;
import by.itacademy.pinchuk.cms.util.AppConfig;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ConfigListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("languages", Lang.values());
        sce.getServletContext().setAttribute("roles", UserRole.values());
        sce.getServletContext().setAttribute("uploadPath", AppConfig.get("app.path.uploads"));
    }
}
