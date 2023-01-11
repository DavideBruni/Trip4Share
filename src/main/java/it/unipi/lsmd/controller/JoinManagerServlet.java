package it.unipi.lsmd.controller;

import it.unipi.lsmd.service.ServiceLocator;
import it.unipi.lsmd.service.TripService;
import it.unipi.lsmd.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/joinManager")
public class JoinManagerServlet extends HttpServlet {
    private final TripService tripService = ServiceLocator.getTripService();
    private static final Logger logger = LoggerFactory.getLogger(JoinManagerServlet.class);


    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        String username = req.getParameter("username");
        String action = req.getParameter("action");
        // only authenticated users can view it and only if parameters aren't null
        if(id==null || username==null || action == null || req.getSession()==null ||
                req.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY) == null) {
            logger.error("Error. Access denied");
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
            logger.error("Error while managing trip request");
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(req.getQueryString());
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}
