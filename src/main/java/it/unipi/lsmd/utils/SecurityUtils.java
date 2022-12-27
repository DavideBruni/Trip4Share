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
    public static final String FOLLOWING_USER_TRIPS = "trip";
    public static final String SUGGESTED_USERS = "suggested_users";
    public static final String SUGGESTED_TRIPS = "suggested_trips";
    public static final String PAGE = "page";

    // ho chiamato quella per i viaggi organizzato ORGANIZED_TRIPS
    // mi deve mandare la pagina corrente e il numero totale

    public static AuthenticatedUserDTO getAuthenticatedUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        return (AuthenticatedUserDTO)session.getAttribute(AUTHENTICATED_USER_KEY);
    }

}
