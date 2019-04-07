package by.itacademy.pinchuk.cms.action;

import by.itacademy.pinchuk.cms.util.ReqParamUtils;
import lombok.experimental.UtilityClass;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@UtilityClass
public class ActionFabric {

    public static Action get(HttpServletRequest req, HttpServletResponse resp) {
        Action concreteAction;
        String reqAction = req.getParameter("action");

        if (ReqParamUtils.isSet(reqAction)) {
            switch (reqAction) {
                case Action.CREATE_ACTION:
                    concreteAction = new ActionCreate(req, resp);
                    break;
                case Action.UPDATE_ACTION:
                    concreteAction = new ActionUpdate(req, resp);
                    break;
                case Action.DELETE_ACTION:
                    concreteAction = new ActionDelete(req, resp);
                    break;
                default:
                    concreteAction = new ActionUnknown(req, resp);
                    break;
            }
        } else {
            if (ReqParamUtils.isSet(req.getParameter("id"))) {
                concreteAction = new ActionGet(req, resp);
            } else {
                concreteAction = new ActionGetAll(req, resp);
            }
        }
        return concreteAction;
    }
}