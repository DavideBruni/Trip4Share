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

@WebServlet("/deleteReview")
public class DeleteReview extends HttpServlet {
    private final UserService userService = ServiceLocator.getUserService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        procesRequest(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        procesRequest(req,resp);
    }


    private void procesRequest(HttpServletRequest request, HttpServletResponse response) {
        String to = request.getParameter("to");
        String title = request.getParameter("title");
        String text = request.getParameter("text");
        int rank = 0;
        try{
            rank= Integer.parseInt(request.getParameter("value"));
        }catch (Exception ne){
            //TODO return error
            return;
        }
        // only authenticated users can view it and only if parameters aren't null

    }
}
