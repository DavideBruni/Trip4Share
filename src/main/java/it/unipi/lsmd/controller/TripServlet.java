package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.dto.TripDetailsDTO;
import it.unipi.lsmd.service.ServiceLocator;
import it.unipi.lsmd.service.TripService;
import it.unipi.lsmd.utils.SecurityUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

@WebServlet("/trip")
public class TripServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        AuthenticatedUserDTO authenticatedUserDTO = SecurityUtils.getAuthenticatedUser(httpServletRequest);

        // check if user is authenticated
        if(authenticatedUserDTO == null){
            httpServletResponse.sendRedirect("login");
            return;
        }

        TripService tripService = ServiceLocator.getTripService();

        String targetJSP = "/WEB-INF/pages/trip.jsp";
        String trip_id = httpServletRequest.getParameter("id");

        TripDetailsDTO trip = tripService.getTrip(trip_id);
        httpServletRequest.setAttribute("trip", trip);

        String action = httpServletRequest.getParameter("action");
        try{

            if(action.equals("add")){
                System.out.println("add to wishlist");

                // create a map
                HashMap<String, Object> map = new HashMap<>();
                map.put("title", trip.getTitle());
                map.put("departure_date", (LocalDate)trip.getDepartureDate());
                map.put("return_date", (LocalDate) trip.getReturnDate());

                tripService.addToWishlist(authenticatedUserDTO.getUsername(), trip_id, map);
                // TODO - increase counter in MongoDB

            }else if(action.equals("remove")){
                System.out.println("remove from wishlist");
                tripService.removeFromWishlist(authenticatedUserDTO.getUsername(), trip_id);

                // TODO - decrease counter in MongoDB

            }


        }catch (NullPointerException e){ }



        RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(targetJSP);
        requestDispatcher.forward(httpServletRequest, httpServletResponse);

    }
}
