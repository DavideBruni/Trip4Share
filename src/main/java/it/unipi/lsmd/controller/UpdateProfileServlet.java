package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.AdminDTO;
import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.dto.RegisteredUserDTO;
import it.unipi.lsmd.dto.ReviewDTO;
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
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@WebServlet("/updateProfile")
public class UpdateProfileServlet extends HttpServlet {
    UserService userService = ServiceLocator.getUserService();
    private static Logger logger = LoggerFactory.getLogger(UpdateProfileServlet.class);


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.info(request.getQueryString());
        if(request.getSession()==null || request.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY) == null) {
            logger.error("Error. Access denied");
            response.sendRedirect(request.getContextPath());
            return;
        }

        RequestDispatcher requestDispatcher;
        AuthenticatedUserDTO authenticatedUserDTO = SecurityUtils.getAuthenticatedUser(request);

        if(authenticatedUserDTO instanceof AdminDTO){
            requestDispatcher = request.getRequestDispatcher("/WEB-INF/pages/modify_admin.jsp");
        }else{
            requestDispatcher = request.getRequestDispatcher("/WEB-INF/pages/modify_info.jsp");
        }
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if(req.getSession()==null){
            logger.error("Error. Access denied");
            resp.sendRedirect(req.getContextPath());
            return;
        }

        AuthenticatedUserDTO user = ((AuthenticatedUserDTO)(req.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY)));

        if(user == null) {
            logger.error("Error. Access denied");
            resp.sendRedirect(req.getContextPath());
            return;
        }

        String targetURL;
        String username = user.getUsername();
        AuthenticatedUserDTO new_authenticated_user;

        if(user instanceof RegisteredUserDTO){
            new_authenticated_user = UserUtils.registeredUserDTOFromRequest(req);
            new_authenticated_user.setUsername(username);
            targetURL = "user?username="+new_authenticated_user.getUsername();

        }else{
            new_authenticated_user = UserUtils.admitDTOFromRequest(req);
            new_authenticated_user.setUsername(username);
            targetURL = "admin";
        }

        if(userService.updateUser(new_authenticated_user, user)){
            req.getSession().setAttribute(SecurityUtils.AUTHENTICATED_USER_KEY,new_authenticated_user);
        }else{
            targetURL = "/WEB-INF/pages/unsuccess.jsp";
        }
        logger.info(new_authenticated_user.toString());
        resp.sendRedirect(targetURL);
    }

}
