package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.AdminDTO;
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

@WebServlet("/management/addAdmin")
public class AddAdminServlet extends HttpServlet {

    private UserService userService = ServiceLocator.getUserService();

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        AuthenticatedUserDTO authenticatedUserDTO = SecurityUtils.getAuthenticatedUser(httpServletRequest);

        if(!(authenticatedUserDTO instanceof AdminDTO)){
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath());
            return;
        }

        RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher("/WEB-INF/pages/create_admin.jsp");
        requestDispatcher.forward(httpServletRequest, httpServletResponse);
    }


    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        String username = httpServletRequest.getParameter("username");
        String first_name = httpServletRequest.getParameter("first_name");
        String last_name = httpServletRequest.getParameter("last_name");
        String password = httpServletRequest.getParameter("password");
        String email = httpServletRequest.getParameter("email");


        // TODO - se almeno un campo e' nullo, abort

        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setUsername(username);
        adminDTO.setFirstName(first_name);
        adminDTO.setLastName(last_name);
        adminDTO.setPassword(password);
        adminDTO.setEmail(email);

        // TODO - check se username duplicato

        userService.signup(adminDTO);
        httpServletResponse.sendRedirect("../admin");

    }
}
