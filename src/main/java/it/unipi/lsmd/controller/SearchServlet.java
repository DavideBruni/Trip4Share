package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.OtherUserDTO;
import it.unipi.lsmd.dto.TripSummaryDTO;
import it.unipi.lsmd.service.ServiceLocator;
import it.unipi.lsmd.service.TripService;
import it.unipi.lsmd.service.UserService;
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

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    private UserService userService = ServiceLocator.getUserService();
    private TripService tripService = ServiceLocator.getTripService();

    /*
    private void processRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        // TODO - handle nullPointerException when do getParameter and other possible exceptions

        int page;
        try {
            page = Integer.parseInt(httpServletRequest.getParameter("page"));
        } catch (NumberFormatException e) {
            page = 1;
        }

        RequestDispatcher requestDispatcher = null;
        if (httpServletRequest.getMethod().equals("POST")) {
            String username = httpServletRequest.getParameter("username");
            if (username != null) {
                requestDispatcher = searchUser(httpServletRequest, username, page);
            } else {
                String destination = httpServletRequest.getParameter("destination");
                String tags = httpServletRequest.getParameter("tags");
                if (destination != null) {
                    requestDispatcher = searchDest(httpServletRequest, destination, page);
                } else if (tags != null) {
                    requestDispatcher = searchTags(httpServletRequest, tags, page);
                } else {
                    // TODO - filter by price
                    requestDispatcher = null;
                }
            }
        } else {
            requestDispatcher = httpServletRequest.getRequestDispatcher("/WEB-INF/pages/search.jsp");
        }
        requestDispatcher.forward(httpServletRequest, httpServletResponse);
    }

     */


    private RequestDispatcher searchUser(HttpServletRequest request, String value, int page) {
        List<OtherUserDTO> users = userService.searchUsers(value, PagesUtilis.OBJECT_PER_PAGE_SEARCH, page);
        request.setAttribute(SecurityUtils.SEARCH_RESULTS, users);
        return request.getRequestDispatcher("/WEB-INF/pages/search_board.jsp");
    }

    private RequestDispatcher searchDest(HttpServletRequest request, String value, int page) {
        String departure_date = request.getParameter("departure_date");
        String return_date = request.getParameter("return_date");
        List<TripSummaryDTO> trips = tripService.getTripsByDestination(value, departure_date, return_date, PagesUtilis.OBJECT_PER_PAGE_SEARCH, page);
        System.out.println(trips);
        request.setAttribute(SecurityUtils.SEARCH_RESULTS, trips);
        return request.getRequestDispatcher("/WEB-INF/pages/search_board.jsp");
    }

    private RequestDispatcher searchTags(HttpServletRequest request, String value, int page) {
        String depDate = request.getParameter("depDate");
        String retDate = request.getParameter("retDate");
        List<TripSummaryDTO> trips = tripService.getTripsByTag(value, depDate, retDate, PagesUtilis.OBJECT_PER_PAGE_SEARCH, page);
        request.setAttribute(SecurityUtils.SEARCH_RESULTS, trips);
        return request.getRequestDispatcher("/WEB-INF/pages/search_board.jsp");
    }


    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        //processRequest(httpServletRequest, httpServletResponse);
        RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher("/WEB-INF/pages/search.jsp");
        requestDispatcher.forward(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        //RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher("/WEB-INF/pages/search.jsp");
        //requestDispatcher.forward(httpServletRequest, httpServletResponse);
        String url = "explore?";

        String destination = httpServletRequest.getParameter("destination");
        String username = httpServletRequest.getParameter("username");
        String min_price = httpServletRequest.getParameter("min_price");
        if(username != null){
            url = url + "searchFor=user&value=" + username;
        }else if(destination != null){
            url = url + "searchFor=destination&value=" + destination;
        }else if(min_price != null){
            url = url + "searchFor=price&value=" + min_price;
            url = url + "&max_value=" + httpServletRequest.getParameter("max_price");
        }

        if(username == null){
            url = url + "&return=" + httpServletRequest.getParameter("return_date");
            url = url + "&departure=" + httpServletRequest.getParameter("departure_date");
        }

        url = url + "&page=" + 1;
        httpServletResponse.sendRedirect(url);
    }

}