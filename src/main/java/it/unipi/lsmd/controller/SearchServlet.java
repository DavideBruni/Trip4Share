package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.OtherUserDTO;
import it.unipi.lsmd.dto.TripSummaryDTO;
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
                if (searchFor.equalsIgnoreCase(String.valueOf(PagesUtilis.SEARCH_TYPE.USER))) {
                    requestDispatcher = searchUser(request,value,page);
                } else if (searchFor.equalsIgnoreCase(String.valueOf(PagesUtilis.SEARCH_TYPE.DESTINATION))) {
                    // search trip for destination and Date
                    requestDispatcher = searchDest(request,value,page);
                } else if (searchFor.equalsIgnoreCase(String.valueOf(PagesUtilis.SEARCH_TYPE.TAGS))){
                    // search trip for tags and date
                    requestDispatcher = searchTags(request,value,page);
                }else{
                    requestDispatcher = request.getRequestDispatcher("/WEB-INF/pages/home.jsp");
                }
                requestDispatcher.forward(request, response);
            }
        }

    }

    private RequestDispatcher searchUser(HttpServletRequest request,String value,int page){
        List<OtherUserDTO> searchedUsers = userService.searchUsers(value, PagesUtilis.OBJECT_PER_PAGE_SEARCH, page);
        request.setAttribute("users_founded", searchedUsers);
        return  request.getRequestDispatcher("/WEB-INF/pages/searchResult.jsp");
    }

    private RequestDispatcher searchDest(HttpServletRequest request,String value,int page){
        String depDate = request.getParameter("depDate");
        String retDate = request.getParameter("retDate");
        List<TripSummaryDTO> trips = tripService.getTripsByDestination(value,depDate, retDate, PagesUtilis.OBJECT_PER_PAGE_SEARCH, page);
        request.setAttribute("trips", trips);
        return request.getRequestDispatcher("/WEB-INF/pages/searchResult.jsp");
    }
    private RequestDispatcher searchTags(HttpServletRequest request,String value,int page){
        String depDate = request.getParameter("depDate");
        String retDate = request.getParameter("retDate");
        List<TripSummaryDTO> trips = tripService.getTripsByTag(value,depDate, retDate, PagesUtilis.OBJECT_PER_PAGE_SEARCH, page);
        request.setAttribute("trips", trips);
        return request.getRequestDispatcher("/WEB-INF/pages/searchResult.jsp");
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
