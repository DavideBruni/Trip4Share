package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.dto.TripSummaryDTO;
import it.unipi.lsmd.service.ServiceLocator;
import it.unipi.lsmd.service.TripService;
import it.unipi.lsmd.utils.PagesUtilis;
import it.unipi.lsmd.utils.SecurityUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/pastTrips")
public class PastTripsServlet extends HttpServlet  {

    private TripService tripService = ServiceLocator.getTripService();


    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        AuthenticatedUserDTO authenticatedUserDTO = SecurityUtils.getAuthenticatedUser(httpServletRequest);
        // check if user is authenticated
        if(authenticatedUserDTO == null){
            httpServletResponse.sendRedirect("login");
            return;
        }

        int page;
        try{
            page = Integer.parseInt(httpServletRequest.getParameter("page"));
        }catch (Exception e){
            page = 1;
        }


        List<TripSummaryDTO> trips = tripService.getPastTrips(authenticatedUserDTO.getUsername(), PagesUtilis.TRIPS_PER_PAGE + 1, page);
        String targetJSP = "/WEB-INF/pages/trips_board.jsp";
        httpServletRequest.setAttribute(SecurityUtils.TITLE_PAGE, "Past Trips");
        httpServletRequest.setAttribute(SecurityUtils.PAGE, page);
        httpServletRequest.setAttribute(SecurityUtils.TRIPS_RESULT, trips);

        RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(targetJSP);
        requestDispatcher.forward(httpServletRequest, httpServletResponse);
    }
}
