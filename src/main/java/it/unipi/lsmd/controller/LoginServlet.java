package it.unipi.lsmd.controller;

import it.unipi.lsmd.dao.neo4j.RegisteredUserNeo4jDAO;
import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.dto.RegisteredUserDTO;
import it.unipi.lsmd.model.DailySchedule;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.Trip;
import it.unipi.lsmd.service.ServiceLocator;
import it.unipi.lsmd.service.TripService;
import it.unipi.lsmd.service.UserService;
import it.unipi.lsmd.service.impl.UserServiceImpl;
import it.unipi.lsmd.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(LoginServlet.class);


    static void redirectUser(HttpServletResponse httpServletResponse, AuthenticatedUserDTO authenticatedUserDTO) throws IOException {
        if(authenticatedUserDTO instanceof RegisteredUserDTO){
            httpServletResponse.sendRedirect("user?username="+authenticatedUserDTO.getUsername());
        }else{
            httpServletResponse.sendRedirect("admin");
        }
    }


    protected void processRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        AuthenticatedUserDTO authenticatedUserDTO = SecurityUtils.getAuthenticatedUser(httpServletRequest);
        UserService userService = ServiceLocator.getUserService();
        String targetJSP = "/WEB-INF/pages/login.jsp";

        // check if user is already authenticated
        if(authenticatedUserDTO != null){
            logger.error("Error. User already authenticated");
            redirectUser(httpServletResponse, authenticatedUserDTO);
            return;
        }


        if(httpServletRequest.getMethod().equals("GET")){

            RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(targetJSP);
            requestDispatcher.forward(httpServletRequest, httpServletResponse);

        }else if(httpServletRequest.getMethod().equals("POST")){
            try {
                String username = httpServletRequest.getParameter("username");
                String password = httpServletRequest.getParameter("password");

                if (username != null && password != null && !username.isEmpty() && !password.isEmpty()){
                    authenticatedUserDTO = userService.authenticate(username, password);

                    // TODO - controllare anche come ha fatto davide
                    if(authenticatedUserDTO == null){
                        httpServletRequest.setAttribute("errorMessage", "Invalid username or password.");

                    }else{
                        // set user as authenticated
                        HttpSession session = httpServletRequest.getSession(true);
                        session.setAttribute(SecurityUtils.AUTHENTICATED_USER_KEY, authenticatedUserDTO);
                        session.setAttribute("itsMe", true);
                        redirectUser(httpServletResponse, authenticatedUserDTO);
                        return;
                    }


                } else {
                    httpServletRequest.setAttribute("errorMessage", "Invalid username or password.");
                }
            } catch (Exception e) {
                logger.error("Error. Invalid username or Password " + e);
                httpServletRequest.setAttribute("errorMessage", "Invalid username or password.");
            }
            RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(targetJSP);
            requestDispatcher.forward(httpServletRequest, httpServletResponse);
        }


    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        logger.info(httpServletRequest.getQueryString());
        processRequest(httpServletRequest, httpServletResponse);
    }


    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        processRequest(httpServletRequest, httpServletResponse);

    }


}
