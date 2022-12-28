package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.dto.RegisteredUserDTO;
import it.unipi.lsmd.dto.ReviewDTO;
import it.unipi.lsmd.dto.TripSummaryDTO;
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
        return request.getRequestDispatcher("/WEB-INF/pages/reviews_board.jsp");
    }

    private RequestDispatcher getOrganizedTrips(HttpServletRequest request, int page){
        return null;
    }

    private RequestDispatcher getPastTrips(HttpServletRequest request, int page){
        List<TripSummaryDTO> trips;
        // TODO - da fare
        return request.getRequestDispatcher("/WEB-INF/pages/trip_board.jsp");
    }

    private RequestDispatcher getFollowers(HttpServletRequest request, int page){
        // TODO - da fare
        return null;
    }

    private RequestDispatcher getFollowing(HttpServletRequest request, int page){
        // TODO - da fare
        return null;
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

        String show = httpServletRequest.getParameter("show");

        if(show != null && show.equals("reviews")){
            // TODO - controllare se va bene
            int page;
            try{
                page = Integer.parseInt(httpServletRequest.getParameter("page"));
            }catch (NumberFormatException e){
                page = 1;
            }
            requestDispatcher = getUserReviews(httpServletRequest, ((RegisteredUserDTO) authenticatedUserDTO).getReviews(), page);
        }else if(show != null && show.equals("pastTrips") && itsMe){
            // TODO fare il link che manda a user?username=...&show=oragnizedTrips&page=1
            requestDispatcher = getPastTrips(httpServletRequest, 1);


        }else{
            httpServletRequest.setAttribute(SecurityUtils.AUTHENTICATED_USER_KEY, authenticatedUserDTO);
            requestDispatcher = httpServletRequest.getRequestDispatcher("/WEB-INF/pages/user.jsp");
        }


        requestDispatcher.forward(httpServletRequest, httpServletResponse);
    }

}
