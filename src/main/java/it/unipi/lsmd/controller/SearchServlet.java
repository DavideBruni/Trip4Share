package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.OtherUserDTO;
import it.unipi.lsmd.dto.TripHomeDTO;
import it.unipi.lsmd.service.ServiceLocator;
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
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchFor = request.getAttribute("searchFor").toString();      //search user or Destination
        String value = request.getAttribute("value").toString();              //search value
        if(value == null || searchFor== null || value.isEmpty() || searchFor.isEmpty()){
            response.sendRedirect("/WEB-INF/pages/home.jsp");
        }else{
            if(searchFor.equalsIgnoreCase(String.valueOf(PagesUtilis.SEARCH_TYPE.USER))){
                List<OtherUserDTO> searchedUsers = userService.searchUsers(value,PagesUtilis.OBJECT_PER_PAGE_SEARCH,1);
                request.setAttribute("users_founded",searchedUsers);
                // redirect
            }else{
                // search trip for destination
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
