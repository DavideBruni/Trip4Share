package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.dto.InvolvedPeopleDTO;
import it.unipi.lsmd.service.ServiceLocator;
import it.unipi.lsmd.service.TripService;
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

@WebServlet("/requests")
public class RequestsViewServlet extends HttpServlet {

    private final TripService tripService = ServiceLocator.getTripService();
    private static final Logger logger = LoggerFactory.getLogger(RequestsViewServlet.class);


    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        // only authenticated users can view it and only if id isn't null
        if(id==null || req.getSession()==null || req.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY) == null) {
            logger.error("Error. Access denied");
            resp.sendRedirect(req.getContextPath());
            return;
        }
        //get participants and organizer, but if organizer isn't the logged user, return
        InvolvedPeopleDTO inv =tripService.getOrganizerAndJoiners(id);
        try {
            if (inv == null || !inv.getOrganizer().equals(((AuthenticatedUserDTO) req.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY)).getUsername())) {
                logger.error("Error. Access denied");
                resp.sendRedirect(req.getContextPath());
            } else {
                req.setAttribute(SecurityUtils.JOINERS, inv.getJoiners());
                req.setAttribute("id", id);
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/pages/requests.jsp");
                requestDispatcher.forward(req, resp);
            }
        }catch(Exception e){
            logger.error("Error. " + e);
            resp.sendRedirect(req.getContextPath());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(req.getQueryString());
        processRequest(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }
}
