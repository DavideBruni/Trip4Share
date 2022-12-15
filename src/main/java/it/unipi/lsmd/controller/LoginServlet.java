package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.service.impl.UserServiceImpl;
import it.unipi.lsmd.utils.SecurityUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        System.out.println("Received: " + httpServletRequest.getMethod());

        AuthenticatedUserDTO authenticatedUserDTO = SecurityUtils.getAuthenticatedUser(httpServletRequest);
        UserServiceImpl userService = new UserServiceImpl();

        // check if user is already authenticated
        if(authenticatedUserDTO != null){
            httpServletResponse.sendRedirect("user");
            return;
        }


        if(httpServletRequest.getMethod().equals("GET")){

            String targetJSP = "/WEB-INF/pages/login.jsp";
            RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(targetJSP);
            requestDispatcher.forward(httpServletRequest, httpServletResponse);

        }else if(httpServletRequest.getMethod().equals("POST")){
            try {
                String username = httpServletRequest.getParameter("username");
                String password = httpServletRequest.getParameter("password");

                if (username != null && password != null && !username.isEmpty() && !password.isEmpty()){
                    authenticatedUserDTO = userService.authenticate(username, password);

                    // set user as authenticated
                    HttpSession session = httpServletRequest.getSession(true);
                    session.setAttribute(SecurityUtils.AUTHENTICATED_USER_KEY, authenticatedUserDTO);

                    // once logged in redirect to user's profile
                    httpServletResponse.sendRedirect("user");

                } else {
                    httpServletRequest.setAttribute("errorMessage", "Invalid username or password.");
                }
            } catch (Exception e) {
                //logger.error("Error during login operation.",e);
                httpServletRequest.setAttribute("errorMessage", "Invalid username or password.");
            }
        }


    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        processRequest(httpServletRequest, httpServletResponse);
    }


    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        processRequest(httpServletRequest, httpServletResponse);

    }


}
