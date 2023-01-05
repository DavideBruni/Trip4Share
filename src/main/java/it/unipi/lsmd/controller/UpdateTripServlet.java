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

@WebServlet("/updateTrip")
public class UpdateTripServlet extends HttpServlet {
    private final TripService tripService = ServiceLocator.getTripService();
    private static Logger logger = LoggerFactory.getLogger(UpdateTripServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.info(request.getQueryString());
        String id = request.getParameter("id");
        if(request.getSession()==null || request.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY) == null || id == null) {
            response.sendRedirect(request.getContextPath());
            return;
        }
        TripDetailsDTO trip = tripService.getTrip(id);
        if(trip!=null) {
            request.getSession().setAttribute(SecurityUtils.TRIP, trip);
            request.setAttribute(SecurityUtils.MODIFY_FLAG,true);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/pages/create_trip.jsp");
            requestDispatcher.forward(request, response);
        }else{
            response.sendRedirect(request.getContextPath());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession() == null || request.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY) == null) {
            response.sendRedirect(request.getContextPath());
            return;
        }
        try {
            TripDetailsDTO newTrip = TripUtils.tripDetailsDTOfromRequest(request);
            TripDetailsDTO oldTrip = (TripDetailsDTO) request.getSession().getAttribute(SecurityUtils.TRIP);
            newTrip.setId(oldTrip.getId());
            if (tripService.updateTrip(newTrip,oldTrip)) {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/pages/success.jsp");
                requestDispatcher.forward(request, response);
            } else {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/pages/unsuccess.jsp");
                requestDispatcher.forward(request, response);
            }
        }catch (Exception e){
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/pages/unsuccess.jsp");
            requestDispatcher.forward(request, response);
        }
    }

}
