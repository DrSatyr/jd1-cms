package by.itacademy.pinchuk.cms.util;

import lombok.experimental.UtilityClass;

import java.util.Locale;
import java.util.ResourceBundle;

@UtilityClass
public class LocaleUtil {

    public String getMessage (String key ,Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        return bundle.getString(key);
    }
}
