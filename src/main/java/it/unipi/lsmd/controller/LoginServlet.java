package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.service.impl.UserServiceImpl;

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


    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        System.out.println("GET Operation");

        String targetJSP = "/WEB-INF/pages/login.jsp";

        RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(targetJSP);
        requestDispatcher.forward(httpServletRequest, httpServletResponse);
    }


    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        System.out.println("POST Operation");
        String targetJSP = "WEB-INF/pages/login.jsp";

        String username = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");

        UserServiceImpl userService = new UserServiceImpl();
        AuthenticatedUserDTO authenticatedUserDTO = null;

        try {
            if (username != null && password != null && !username.isEmpty() && !password.isEmpty()){
                authenticatedUserDTO = userService.authenticate(username, password);
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
