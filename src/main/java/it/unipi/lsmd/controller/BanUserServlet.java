package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.AdminDTO;
import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.service.ServiceLocator;
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

@WebServlet("/banUser")
public class BanUserServlet extends HttpServlet {

    private UserService userService = ServiceLocator.getUserService();
    private static Logger logger = LoggerFactory.getLogger(BanUserServlet.class);

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        logger.info(httpServletRequest.getQueryString());
        AuthenticatedUserDTO authenticatedUserDTO = SecurityUtils.getAuthenticatedUser(httpServletRequest);

        if(!(authenticatedUserDTO instanceof AdminDTO)){
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath());
            logger.error("Error. Access denied.");
            return;
        }

        RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher("/WEB-INF/pages/ban_user.jsp");
        requestDispatcher.forward(httpServletRequest, httpServletResponse);

    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        String username = httpServletRequest.getParameter("username");
        String targetURL = "admin";
        if(username == null || username.equals("")){
            httpServletRequest.setAttribute(SecurityUtils.ERROR_MESSAGE, "Invalid Username!"); // TODO sistemarlo bene nel jsp
            targetURL = "banUser";
        }else {
            userService.deleteUser(username);
        }
        httpServletResponse.sendRedirect(targetURL);
    }
}
