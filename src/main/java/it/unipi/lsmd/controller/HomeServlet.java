package it.unipi.lsmd.controller;


import it.unipi.lsmd.dto.TripHomeDTO;
import it.unipi.lsmd.service.ServiceLocator;
import it.unipi.lsmd.service.TripService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(HomeServlet.class);
    private TripService tripService = ServiceLocator.getTripService();

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO - Use session variables
        String usernameFromSession = "fausto";
        String targetJSP = "/WEB-INF/pages/home.jsp";
        List<TripHomeDTO> trips =  tripService.getTripsOrganizedByFollowers(usernameFromSession);
        request.setAttribute("trips",trips);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(targetJSP);
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        processRequest(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        processRequest(httpServletRequest, httpServletResponse);
    }
}
