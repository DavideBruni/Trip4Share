package it.unipi.lsmd.utils;

import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.dto.RegisteredUserDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Enumeration;

public class SecurityUtils {

    public static final String AUTHENTICATED_USER_KEY = "authenticatedUser";

    public static AuthenticatedUserDTO getAuthenticatedUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        return (AuthenticatedUserDTO)session.getAttribute(AUTHENTICATED_USER_KEY);
    }

}
