package it.unipi.lsmd.utils;

import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.dto.RegisteredUserDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Enumeration;

public class SecurityUtils {

    public static final String TITLE_PAGE = "title_page";
    public static final String AUTHENTICATED_USER_KEY = "authenticatedUser";
    public static final String WISHLIST_KEY = "wishlist";
    public static final String REVIEWS_KEY = "reviews";

    public static final String USERS_FOLLOWERS_KEY = "followers";
    public static final String CHEAPEST_TRIPS = "cheapest";
    public static final String MOST_POPULAR = "most_populars";
    public static final String FOLLOWING_USER_TRIPS = "trip";
    public static final String SUGGESTED_USERS = "suggested_users";
    public static final String SUGGESTED_TRIPS = "suggested_trips";
    public static final String SEARCH_RESULTS = "search_results";
    public static final String PAGE = "page";
    public static final String JOINERS = "joiners";
    public static final String SIGNUP_ERROR = "error_message";
    public static final String ORGANIZED_TRIPS = "organized_trips";
    public static final String TRIPS_RESULT = "trips_result";
    public static final String USER_RESULTS = "user_results";
    public static final String STATUS = "status";
    public static final String SUGGESTIONS_EXPLORE = "aggregation_explore";
    public static final String SUGGESTIONS_EXPLORE_TITLE = "suggestions_explore_title";
    public static final String TRIP = "trip";
    public static final String MODIFY_FLAG = "modify_trip";
    public static final String ERROR_MESSAGE = "error_message";

    // ho chiamato quella per i viaggi organizzato ORGANIZED_TRIPS
    // mi deve mandare la pagina corrente e il numero totale

    public static AuthenticatedUserDTO getAuthenticatedUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        return (AuthenticatedUserDTO)session.getAttribute(AUTHENTICATED_USER_KEY);
    }

}
