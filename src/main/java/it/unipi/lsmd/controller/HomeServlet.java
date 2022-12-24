package it.unipi.lsmd.controller;


import it.unipi.lsmd.dto.OtherUserDTO;
import it.unipi.lsmd.dto.TripSummaryDTO;
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
    private final TripService tripService = ServiceLocator.getTripService();
    private final UserService userService = ServiceLocator.getUserService();

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Show Trips
        if(request.getSession()==null || request.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY) == null) {
            response.sendRedirect(request.getContextPath());
            return;
        }
        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        }catch (IllegalArgumentException e){
            page = 1;
        }

        try {
            String usernameFromSession = SecurityUtils.getAuthenticatedUser(request).getUsername();
            String targetJSP = "/WEB-INF/pages/registered_home.jsp";
            List<TripSummaryDTO> trips =  tripService.getTripsOrganizedByFollowers(usernameFromSession,PagesUtilis.OBJECT_PER_PAGE_SEARCH,page);
            request.setAttribute("trips",trips);

            //Show Suggested user
            List<OtherUserDTO> suggested = userService.getSuggestedUsers(usernameFromSession,PagesUtilis.SUGGESTED_USERS);
            request.setAttribute("suggestedUser",suggested);

            List<TripSummaryDTO> suggested_trips = tripService.getSuggestedTrips(usernameFromSession, PagesUtilis.SUGGESTED_TRAVELS);
            request.setAttribute("suggestedTrips",suggested_trips);

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
