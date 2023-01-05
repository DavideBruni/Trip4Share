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

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    //private UserService userService = ServiceLocator.getUserService();
    //private TripService tripService = ServiceLocator.getTripService();
    private static Logger logger = LoggerFactory.getLogger(SearchServlet.class);


    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
       RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher("/WEB-INF/pages/search.jsp");
        requestDispatcher.forward(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
       String url = "explore?";

        String destination = httpServletRequest.getParameter("destination");
        String username = httpServletRequest.getParameter("username");
        String min_price = httpServletRequest.getParameter("min_price");
        String tag = httpServletRequest.getParameter("tag");
        if(username != null && !username.equals("")){
            url = url + "searchFor=user&value=" + username;
        }else if(destination != null && !destination.equals("")){
            url = url + "searchFor=destination&value=" + destination;
        }else if(min_price != null && !min_price.equals("")){
            url = url + "searchFor=price&value=" + min_price;
            url = url + "&max_value=" + httpServletRequest.getParameter("max_price");
        }else if(tag != null && !tag.equals("")){
            url = url + "searchFor=tags&value=" + tag;
        }

        if(username == null){
            url = url + "&return=" + httpServletRequest.getParameter("return_date");
            url = url + "&departure=" + httpServletRequest.getParameter("departure_date");
        }

        url = url + "&page=" + 1;
        httpServletResponse.sendRedirect(url);
    }

}