package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.*;
import it.unipi.lsmd.service.ServiceLocator;
import it.unipi.lsmd.service.TripService;
import it.unipi.lsmd.service.UserService;
import it.unipi.lsmd.service.impl.UserServiceImpl;
import it.unipi.lsmd.utils.PagesUtilis;
import it.unipi.lsmd.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private TripService tripService = ServiceLocator.getTripService();
    private UserService userService = ServiceLocator.getUserService();
    private static Logger logger = LoggerFactory.getLogger(UserServlet.class);



    private RequestDispatcher getUserReviews(HttpServletRequest request, String username, int page) {

        List<ReviewDTO> reviews = userService.getReviews(username, PagesUtilis.REVIEWS_PER_PAGE, page);
        request.setAttribute(SecurityUtils.REVIEWS_KEY, reviews);
        request.setAttribute(SecurityUtils.PAGE, page);
        return request.getRequestDispatcher("/WEB-INF/pages/reviews_board.jsp");
    }

    private RequestDispatcher getOrganizedTrips(HttpServletRequest request, String username, int page) {
        List<TripSummaryDTO> trips = tripService.getTripsOrganizedByUser(username, PagesUtilis.TRIPS_PER_PAGE, page);
        request.setAttribute(SecurityUtils.TRIPS_RESULT, trips);
        request.setAttribute(SecurityUtils.PAGE, page);
        request.setAttribute(SecurityUtils.TITLE_PAGE, "Trips organized by " + username);
        return request.getRequestDispatcher("/WEB-INF/pages/trips_board.jsp");
    }

    private RequestDispatcher getFollowers(HttpServletRequest request, String username, int size, int page) {       // TODO page size
        List<OtherUserDTO> followers = userService.getFollowers(username, size, page);
        request.setAttribute(SecurityUtils.USER_RESULTS, followers);
        request.setAttribute(SecurityUtils.PAGE, page);
        request.setAttribute(SecurityUtils.TITLE_PAGE, username + "'s followers");
        return request.getRequestDispatcher("/WEB-INF/pages/users_board.jsp");
    }

    private RequestDispatcher getFollowing(HttpServletRequest request, String username, int size, int page) {   // TODO page size
        List<OtherUserDTO> following = userService.getFollowing(username, size, page);
        request.setAttribute(SecurityUtils.USER_RESULTS, following);
        request.setAttribute(SecurityUtils.PAGE, page);
        request.setAttribute(SecurityUtils.TITLE_PAGE, username + "'s following");
        return request.getRequestDispatcher("/WEB-INF/pages/users_board.jsp");
    }

    private String followUser(String me, String friend){
        return userService.follow(me, friend);
    }

    private String unfollowUser(String me, String friend){
        return userService.unfollow(me, friend);
    }



    @Override

    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        logger.info(httpServletRequest.getQueryString());
        processRequest(httpServletRequest,httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        processRequest(httpServletRequest,httpServletResponse);
    }

    private void processRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {

        if (httpServletRequest.getSession() == null) {
            logger.error("Error. Access denied");
            httpServletResponse.sendRedirect("login");
            return;
        }

        AuthenticatedUserDTO authenticatedUserDTO = SecurityUtils.getAuthenticatedUser(httpServletRequest);
        if (authenticatedUserDTO == null) {
            logger.error("Error. Access denied");
            httpServletResponse.sendRedirect("login");
            return;
        }

        if(authenticatedUserDTO instanceof AdminDTO){
            logger.error("Error. Access denied");
            httpServletResponse.sendRedirect("admin");
            return;
        }

        RequestDispatcher requestDispatcher;
        String me = authenticatedUserDTO.getUsername();
        String username = httpServletRequest.getParameter("username");
        httpServletRequest.setAttribute("itsMe", true);
        authenticatedUserDTO = userService.getUser(username, me);

        boolean itsMe = username != null && username.equals(me);

        // if it's not user's own profile
        if (!itsMe) {
            httpServletRequest.setAttribute("itsMe", false);
        }

        String show = httpServletRequest.getParameter("show");
        String action = httpServletRequest.getParameter("action");
        int page;
        try {
            page = Integer.parseInt(httpServletRequest.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        if(show != null) {
            if (show.equals("reviews")) {
                requestDispatcher = getUserReviews(httpServletRequest, username, page);
            } else if (show.equals("organizedTrips")) {
                requestDispatcher = getOrganizedTrips(httpServletRequest, username, page);
            } else if (show.equals("followers")) {
                requestDispatcher = getFollowers(httpServletRequest, authenticatedUserDTO.getUsername(), PagesUtilis.USERS_PER_PAGE + 1, page);
            } else if (show.equals("following")) {
                requestDispatcher = getFollowing(httpServletRequest, authenticatedUserDTO.getUsername(), PagesUtilis.USERS_PER_PAGE + 1, page);
            }else{
                logger.error("Error. Invalid value for parameter show " + show);
                requestDispatcher = httpServletRequest.getRequestDispatcher("/WEB-INF/pages/user.jsp");
            }
        }else if(!itsMe && action!=null){
            if (action.equals("follow")) {
                httpServletResponse.setContentType("text/plain");
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.getWriter().write(followUser(me,username));
                return;
            }else if (action.equals("unfollow")) {
                httpServletResponse.setContentType("text/plain");
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.getWriter().write(unfollowUser(me,username));
                return;
            }else{
                logger.error("Error. Invalid value for parameter action " + action);
            }
            httpServletRequest.setAttribute(SecurityUtils.USER, authenticatedUserDTO);
            requestDispatcher = httpServletRequest.getRequestDispatcher("/WEB-INF/pages/user.jsp");
        }else{
            httpServletRequest.setAttribute(SecurityUtils.USER, authenticatedUserDTO);
            requestDispatcher = httpServletRequest.getRequestDispatcher("/WEB-INF/pages/user.jsp");
        }
        requestDispatcher.forward(httpServletRequest, httpServletResponse);
    }
}
