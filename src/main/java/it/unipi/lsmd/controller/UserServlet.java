package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.service.ServiceLocator;
import it.unipi.lsmd.service.UserService;
import it.unipi.lsmd.service.impl.UserServiceImpl;
import it.unipi.lsmd.utils.SecurityUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {


        AuthenticatedUserDTO authenticatedUserDTO = SecurityUtils.getAuthenticatedUser(httpServletRequest);

        // check if user is authenticated
        if(authenticatedUserDTO == null){
            httpServletResponse.sendRedirect("login");
            return;
        }

        UserService userService = ServiceLocator.getUserService();

        String targetJSP = "/WEB-INF/pages/user.jsp";
        String user_id = httpServletRequest.getParameter("user");
        httpServletRequest.setAttribute("itsMe", true);

        // if it's not user's own profile
        if(user_id != null && !user_id.equals(authenticatedUserDTO.getId())){
            authenticatedUserDTO = userService.getUser(user_id);
            httpServletRequest.setAttribute("itsMe", false);
        }

        // send authenticatedUserDTO to front-end
        httpServletRequest.setAttribute("user", authenticatedUserDTO.getId());

        RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(targetJSP);
        requestDispatcher.forward(httpServletRequest, httpServletResponse);
    }
}
