package by.itacademy.pinchuk.cms.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Template {

    public static final String TPL_PATH = "tpl.path";
    public static final String TPL_INDEX_NAME = "tpl.index.name";

    public static String getViewPath(String name) {
        return AppConfig.get(TPL_PATH) + name + ".jsp";
    }

    public static String getTplIndex() {
        return AppConfig.get(TPL_PATH)
                + AppConfig.get(TPL_INDEX_NAME)
                + ".jsp";
    }
}
