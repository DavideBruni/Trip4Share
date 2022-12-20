package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.PriceDestinationDTO;
import it.unipi.lsmd.dto.TripHomeDTO;
import it.unipi.lsmd.service.ServiceLocator;
import it.unipi.lsmd.service.TripService;
import it.unipi.lsmd.utils.PagesUtilis;
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
        List<TripHomeDTO> trips = tripService.cheapestTripForDestinationInPeriod(start,end,page, PagesUtilis.OBJECT_PER_PAGE_SEARCH);
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

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        processRequest(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        processRequest(httpServletRequest, httpServletResponse);
    }
}
