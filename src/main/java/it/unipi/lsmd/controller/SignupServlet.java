package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.dto.RegisteredUserDTO;
import it.unipi.lsmd.model.User;
import it.unipi.lsmd.service.ServiceLocator;
import it.unipi.lsmd.service.UserService;
import it.unipi.lsmd.utils.SecurityUtils;
import it.unipi.lsmd.utils.UserUtils;
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
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private final UserService userService = ServiceLocator.getUserService();
    private static Logger logger = LoggerFactory.getLogger(SignupServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info(request.getQueryString());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/pages/signup.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        processRequest(httpServletRequest, httpServletResponse);
    }

    private void processRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        RegisteredUserDTO user = UserUtils.registeredUserDTOFromRequest(httpServletRequest);

        if(user == null){
            logger.error("Error. Missing values");
            httpServletRequest.setAttribute(SecurityUtils.SIGNUP_ERROR,"Missing values");
            httpServletResponse.sendRedirect("signup");
            return;
        }

        String result = userService.signup(user);
        if(!result.equals("Something gone wrong") && !result.equals("Duplicate key")){
            //okay, login
            user.setId(result);
            AuthenticatedUserDTO authenticatedUserDTO = UserUtils.DTOwithoutDetails(user);  //TODO - perche' questo? Non posso passargli tutto il RegisteredUserDTO?
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute(SecurityUtils.AUTHENTICATED_USER_KEY, authenticatedUserDTO);
            session.setAttribute("itsMe", true);
            LoginServlet.redirectUser(httpServletResponse, authenticatedUserDTO);
        }else{
            if(result.equals("Duplicate key")){
                logger.error("Error. Username already exists");
                httpServletRequest.setAttribute(SecurityUtils.SIGNUP_ERROR,"Username already used");
            }else{
                logger.error("Error. Something went wrong");
                httpServletRequest.setAttribute(SecurityUtils.SIGNUP_ERROR,result);
            }
            RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher("/WEB-INF/pages/error.jsp");
            requestDispatcher.forward(httpServletRequest, httpServletResponse);
        }
        logger.info("New User: " + user);
    }

}
