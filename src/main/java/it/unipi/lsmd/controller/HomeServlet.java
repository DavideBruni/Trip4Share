package it.unipi.lsmd.controller;


import it.unipi.lsmd.dto.SuggestedUserDTO;
import it.unipi.lsmd.dto.TripHomeDTO;
import it.unipi.lsmd.service.ServiceLocator;
import it.unipi.lsmd.service.TripService;
import it.unipi.lsmd.service.UserService;
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
    private UserService userService = ServiceLocator.getUserService();

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Show Trips
        //TODO - Use session variables
        // TODO - Handle pagination
        // TODO - Handle session empty/not started
        String usernameFromSession = "fausto";
        String targetJSP = "/WEB-INF/pages/home.jsp";
        List<TripHomeDTO> trips =  tripService.getTripsOrganizedByFollowers(usernameFromSession);
        request.setAttribute("trips",trips);

        //Show Suggested user
        List<SuggestedUserDTO> suggested = userService.getSuggestedUsers(usernameFromSession);
        request.setAttribute("suggested",suggested);

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
