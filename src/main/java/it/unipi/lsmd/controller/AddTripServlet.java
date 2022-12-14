package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.TripDetailsDTO;
import it.unipi.lsmd.service.ServiceLocator;
import it.unipi.lsmd.service.TripService;
import it.unipi.lsmd.utils.SecurityUtils;
import it.unipi.lsmd.utils.TripUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addTrip")
public class AddTripServlet extends HttpServlet{
    private final TripService tripService = ServiceLocator.getTripService();
    private static Logger logger = LoggerFactory.getLogger(AddTripServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info(request.getQueryString());

        if(request.getSession() == null || request.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY) == null) {
            response.sendRedirect(request.getContextPath());
            return;
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/pages/create_trip.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        processRequest(httpServletRequest, httpServletResponse);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(request.getSession()==null || request.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY) == null) {
            response.sendRedirect(request.getContextPath());
            return;
        }
        try {
            TripDetailsDTO trip = TripUtils.tripDetailsDTOfromRequest(request);
            logger.info(trip.toString());

            if(tripService.addTrip(trip)) {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/pages/success.jsp");
                requestDispatcher.forward(request, response);
            }else{
                logger.error("Error while creating a new Trip");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/pages/unsuccess.jsp");
                requestDispatcher.forward(request, response);
            }
        }catch(Exception e){
            logger.error("Error while creating a new Trip " + e);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/pages/unsuccess.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
