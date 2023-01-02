package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.dto.OtherUserDTO;
import it.unipi.lsmd.dto.ReviewDTO;
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
import java.time.LocalDate;

@WebServlet("/leaveReview")
public class LeaveReviewServlet extends HttpServlet {
    private final UserService userService = ServiceLocator.getUserService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        procesRequest(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        procesRequest(req,resp);
    }

    private void procesRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String title = request.getParameter("title");
        String text = request.getParameter("text");
        int rank = 0;
        try{
            rank= Integer.parseInt(request.getParameter("value"));
        }catch (Exception ne){
            response.sendRedirect(request.getContextPath());
            return;
        }

        if (username == null || title==null || text ==null ||request.getSession() == null || request.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY) == null) {
            response.sendRedirect(request.getContextPath());
            return;
        }

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setAuthor(((AuthenticatedUserDTO)(request.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY))).getUsername());
        reviewDTO.setRating(rank);
        reviewDTO.setText(text);
        reviewDTO.setTitle(title);
        reviewDTO.setDate(String.valueOf(LocalDate.now()));
        OtherUserDTO to = new OtherUserDTO();
        to.setUsername(username);
        if(userService.setReview(reviewDTO,to)){
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/pages/success.jsp");
            requestDispatcher.forward(request, response);
        }else{
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/pages/unsuccess.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
