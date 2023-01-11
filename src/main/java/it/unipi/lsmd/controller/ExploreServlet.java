package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.DestinationsDTO;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/explore")
public class ExploreServlet extends HttpServlet {

    private final TripService tripService = ServiceLocator.getTripService();
    private final UserService userService = ServiceLocator.getUserService();
    private static final Logger logger = LoggerFactory.getLogger(ExploreServlet.class);

    private void processRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        String value = httpServletRequest.getParameter("value");
        int page;
        try{
            page = Integer.parseInt(httpServletRequest.getParameter("page"));
        }catch (Exception e){
            page = 1;
        }
        httpServletRequest.setAttribute(SecurityUtils.PAGE, page);

        String searchFor = httpServletRequest.getParameter("searchFor");
        RequestDispatcher requestDispatcher;
        if(searchFor != null && searchFor.equals("destination")){
            requestDispatcher = searchDestination(httpServletRequest, value, page);
        }else if(searchFor != null && searchFor.equals("user")){
            requestDispatcher = searchUser(httpServletRequest, value, page);
        }else if(searchFor != null && searchFor.equals("price")){
            String max_value = httpServletRequest.getParameter("max_value");
            requestDispatcher = searchTripsByPrice(httpServletRequest, value, max_value, page);
        }else if(searchFor != null && searchFor.equals("tags")){
            requestDispatcher = searchTags(httpServletRequest, value, page);
        }else{
            httpServletRequest.removeAttribute(SecurityUtils.PAGE);
            requestDispatcher = httpServletRequest.getRequestDispatcher("/WEB-INF/pages/search.jsp");
        }
        requestDispatcher.forward(httpServletRequest, httpServletResponse);
    }


    private RequestDispatcher searchUser(HttpServletRequest request, String value, int page) {
        List<OtherUserDTO> users = userService.searchUsers(value, PagesUtilis.OBJECT_PER_PAGE_SEARCH, page);
        request.setAttribute(SecurityUtils.USER_RESULTS, users);
        request.setAttribute(SecurityUtils.TITLE_PAGE, "Results for " + value);
        return request.getRequestDispatcher("/WEB-INF/pages/users_board.jsp");
    }

    private RequestDispatcher searchDestination(HttpServletRequest request, String value, int page) {
        String departure_date = request.getParameter("departure");
        String return_date = request.getParameter("return");
        List<DestinationsDTO> destinations;
        String title;

        List<TripSummaryDTO> trips = tripService.getTripsByDestination(value, departure_date, return_date, PagesUtilis.OBJECT_PER_PAGE_SEARCH, page);

        if(departure_date != null && return_date != null && !departure_date.equals("") && !return_date.equals("")){
            destinations = tripService.mostPopularDestinationsByPeriod(departure_date, return_date, PagesUtilis.SUGGESTIONS_EXPLORE);
            title = "TOP " + PagesUtilis.SUGGESTIONS_EXPLORE + " Destinations (on that period)";
        }else{
            destinations = tripService.mostPopularDestinations(PagesUtilis.SUGGESTIONS_EXPLORE);
            title = "TOP " + PagesUtilis.SUGGESTIONS_EXPLORE + " Destinations";
        }

        request.setAttribute(SecurityUtils.TRIPS_RESULT, trips);
        request.setAttribute(SecurityUtils.SUGGESTIONS_EXPLORE_TITLE, title);
        request.setAttribute(SecurityUtils.SUGGESTIONS_EXPLORE, onlyDestinations(destinations));
        request.setAttribute(SecurityUtils.TITLE_PAGE, "Results for " + value);
        return request.getRequestDispatcher("/WEB-INF/pages/explore.jsp");
    }


    private RequestDispatcher searchTags(HttpServletRequest request, String value, int page) {
        String departure_date = request.getParameter("departure");
        String return_date = request.getParameter("return");
        List<TripSummaryDTO> trips = tripService.getTripsByTag(value, departure_date, return_date, PagesUtilis.OBJECT_PER_PAGE_SEARCH, page);
        List<DestinationsDTO> destinations = tripService.mostPopularDestinationsByTag(value, PagesUtilis.SUGGESTIONS_EXPLORE);
        request.setAttribute(SecurityUtils.SUGGESTIONS_EXPLORE_TITLE, "TOP 5 Destinations");
        request.setAttribute(SecurityUtils.SUGGESTIONS_EXPLORE, onlyDestinations(destinations));
        request.setAttribute(SecurityUtils.TRIPS_RESULT, trips);
        request.setAttribute(SecurityUtils.TITLE_PAGE, "Results for " + value);
        return request.getRequestDispatcher("/WEB-INF/pages/explore.jsp");
    }

    private RequestDispatcher searchTripsByPrice(HttpServletRequest request, String min_price, String max_price, int page) {
        String departure_date = request.getParameter("departure");
        String return_date = request.getParameter("return");
        double min,max;
        try{
            min = Double.parseDouble(min_price);
        }catch (NumberFormatException e){
            logger.error("Error. Invalid price " + e);
            return request.getRequestDispatcher("search");
        }
        try{
            max = Double.parseDouble(max_price);
        }catch (NumberFormatException e){
            max = 0;
        }
        List<TripSummaryDTO> trips = tripService.getTripsByPrice(min, max, departure_date, return_date, PagesUtilis.OBJECT_PER_PAGE_SEARCH, page);
        List<DestinationsDTO> destinations = tripService.mostPopularDestinationsByPrice(min, max, PagesUtilis.SUGGESTIONS_EXPLORE);
        System.out.println(destinations);
        request.setAttribute(SecurityUtils.SUGGESTIONS_EXPLORE, onlyDestinations(destinations));
        request.setAttribute(SecurityUtils.SUGGESTIONS_EXPLORE_TITLE, "TOP 5 Destinations");
        request.setAttribute(SecurityUtils.TRIPS_RESULT, trips);
        String title_price;
        if(max!=0)
            title_price="Results for " + min + "€ - " + max + "€";
        else
            title_price="Trips from " + min + "€";
        request.setAttribute(SecurityUtils.TITLE_PAGE, title_price);
        return request.getRequestDispatcher("/WEB-INF/pages/explore.jsp");
    }



    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        logger.info(httpServletRequest.getQueryString());
        processRequest(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        processRequest(httpServletRequest, httpServletResponse);
    }

    private List<String> onlyDestinations(List<DestinationsDTO> destinations) {
        List<String> dests = new ArrayList<>();
        for(DestinationsDTO x: destinations){
            dests.add(x.getDestination());
        }
        return dests;
    }
}
