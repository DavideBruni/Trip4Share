package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.AdminDTO;
import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.utils.SecurityUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin")
public class AdminServlet  extends HttpServlet {

    RequestDispatcher addAdmin(){
        return null;
    }

    RequestDispatcher removeAdmin(){
        return null;
    }

    RequestDispatcher banUser(){
        return null;
    }

    RequestDispatcher searchUser(HttpServletRequest httpServletRequest){
        return httpServletRequest.getRequestDispatcher("/WEB-INF/pages/admin_search.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        AuthenticatedUserDTO authenticatedUserDTO = SecurityUtils.getAuthenticatedUser(httpServletRequest);

        if(!(authenticatedUserDTO instanceof AdminDTO)){
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath());
            return;
        }

        RequestDispatcher requestDispatcher = null;
        String targetJSP = "/WEB-INF/pages/admin.jsp";

        String action = httpServletRequest.getParameter("action");
        if(action != null & action.equals("search")){
            //requestDispatcher = searchUser();
        }



        requestDispatcher = httpServletRequest.getRequestDispatcher(targetJSP);
        requestDispatcher.forward(httpServletRequest, httpServletResponse);
    }
}
