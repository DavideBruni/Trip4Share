package it.unipi.lsmd.controller;


import it.unipi.lsmd.dto.OtherUserDTO;
import it.unipi.lsmd.dto.TripHomeDTO;
import it.unipi.lsmd.service.ServiceLocator;
import it.unipi.lsmd.service.TripService;
import it.unipi.lsmd.service.UserService;
import it.unipi.lsmd.utils.PagesUtilis;
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
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(HomeServlet.class);
    private TripService tripService = ServiceLocator.getTripService();
    private UserService userService = ServiceLocator.getUserService();

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Show Trips
        // TODO - Handle pagination Trip Pagination (Populate Neo4j with good relation)
        try {
            String usernameFromSession = SecurityUtils.getAuthenticatedUser(request).getUsername();
            String targetJSP = "/WEB-INF/pages/home.jsp";
            List<TripHomeDTO> trips =  tripService.getTripsOrganizedByFollowers(usernameFromSession);
            request.setAttribute("trips",trips);

            //Show Suggested user
            List<OtherUserDTO> suggested = userService.getSuggestedUsers(usernameFromSession,PagesUtilis.SUGGESTED_USER_HOME);
            request.setAttribute("suggested",suggested);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher(targetJSP);
            requestDispatcher.forward(request, response);
        }catch (NullPointerException ne){
            response.sendRedirect(request.getContextPath());
        }

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
