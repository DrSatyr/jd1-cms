package by.itacademy.pinchuk.cms.util;

import by.itacademy.pinchuk.cms.action.Action;
import by.itacademy.pinchuk.cms.entity.Alert;
import by.itacademy.pinchuk.cms.entity.AlertType;
import by.itacademy.pinchuk.cms.entity.Lang;
import by.itacademy.pinchuk.cms.entity.UserRole;
import by.itacademy.pinchuk.cms.formatter.DateFormat;
import lombok.experimental.UtilityClass;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@UtilityClass
public class RequestHelper {

    public static final String EMPTY_STRING = "";
    public static final List<String> IMG_MIME_TYPES = Arrays.asList("image/png", "image/jpeg");

    public static void addAlert(HttpServletRequest req, AlertType alertType, String content) {
        Alert alert = Alert.builder()
                .alertType(alertType)
                .content(content)
                .build();
        if (req.getSession().getAttribute("alerts") != null) {
            Queue<Alert> alerts = (Queue<Alert>) req.getSession().getAttribute("alerts");
            alerts.add(alert);
        } else {
            Queue<Alert> alerts = new LinkedBlockingQueue<>();
            alerts.add(alert);
            req.getSession().setAttribute("alerts", alerts);
        }
    }

    public String saveImage(HttpServletRequest req, String fieldName, String dir) throws IOException, ServletException {
        String result = null;
        Part reqImage = req.getPart(fieldName);
        if (reqImage.getSize() > 0) {
            File uploadDir = Paths.get(AppConfig.get("sys.path.uploads"), dir).toFile();
            uploadDir.mkdirs();
            if (uploadDir.exists() && IMG_MIME_TYPES.contains(reqImage.getContentType())) {
                String imgName = dir + System.currentTimeMillis();
                String imgExtension = reqImage.getContentType().split("/")[1];
                String imgFileName = imgName + "." + imgExtension;
                File image = Paths.get(uploadDir.getPath(), imgFileName).toFile();
                reqImage.write(image.getPath());
                result = Paths.get(dir, imgFileName).toString();
            } else {
                RequestHelper.addAlert(req, AlertType.WARNING, "Возникла ошибка при загрузке изображения!");
            }
        }
        return result;
    }

    public static boolean isEntityView(HttpServletRequest req) {
        return isValidId(req.getParameter("id")) && isParamNotSet(req.getParameter("action"));
    }

    public static boolean isListView(HttpServletRequest req) {
        return isParamNotSet(req.getParameter("id")) && isParamNotSet(req.getParameter("action"));
    }

    public static boolean isEditView(HttpServletRequest req) {
        return Action.CREATE.equals(req.getParameter("action"))
                || Action.UPDATE.equals(req.getParameter("action"));
    }

    public static boolean isSetParam(String value) {
        return Objects.nonNull(value) && !EMPTY_STRING.equals(value);
    }

    public static boolean isParamNotSet(String value) {
        return !isSetParam(value);
    }

    public static boolean isValidInt(String value) {
        return isSetParam(value) && value.matches("[0-9]\\d*");
    }

    public static boolean isValidId(String value) {
        return isSetParam(value) && value.matches("[1-9]\\d*");
    }

    public static boolean isValidLang(String value) {
        return isSetParam(value) && isLangPresent(value);
    }

    public static boolean isValidUserRole(String value) {
        return isSetParam(value) && isUserRolePresent(value);
    }

    public static String getString(String value) {
        return isSetParam(value) ? value : EMPTY_STRING;
    }

    public static Integer getInt(String value) {
        return isValidInt(value) ? Integer.valueOf(value) : null;
    }

    public static Integer getId(String value) {
        return isValidId(value) ? Integer.valueOf(value) : null;
    }

    public static boolean getBoolean(String value) {
        return Objects.nonNull(value);
    }

    public static LocalDate getDate(String value) {
        return DateFormat.format(value);
    }

    private boolean isLangPresent(String value) {
        boolean result = true;
        try {
            Lang.valueOf(value);
        } catch (IllegalArgumentException e) {
            result = false;
        }
        return result;
    }

    private boolean isUserRolePresent(String value) {
        boolean result = true;
        try {
            UserRole.valueOf(value);
        } catch (IllegalArgumentException e) {
            result = false;
        }
        return result;
    }
}