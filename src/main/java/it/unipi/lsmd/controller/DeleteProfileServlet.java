package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.service.ServiceLocator;
import it.unipi.lsmd.service.UserService;
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

@WebServlet("/deleteProfile")
public class DeleteProfileServlet extends HttpServlet {
    UserService userService = ServiceLocator.getUserService();
    private static Logger logger = LoggerFactory.getLogger(DeleteProfileServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(req.getQueryString());
        processRequest(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        if(req.getSession()==null || req.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY) == null) {
            resp.sendRedirect(req.getContextPath());
            return;
        }
        Boolean flag = userService.deleteUser(((AuthenticatedUserDTO)req.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY)).getUsername());
        req.getSession().removeAttribute(SecurityUtils.AUTHENTICATED_USER_KEY);
        req.getSession().invalidate();
        if(flag){
            resp.sendRedirect("index");
        }else{
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/pages/unsuccess.jsp");
            requestDispatcher.forward(req,resp);
        }
    }
}
