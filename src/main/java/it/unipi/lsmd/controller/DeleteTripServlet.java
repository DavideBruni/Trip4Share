package it.unipi.lsmd.controller;

import it.unipi.lsmd.service.ServiceLocator;
import it.unipi.lsmd.service.TripService;
import it.unipi.lsmd.utils.SecurityUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteTrip")
public class DeleteTripServlet extends HttpServlet {
    TripService tripService = ServiceLocator.getTripService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String id = req.getParameter("id");
        if(id== null || req.getSession()==null || req.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY) == null) {
            resp.sendRedirect(req.getContextPath());
            return;
        }
        RequestDispatcher requestDispatcher;
        if(tripService.deleteTrip(id)){
            requestDispatcher = req.getRequestDispatcher("/WEB-INF/pages/success.jsp");
        }else{
            requestDispatcher = req.getRequestDispatcher("/trip?id="+id);
        }
        requestDispatcher.forward(req, resp);
    }
}
