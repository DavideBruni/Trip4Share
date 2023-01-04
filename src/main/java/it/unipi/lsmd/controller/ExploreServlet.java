package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.DestinationsDTO;
import it.unipi.lsmd.dto.OtherUserDTO;
import it.unipi.lsmd.dto.TripSummaryDTO;
import it.unipi.lsmd.service.ServiceLocator;
import it.unipi.lsmd.service.TripService;
import it.unipi.lsmd.service.UserService;
import it.unipi.lsmd.utils.PagesUtilis;
import it.unipi.lsmd.utils.SecurityUtils;
import org.javatuples.Pair;

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

    private TripService tripService = ServiceLocator.getTripService();
    private UserService userService = ServiceLocator.getUserService();

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*
        // TODO - handle nullPointerException when do getParameter and other possible exceptions
        if(!SessionUtils.userIsLogged(request)){
            response.sendRedirect("/WEB-INF/pages/home.jsp");
        }else {
            Enumeration parameters =request.getParameterNames();
            RequestDispatcher requestDispatcher;
            if (! parameters.hasMoreElements()) {
                requestDispatcher = mostPopularDestinations(request, 1);
            }else{
                String searchFor = request.getParameter("searchFor");

                int page = 1;
                try {
                    page = Integer.parseInt(request.getParameter("page"));
                }catch (IllegalArgumentException e){
                    response.sendRedirect(request.getContextPath()+"/home");
                }
                if(searchFor==null){
                  requestDispatcher =mostPopularDestinations(request, page);
                }else if (searchFor.equalsIgnoreCase(String.valueOf(PagesUtilis.SEARCH_TYPE.TAGS))){
                    String tag = request.getParameter("tag");
                    requestDispatcher = mostPopularByTag(request,tag,page);
                }else if(searchFor.equalsIgnoreCase(String.valueOf(PagesUtilis.SEARCH_TYPE.PRICE))){
                    double start = Double.parseDouble(request.getParameter("start"));
                    double end = Double.parseDouble(request.getParameter("end"));
                    requestDispatcher = mostPopularByPrice(request,start,end,page);
                }else if(searchFor.equalsIgnoreCase(String.valueOf(PagesUtilis.SEARCH_TYPE.PERIOD))){
                    String start = request.getParameter("start");
                    String end = request.getParameter("end");
                    requestDispatcher = mostPopularByPeriod(request,start,end,page);
                }else if(searchFor.equalsIgnoreCase(String.valueOf(PagesUtilis.SEARCH_TYPE.CHEAP_DEST))){
                    requestDispatcher = cheapestDestinationsByAvg(request,page);
                }else{
                    String start = request.getParameter("start");
                    String end = request.getParameter("end");
                    requestDispatcher = cheapestDestinations(request,start, end,page);
                }
            }// no parameters, show  trip sort by likes
            requestDispatcher.forward(request, response);
        }
         */

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
        request.setAttribute(SecurityUtils.SUGGESTIONS_EXPLORE, destinations);
        request.setAttribute(SecurityUtils.TRIPS_RESULT, trips);
        request.setAttribute(SecurityUtils.TITLE_PAGE, "Results for " + value);
        return request.getRequestDispatcher("/WEB-INF/pages/explore.jsp");
    }

    private RequestDispatcher searchTripsByPrice(HttpServletRequest request, String min_price, String max_price, int page) {
        String departure_date = request.getParameter("departure");
        String return_date = request.getParameter("return");
        int min = Integer.parseInt(min_price);
        int max;
        try{
            max = Integer.parseInt(max_price);
        }catch (NumberFormatException e){
            max = 0;
        }
        List<TripSummaryDTO> trips = tripService.getTripsByPrice(min, max, departure_date, return_date, PagesUtilis.OBJECT_PER_PAGE_SEARCH, page);
        List<DestinationsDTO> destinations = tripService.mostPopularDestinationsByPrice(min, max, PagesUtilis.SUGGESTIONS_EXPLORE);
        System.out.println(destinations);
        request.setAttribute(SecurityUtils.SUGGESTIONS_EXPLORE, onlyDestinations(destinations));
        request.setAttribute(SecurityUtils.SUGGESTIONS_EXPLORE_TITLE, "TOP 5 Destinations");
        request.setAttribute(SecurityUtils.TRIPS_RESULT, trips);
        request.setAttribute(SecurityUtils.TITLE_PAGE, "Results for " + min + " - " + max + "€");
        return request.getRequestDispatcher("/WEB-INF/pages/explore.jsp");
    }



    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        //processRequest(httpServletRequest, httpServletResponse);

        //TODO spostare sopra
        String targetJSP = "/WEB-INF/pages/trips_board.jsp";

        //serach ---> explore?searchFor=destination&...&page=1

        String value = httpServletRequest.getParameter("value");
        int page;
        try{
            page = Integer.parseInt(httpServletRequest.getParameter("page"));
        }catch (Exception e){
            page = 1;
        }
        httpServletRequest.setAttribute(SecurityUtils.PAGE, page);

        String searchFor = httpServletRequest.getParameter("searchFor");
        RequestDispatcher requestDispatcher = null;
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
            //TODO
            requestDispatcher = httpServletRequest.getRequestDispatcher("/WEB-INF/pages/error404.jsp");
        }

        requestDispatcher.forward(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        System.out.println("qui");
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
