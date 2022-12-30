package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.dto.InvolvedPeopleDTO;
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

@WebServlet("/joinManager")
public class JoinServlet extends HttpServlet {
    private final TripService tripService = ServiceLocator.getTripService();

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String username = req.getParameter("username");
        String action = req.getParameter("action");
        // only authenticated users can view it and only if parameters aren't null
        if(id==null || username==null || action == null || req.getSession()==null ||
                req.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY) == null) {
            resp.sendRedirect(req.getContextPath());
            return;
        }
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");

        if(tripService.manageTripRequest(id,username,action)) {
            resp.getWriter().write("OK");
            System.out.println("OK");
        }
        else {
            resp.getWriter().write("ERROR");
            System.out.println("Error");
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}
