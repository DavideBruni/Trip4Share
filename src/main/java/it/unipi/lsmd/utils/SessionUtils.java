package it.unipi.lsmd.utils;

import it.unipi.lsmd.utils.SecurityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface SessionUtils {
    static boolean userIsLogged(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        if (session != null) {
            Object o = session.getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY);
            if(o!=null)
                return true;
        }
        return false;
    }
}
