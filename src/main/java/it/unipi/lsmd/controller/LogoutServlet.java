package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.utils.SecurityUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        HttpSession session = httpServletRequest.getSession(true);
        if(session.getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY) != null){
            session.setAttribute(SecurityUtils.AUTHENTICATED_USER_KEY, null);
            httpServletResponse.sendRedirect("login");
        }else{
            httpServletResponse.sendRedirect("index");
        }

    }
}
