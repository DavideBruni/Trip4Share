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

@WebServlet("/wishlist")
public class WishlistServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        AuthenticatedUserDTO authenticatedUserDTO = SecurityUtils.getAuthenticatedUser(httpServletRequest);
        if(authenticatedUserDTO == null){
            httpServletResponse.sendRedirect("login");
            return;
        }

        TripService tripService = ServiceLocator.getTripService();
        tripService.getWishlist(authenticatedUserDTO.getUsername());

    }
}
