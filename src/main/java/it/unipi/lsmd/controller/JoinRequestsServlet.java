package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.service.ServiceLocator;
import it.unipi.lsmd.service.TripService;
import it.unipi.lsmd.utils.SecurityUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/join")
public class JoinRequestsServlet extends HttpServlet {
    TripService tripService = ServiceLocator.getTripService();
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        processRequest(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        processRequest(httpServletRequest, httpServletResponse);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(request.getSession()==null){
            response.sendRedirect(request.getContextPath());
            return;
        }
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        String username = ((AuthenticatedUserDTO)(request.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY))).getUsername();
        String trip_id = request.getParameter("id");
        if(username!=null && trip_id!=null) {
            String action = request.getParameter("action");
            if(action.equals("join")){
                response.getWriter().write(tripService.setJoin(username,trip_id));
            }else if (action.equals("cancel")) {
                response.getWriter().write(tripService.cancelJoin(username,trip_id));
            }else{
                response.getWriter().write("Error");
            }
        }else{
            response.getWriter().write("Error");
        }
    }
}
