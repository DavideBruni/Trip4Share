package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.dto.TripDetailsDTO;
import it.unipi.lsmd.dto.TripSummaryDTO;
import it.unipi.lsmd.service.ServiceLocator;
import it.unipi.lsmd.service.TripService;
import it.unipi.lsmd.utils.SecurityUtils;
import it.unipi.lsmd.utils.TripUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

@WebServlet("/trip")
public class TripServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        AuthenticatedUserDTO authenticatedUserDTO = SecurityUtils.getAuthenticatedUser(httpServletRequest);

        TripService tripService = ServiceLocator.getTripService();

        String targetJSP = "/WEB-INF/pages/trip.jsp";
        String trip_id = httpServletRequest.getParameter("id");

        TripDetailsDTO trip = tripService.getTrip(trip_id);
        if(authenticatedUserDTO != null) {
            LocalDateTime last_update = tripService.wishlistUpdateTime(authenticatedUserDTO.getUsername(), trip.getId());
            if(last_update == null)
                trip.setInWishlist(false);
            try {

                String action = httpServletRequest.getParameter("action");

                if((action.equals("add") && last_update == null) || (last_update != null && trip.getLast_modified().isAfter(last_update))){
                    TripSummaryDTO tripSummary = TripUtils.tripSummaryFromTripDetails(trip);
                    tripService.addToWishlist(authenticatedUserDTO.getUsername(), trip_id, tripSummary);
                    trip.setInWishlist(true);
                }else if(action.equals("remove") && last_update != null){
                    tripService.removeFromWishlist(authenticatedUserDTO.getUsername(), trip_id);
                    trip.setInWishlist(false);
                }

            } catch (NullPointerException e) {
            }
            httpServletRequest.setAttribute(SecurityUtils.STATUS,tripService.getJoinStatus(trip_id,authenticatedUserDTO.getUsername()));
        }
        httpServletRequest.setAttribute("trip", trip);

        RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(targetJSP);
        requestDispatcher.forward(httpServletRequest, httpServletResponse);

    }
}
