package it.unipi.lsmd.utils;

import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.dto.RegisteredUserDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Enumeration;

public class SecurityUtils {

    public static final String AUTHENTICATED_USER_KEY = "authenticatedUser";
    public static final String WISHLIST_KEY = "wishlist";
    public static final String USERS_FOLLOWERS_KEY = "followers";
    public static final String CHEAPEST_TRIPS = "cheapest";
    public static final String MOST_POPULAR = "most_populars";


    public static AuthenticatedUserDTO getAuthenticatedUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        return (AuthenticatedUserDTO)session.getAttribute(AUTHENTICATED_USER_KEY);
    }

}
