package it.unipi.lsmd.utils;

import it.unipi.lsmd.dto.AuthenticatedUserDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SecurityUtils {

    public static final String TITLE_PAGE = "title_page";
    public static final String AUTHENTICATED_USER_KEY = "authenticatedUser";
    public static final String REVIEWS_KEY = "reviews";
    public static final String CHEAPEST_TRIPS = "cheapest";
    public static final String MOST_POPULAR = "most_populars";
    public static final String FOLLOWING_USER_TRIPS = "trip";
    public static final String SUGGESTED_USERS = "suggested_users";
    public static final String SUGGESTED_TRIPS = "suggested_trips";
    public static final String PAGE = "page";
    public static final String JOINERS = "joiners";
    public static final String SIGNUP_ERROR = "error_message";
    public static final String TRIPS_RESULT = "trips_result";
    public static final String USER_RESULTS = "user_results";
    public static final String STATUS = "status";
    public static final String SUGGESTIONS_EXPLORE = "aggregation_explore";
    public static final String SUGGESTIONS_EXPLORE_TITLE = "suggestions_explore_title";
    public static final String TRIP = "trip";
    public static final String USER = "user";
    public static final String MODIFY_FLAG = "modify_trip";
    public static final String ERROR_MESSAGE = "error_message";
    public static final String REVIEW_TO = "username";


    public static AuthenticatedUserDTO getAuthenticatedUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        return (AuthenticatedUserDTO)session.getAttribute(AUTHENTICATED_USER_KEY);
    }

}
