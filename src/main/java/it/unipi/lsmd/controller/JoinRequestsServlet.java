package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.service.ServiceLocator;
import it.unipi.lsmd.service.TripService;
import it.unipi.lsmd.service.WishlistService;
import it.unipi.lsmd.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/join")
public class JoinRequestsServlet extends HttpServlet {
    private final TripService tripService = ServiceLocator.getTripService();
    private final WishlistService wishlistService = ServiceLocator.getWishlistService();
    private static final Logger logger = LoggerFactory.getLogger(JoinRequestsServlet.class);

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        logger.info(httpServletRequest.getQueryString());
        processRequest(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        processRequest(httpServletRequest, httpServletResponse);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(request.getSession()==null){
            logger.error("Error. Access denied.");
            response.sendRedirect(request.getContextPath());
            return;
        }
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        String username;
        try {
            username = ((AuthenticatedUserDTO) (request.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY))).getUsername();
        }catch(Exception e){
            logger.error("Error. Access denied.");
            response.sendRedirect(request.getContextPath());
            return;
        }
        String trip_id = request.getParameter("id");
        if(username!=null && trip_id!=null) {
            String action = request.getParameter("action");
            if(action != null && action.equals("join")){
                String result = tripService.setJoin(username,trip_id);
                if(!result.equals("Error"))
                    wishlistService.removeFromWishlist(username, trip_id);
                response.getWriter().write(result);
            }else if (action != null && action.equals("cancel")) {
                response.getWriter().write(tripService.cancelJoin(username,trip_id));
            }else{
                logger.error("Error. Invalid action " + action);
                response.getWriter().write("Error");
            }
        }else{
            logger.error("Error. Invalid invalid trip or username");
            response.getWriter().write("Error");
        }
    }
}
