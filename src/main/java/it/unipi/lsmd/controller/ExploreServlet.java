package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.OtherUserDTO;
import it.unipi.lsmd.dto.PriceDestinationDTO;
import it.unipi.lsmd.dto.TripSummaryDTO;
import it.unipi.lsmd.service.ServiceLocator;
import it.unipi.lsmd.service.TripService;
import it.unipi.lsmd.service.UserService;
import it.unipi.lsmd.utils.PagesUtilis;
import it.unipi.lsmd.utils.SecurityUtils;
import it.unipi.lsmd.utils.SessionUtils;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

@WebServlet("/explore")
public class ExploreServlet extends HttpServlet {

    private TripService tripService = ServiceLocator.getTripService();
    private UserService userService = ServiceLocator.getUserService();

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

    }

    private RequestDispatcher cheapestDestinations(HttpServletRequest request, String start, String end, int page) {
        List<TripSummaryDTO> trips = tripService.cheapestTripForDestinationInPeriod(start,end,page, PagesUtilis.OBJECT_PER_PAGE_SEARCH);
        request.setAttribute("trips", trips);
        return  request.getRequestDispatcher("/WEB-INF/pages/explore.jsp");
    }

    private RequestDispatcher cheapestDestinationsByAvg(HttpServletRequest request, int page) {
        List<PriceDestinationDTO> destinations = tripService.cheapestDestinationsByAvg(page, PagesUtilis.OBJECT_PER_PAGE_SEARCH);
        request.setAttribute("dest&price", destinations);
        return  request.getRequestDispatcher("/WEB-INF/pages/explore.jsp");
    }

    private RequestDispatcher mostPopularByPeriod(HttpServletRequest request, String start, String end, int page) {
        List<String> destinations = tripService.mostPopularDestinationsByPeriod(start, end ,page, PagesUtilis.OBJECT_PER_PAGE_SEARCH);
        request.setAttribute("destinations", destinations);
        return  request.getRequestDispatcher("/WEB-INF/pages/explore.jsp");
    }

    private RequestDispatcher mostPopularByPrice(HttpServletRequest request, double start, double end, int page) {
        List<String> destinations = tripService.mostPopularDestinationsByPrice(start, end ,page, PagesUtilis.OBJECT_PER_PAGE_SEARCH);
        request.setAttribute("destinations", destinations);
        return  request.getRequestDispatcher("/WEB-INF/pages/explore.jsp");
    }

    private RequestDispatcher mostPopularByTag(HttpServletRequest request, String tag, int page) {
        List<String> destinations = tripService.mostPopularDestinationsByTag(tag,page, PagesUtilis.OBJECT_PER_PAGE_SEARCH);
        request.setAttribute("destinations", destinations);
        return  request.getRequestDispatcher("/WEB-INF/pages/explore.jsp");
    }

    private RequestDispatcher mostPopularDestinations(HttpServletRequest request, int page) {
        List<String> destinations = tripService.mostPopularDestinations(page,PagesUtilis.OBJECT_PER_PAGE_SEARCH);
        request.setAttribute("destinations", destinations);
        return  request.getRequestDispatcher("/WEB-INF/pages/explore.jsp");
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
        List<TripSummaryDTO> trips = tripService.getTripsByDestination(value, departure_date, return_date, PagesUtilis.OBJECT_PER_PAGE_SEARCH, page);
        request.setAttribute(SecurityUtils.TRIPS_RESULT, trips);
        request.setAttribute(SecurityUtils.TITLE_PAGE, "Results for " + value);
        return request.getRequestDispatcher("/WEB-INF/pages/trips_board.jsp");
    }

    private RequestDispatcher searchTags(HttpServletRequest request, String value, int page) {
        String departure_date = request.getParameter("departure");
        String return_date = request.getParameter("return");
        List<TripSummaryDTO> trips = tripService.getTripsByTag(value, departure_date, return_date, PagesUtilis.OBJECT_PER_PAGE_SEARCH, page);
        request.setAttribute(SecurityUtils.TRIPS_RESULT, trips);
        request.setAttribute(SecurityUtils.TITLE_PAGE, "Results for " + value);
        return request.getRequestDispatcher("/WEB-INF/pages/trips_board.jsp");
    }

    private RequestDispatcher searchPrice(HttpServletRequest request, String min_price, String max_price, int page) {
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
        request.setAttribute(SecurityUtils.TRIPS_RESULT, trips);
        request.setAttribute(SecurityUtils.TITLE_PAGE, "Results for " + min + " - " + max + "€");
        return request.getRequestDispatcher("/WEB-INF/pages/trips_board.jsp");
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
            requestDispatcher = searchPrice(httpServletRequest, value, max_value, page);
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
}
