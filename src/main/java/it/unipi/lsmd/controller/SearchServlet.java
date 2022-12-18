package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.OtherUserDTO;
import it.unipi.lsmd.dto.TripHomeDTO;
import it.unipi.lsmd.service.ServiceLocator;
import it.unipi.lsmd.service.TripService;
import it.unipi.lsmd.service.UserService;
import it.unipi.lsmd.utils.PagesUtilis;
import it.unipi.lsmd.utils.SessionUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    private UserService userService = ServiceLocator.getUserService();
    private TripService tripService = ServiceLocator.getTripService();
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO - handle nullPointerException when do getParameter and other possible exceptions
        if(!SessionUtils.userIsLogged(request)){
            response.sendRedirect("/WEB-INF/pages/home.jsp");
        }else {
            String searchFor = request.getParameter("searchFor");      //search user or Destination
            String value = request.getParameter("value");              //search value
            int page = Integer.parseInt(request.getParameter("page"));
            if (value == null || searchFor == null || value.isEmpty() || searchFor.isEmpty()) {
                response.sendRedirect("/WEB-INF/pages/home.jsp");
            } else {
                RequestDispatcher requestDispatcher;
                String targetJsp = "/WEB-INF/pages/explore.jsp";
                if (searchFor.equalsIgnoreCase(String.valueOf(PagesUtilis.SEARCH_TYPE.USER))) {
                    List<OtherUserDTO> searchedUsers = userService.searchUsers(value, PagesUtilis.OBJECT_PER_PAGE_SEARCH, page);
                    request.setAttribute("users_founded", searchedUsers);
                    requestDispatcher = request.getRequestDispatcher(targetJsp);
                } else if (searchFor.equalsIgnoreCase(String.valueOf(PagesUtilis.SEARCH_TYPE.DESTINATION))) {
                    // search trip for destination and Date
                    String depDate = request.getParameter("depDate");
                    String retDate = request.getParameter("retDate");
                    List<TripHomeDTO> trips = tripService.getTripsByDestination(value,depDate, retDate, PagesUtilis.OBJECT_PER_PAGE_SEARCH, page);
                    request.setAttribute("trips", trips);
                    requestDispatcher = request.getRequestDispatcher(targetJsp);
                } else if (searchFor.equalsIgnoreCase(String.valueOf(PagesUtilis.SEARCH_TYPE.TAGS))){
                    // search trip for tags and date
                    String depDate = request.getParameter("depDate");
                    String retDate = request.getParameter("retDate");
                    List<TripHomeDTO> trips = tripService.getTripsByTag(value,depDate, retDate, PagesUtilis.OBJECT_PER_PAGE_SEARCH, page);
                    request.setAttribute("trips", trips);
                    requestDispatcher = request.getRequestDispatcher(targetJsp);
                }else{
                    requestDispatcher = request.getRequestDispatcher("/WEB-INF/pages/home.jsp");
                }
                requestDispatcher.forward(request, response);
            }
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
