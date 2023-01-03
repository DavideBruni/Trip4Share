package it.unipi.lsmd.controller;

import it.unipi.lsmd.service.ServiceLocator;
import it.unipi.lsmd.service.UserService;
import it.unipi.lsmd.utils.SecurityUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/UpdateReview")
public class UpdateReview extends HttpServlet {

    private final UserService userService = ServiceLocator.getUserService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String to = request.getParameter("to");
       /* if (username == null || title==null || text ==null ||request.getSession() == null || request.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY) == null) {
            response.sendRedirect(request.getContextPath());
            return;
        }*/
        //userService.getReview()
        //setParameter
        //setSession --> Review
        //requestDispatcher
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //getParameters
        //UpdateReview --> Check if there are differences
        //requestDispatcher to (un)success
    }
}
