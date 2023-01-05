package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.dto.OtherUserDTO;
import it.unipi.lsmd.dto.ReviewDTO;
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
import java.time.LocalDate;

@WebServlet("/leaveReview")
public class LeaveReviewServlet extends HttpServlet {
    private final UserService userService = ServiceLocator.getUserService();
    private static Logger logger = LoggerFactory.getLogger(LeaveReviewServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info(request.getQueryString());
        String username = request.getParameter("username");
        if (username==null || request.getSession() == null || request.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY) == null) {
            logger.error("Error. Access denied");
            response.sendRedirect(request.getContextPath());
            return;
        }
        request.getSession().setAttribute(SecurityUtils.REVIEW_TO,username);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/pages/create_review.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String text = request.getParameter("text");
        int rank = 0;
        try{
            rank= Integer.parseInt(request.getParameter("value"));
        }catch (Exception ne){
            logger.error("Error. Invalid rating " + ne);
            response.sendRedirect(request.getContextPath());
            return;
        }

        if (title==null || text ==null ||request.getSession() == null || request.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY) == null) {
            logger.error("Error. Access denied");
            response.sendRedirect(request.getContextPath());
            return;
        }
        String username = (String) request.getSession().getAttribute(SecurityUtils.REVIEW_TO);
        request.getSession().removeAttribute(SecurityUtils.REVIEW_TO);

        RequestDispatcher requestDispatcher = null;

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setAuthor(((AuthenticatedUserDTO)(request.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY))).getUsername());
        reviewDTO.setRating(rank);
        reviewDTO.setText(text);
        reviewDTO.setTitle(title);
        reviewDTO.setDate(LocalDate.now());
        OtherUserDTO to = new OtherUserDTO();
        to.setUsername(username);
        if(userService.setReview(reviewDTO,to)){
            requestDispatcher = request.getRequestDispatcher("/WEB-INF/pages/success.jsp");
        }else{
            requestDispatcher = request.getRequestDispatcher("/WEB-INF/pages/unsuccess.jsp");
        }
        logger.info(reviewDTO.toString());
        requestDispatcher.forward(request, response);
    }
}
