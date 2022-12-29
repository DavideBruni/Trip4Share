package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.*;
import it.unipi.lsmd.service.ServiceLocator;
import it.unipi.lsmd.service.TripService;
import it.unipi.lsmd.service.UserService;
import it.unipi.lsmd.service.impl.UserServiceImpl;
import it.unipi.lsmd.utils.PagesUtilis;
import it.unipi.lsmd.utils.SecurityUtils;

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



    private RequestDispatcher getUserReviews(HttpServletRequest request, List<ReviewDTO> reviews, int page){

        int start_index = (page-1) * PagesUtilis.REVIEWS_PER_PAGE;
        int end_index = start_index + PagesUtilis.REVIEWS_PER_PAGE;
        request.setAttribute(SecurityUtils.REVIEWS_KEY, reviews.subList(start_index, Math.min(end_index, reviews.size())));
        request.setAttribute(SecurityUtils.PAGE, page);
        return request.getRequestDispatcher("/WEB-INF/pages/reviews_board.jsp");
    }

    private RequestDispatcher getOrganizedTrips(HttpServletRequest request, String username, int page){
        List<TripSummaryDTO> trips = tripService.getTripsOrganizedByUser(username);
        request.setAttribute(SecurityUtils.TRIPS_RESULT, trips);
        request.setAttribute(SecurityUtils.PAGE,page);
        request.setAttribute(SecurityUtils.TITLE_PAGE, "Trips organized by "+ username);
        return request.getRequestDispatcher("/WEB-INF/pages/trips_board.jsp");
    }

    private RequestDispatcher getFollowers(HttpServletRequest request, String username, int page){
        List<OtherUserDTO> followers = userService.getFollowers(username);
        request.setAttribute(SecurityUtils.USER_RESULTS, followers);
        request.setAttribute(SecurityUtils.PAGE, page);
        request.setAttribute(SecurityUtils.TITLE_PAGE, username+"'s followers");
        return request.getRequestDispatcher("/WEB-INF/pages/users_board.jsp");    }

    private RequestDispatcher getFollowing(HttpServletRequest request, String username, int page){
        List<OtherUserDTO> following = userService.getFollowing(username);
        request.setAttribute(SecurityUtils.USER_RESULTS, following);
        request.setAttribute(SecurityUtils.PAGE, page);
        request.setAttribute(SecurityUtils.TITLE_PAGE, username+"'s following");
        return request.getRequestDispatcher("/WEB-INF/pages/users_board.jsp");
    }



    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        AuthenticatedUserDTO authenticatedUserDTO = SecurityUtils.getAuthenticatedUser(httpServletRequest);

        // check if user is authenticated
        if(authenticatedUserDTO == null){
            httpServletResponse.sendRedirect("login");
            return;
        }

        RequestDispatcher requestDispatcher = null;

        String username = httpServletRequest.getParameter("username");
        httpServletRequest.setAttribute("itsMe", true);

        boolean itsMe = username != null && username.equals(authenticatedUserDTO.getUsername());


        // if it's not user's own profile
        if(!itsMe){
            authenticatedUserDTO = userService.getUser(username);
            httpServletRequest.setAttribute("itsMe", false);
        }


        // set n_followers and n_following
        ((RegisteredUserDTO)authenticatedUserDTO).setN_followers(userService.getFollowersNumber(authenticatedUserDTO.getUsername()));
        ((RegisteredUserDTO)authenticatedUserDTO).setN_following(userService.getFollowingNumber(authenticatedUserDTO.getUsername()));
        ((RegisteredUserDTO)authenticatedUserDTO).setAvg_rating(userService.getRating(authenticatedUserDTO.getUsername()));

        String show = httpServletRequest.getParameter("show");
        int page;
        try{
            page = Integer.parseInt(httpServletRequest.getParameter("page"));
        }catch (Exception e){
            page = 1;
        }

        if(show != null && show.equals("reviews")){
            // TODO - controllare se va bene
            requestDispatcher = getUserReviews(httpServletRequest, ((RegisteredUserDTO) authenticatedUserDTO).getReviews(), page);
        }else if(show != null && show.equals("organizedTrips")) {
            requestDispatcher = getOrganizedTrips(httpServletRequest, username, page);
        }else if(show != null && show.equals("followers")){
            requestDispatcher = getFollowers(httpServletRequest, authenticatedUserDTO.getUsername(), page);
        }else if(show != null && show.equals("following")){
            requestDispatcher = getFollowing(httpServletRequest, authenticatedUserDTO.getUsername(), page);
        }else{
            httpServletRequest.setAttribute(SecurityUtils.AUTHENTICATED_USER_KEY, authenticatedUserDTO);
            requestDispatcher = httpServletRequest.getRequestDispatcher("/WEB-INF/pages/user.jsp");
        }


        requestDispatcher.forward(httpServletRequest, httpServletResponse);
    }

}
