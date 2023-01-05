package it.unipi.lsmd.controller;

import it.unipi.lsmd.config.AppServletContextListener;
import it.unipi.lsmd.dto.AdminDTO;
import it.unipi.lsmd.dto.AuthenticatedUserDTO;
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

@WebServlet("/admin")
public class AdminServlet  extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(AdminServlet.class);

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        logger.info(httpServletRequest.getQueryString());

        AuthenticatedUserDTO authenticatedUserDTO = SecurityUtils.getAuthenticatedUser(httpServletRequest);

        if(!(authenticatedUserDTO instanceof AdminDTO)){
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath());
            logger.error("Error. Access denied.");
            return;
        }

        logger.info(authenticatedUserDTO.toString());
        String targetJSP = "/WEB-INF/pages/admin.jsp";

        RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(targetJSP);
        requestDispatcher.forward(httpServletRequest, httpServletResponse);
    }
}
