package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.dto.TripSummaryDTO;
import it.unipi.lsmd.service.ServiceLocator;
import it.unipi.lsmd.service.TripService;
import it.unipi.lsmd.service.WishlistService;
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
import java.util.ArrayList;

@WebServlet("/wishlist")
public class WishlistServlet extends HttpServlet {

    private final WishlistService wishlistService = ServiceLocator.getWishlistService();
    private static Logger logger = LoggerFactory.getLogger(WishlistServlet.class);


    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        logger.info(httpServletRequest.getQueryString());
        AuthenticatedUserDTO authenticatedUserDTO = SecurityUtils.getAuthenticatedUser(httpServletRequest);
        if(authenticatedUserDTO == null){
            logger.info("Error. Access denied");
            httpServletResponse.sendRedirect("login");
            return;
        }

        int page;
        try{
            page = Integer.parseInt(httpServletRequest.getParameter("page"));
        }catch (Exception e){
            page = 1;
        }

        ArrayList<TripSummaryDTO> wishlist = wishlistService.getWishlist(authenticatedUserDTO.getUsername(), PagesUtilis.TRIPS_PER_PAGE, page);

        String targetJSP = "/WEB-INF/pages/trips_board.jsp";
        httpServletRequest.setAttribute(SecurityUtils.TITLE_PAGE, "Wishlist");
        httpServletRequest.setAttribute(SecurityUtils.PAGE, page);
        httpServletRequest.setAttribute(SecurityUtils.TRIPS_RESULT, wishlist);

        logger.info(wishlist.toString());

        RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(targetJSP);
        requestDispatcher.forward(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO
        super.doPost(req, resp);
    }
}
