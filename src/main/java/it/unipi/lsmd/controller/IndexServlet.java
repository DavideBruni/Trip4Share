package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.TripDetailsDTO;
import it.unipi.lsmd.dto.TripSummaryDTO;
import it.unipi.lsmd.service.ServiceLocator;
import it.unipi.lsmd.service.TripService;
import it.unipi.lsmd.utils.PagesUtilis;
import it.unipi.lsmd.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name="IndexServlet", urlPatterns={"", "/index"})
public class IndexServlet extends HttpServlet {

    private final TripService tripService = ServiceLocator.getTripService();
    private static Logger logger = LoggerFactory.getLogger(IndexServlet.class);

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        logger.info(httpServletRequest.getQueryString());
        processRequest(httpServletRequest, httpServletResponse);
    }

    private void processRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        List<TripDetailsDTO> cheapest = tripService.cheapestTripForDestinationInPeriod(null,null, PagesUtilis.TRIP_NUMBER_INDEX);
        List<TripSummaryDTO> mostPopulars = tripService.mostPopularTrips(PagesUtilis.TRIP_NUMBER_INDEX);
        httpServletRequest.setAttribute(SecurityUtils.CHEAPEST_TRIPS,cheapest);
        httpServletRequest.setAttribute(SecurityUtils.MOST_POPULAR,mostPopulars);
        RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher("index.jsp");
        requestDispatcher.forward(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        processRequest(httpServletRequest, httpServletResponse);
    }

}
