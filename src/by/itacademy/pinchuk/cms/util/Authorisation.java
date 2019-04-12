package by.itacademy.pinchuk.cms.util;

import by.itacademy.pinchuk.cms.action.Action;
import by.itacademy.pinchuk.cms.entity.UserRole;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@UtilityClass
public class Authorisation {

    private Map<UserRole, PageConstrain> constrains = new HashMap<>();

    static {
        //USER constrain
        PageConstrain userConstrain = new PageConstrain()
                .put("/app/content", Action.CREATE, Action.UPDATE, Action.DELETE)
                .put("/app/category", Action.CREATE, Action.UPDATE, Action.DELETE)
                .put("/app/user", Action.GET, Action.CREATE, Action.UPDATE, Action.DELETE);
        constrains.put(UserRole.USER, userConstrain);
        //EDITOR constrain
        PageConstrain editorConstrain = new PageConstrain()
                .put("/app/user", Action.GET, Action.CREATE, Action.UPDATE, Action.DELETE);
        constrains.put(UserRole.EDITOR, editorConstrain);
        //ADMINISTRATOR constrain
        constrains.put(UserRole.ADMINISTRATOR, new PageConstrain());
    }

    public boolean isNotPermitted(UserRole role, String requestURI, String action) {
        Map<String, List<String>> roleConstrains = constrains.get(role).getValues();
        List<String> uriConstrains = roleConstrains.get(requestURI);
        return Objects.nonNull(uriConstrains) && uriConstrains.stream().anyMatch(it -> it.equals(action));
    }

    public boolean isPermitted(UserRole role, String requestURI, String action) {
        return !isNotPermitted(role, requestURI, action);
    }

    private class PageConstrain {

        @Getter
        private Map<String, List<String>> values = new HashMap<>();

        public PageConstrain put(String requestURI, String... actions) {
            values.put(requestURI, Arrays.asList(actions));
            return this;
        }
    }
}
