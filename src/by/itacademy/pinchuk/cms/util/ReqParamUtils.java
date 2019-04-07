package by.itacademy.pinchuk.cms.util;

import by.itacademy.pinchuk.cms.entity.Lang;
import by.itacademy.pinchuk.cms.formatter.DateFormat;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.Objects;

@UtilityClass
public class ReqParamUtils {

    public static final String EMPTY = "";

    public static boolean isSet(String value) {
        return Objects.nonNull(value) && !EMPTY.equals(value);
    }

    public static boolean notSet(String value) {
        return !isSet(value);
    }

    public static boolean isValidInt(String value) {
        return isSet(value) && value.matches("[0-9]\\d*");
    }

    public static boolean isValidId(String value) {
        return isSet(value) && value.matches("[1-9]\\d*");
    }

    public static boolean isValidLang(String value) {
        return isSet(value) && isLangPresent(value);
    }

    public static String getString (String value) {
        return isSet(value) ? value : EMPTY;
    }

    public static Integer getInt(String value) {
        return isValidInt(value) ? Integer.valueOf(value) : null;
    }

    public static Integer getId(String value) {
        return isValidId(value) ? Integer.valueOf(value) : null;
    }

    public static boolean getBoolean (String value) {
        return Objects.nonNull(value);
    }

    public static LocalDate getDate (String value) {
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
}