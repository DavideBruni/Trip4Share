package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.service.ServiceLocator;
import it.unipi.lsmd.service.UserService;
import it.unipi.lsmd.utils.SecurityUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/updateProfile")
public class UpdateProfileServlet extends HttpServlet {
    UserService userService = ServiceLocator.getUserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession()==null || req.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY) == null) {
            resp.sendRedirect(req.getContextPath());
        }
        String username = ((AuthenticatedUserDTO)(req.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY))).getUsername();
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String nationality = req.getParameter("nationality");
        String birthDate = req.getParameter("birthDate");
        List<String> spokenLanguages;
        try{
            spokenLanguages= Arrays.asList(req.getParameter("languages").split(","));
        }catch(NullPointerException ne){
            spokenLanguages=null;
        }
    }

}
