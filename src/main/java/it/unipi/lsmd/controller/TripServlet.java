package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.dto.TripDetailsDTO;
import it.unipi.lsmd.service.ServiceLocator;
import it.unipi.lsmd.service.TripService;
import it.unipi.lsmd.service.WishlistService;
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

@WebServlet("/trip")
public class TripServlet extends HttpServlet {

    private final TripService tripService = ServiceLocator.getTripService();
    private final WishlistService wishlistService = ServiceLocator.getWishlistService();
    private static final Logger logger = LoggerFactory.getLogger(TripServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        logger.info(httpServletRequest.getQueryString());
        processRequest(httpServletRequest,httpServletResponse);

    }

    private void processRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        AuthenticatedUserDTO authenticatedUserDTO = SecurityUtils.getAuthenticatedUser(httpServletRequest);

        String targetJSP = "/WEB-INF/pages/trip.jsp";
        String trip_id = httpServletRequest.getParameter("id");


        TripDetailsDTO trip = tripService.getTrip(trip_id);

        if(trip != null){

            if (authenticatedUserDTO != null) {

                try {

                    String action = httpServletRequest.getParameter("action");
                    trip.setInWishlist(wishlistService.isInWishlist(authenticatedUserDTO.getUsername(), trip));

                    if (action.equals("add") && !trip.isInWishlist()) {
                        boolean inWishlist = wishlistService.addToWishlist(authenticatedUserDTO.getUsername(), trip);
                        trip.setInWishlist(inWishlist);
                    } else if (action.equals("remove") && trip.isInWishlist()) {
                        boolean inWishlsit = wishlistService.removeFromWishlist(authenticatedUserDTO.getUsername(), trip);
                        trip.setInWishlist(inWishlsit);
                    }

                } catch (NullPointerException e) {
                    // no action specified in query
                }
                String join_status = tripService.getJoinStatus(trip_id, authenticatedUserDTO.getUsername());
                httpServletRequest.setAttribute(SecurityUtils.STATUS, join_status);
            }
            logger.info(trip.toString());
        }
        httpServletRequest.setAttribute(SecurityUtils.TRIP, trip);
        RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(targetJSP);
        requestDispatcher.forward(httpServletRequest, httpServletResponse);

    }
}
