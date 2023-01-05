package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.dto.OtherUserDTO;
import it.unipi.lsmd.dto.ReviewDTO;
import it.unipi.lsmd.service.ServiceLocator;
import it.unipi.lsmd.service.UserService;
import it.unipi.lsmd.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/deleteReview")
public class DeleteReviewServlet extends HttpServlet {
    private final UserService userService = ServiceLocator.getUserService();
    private static Logger logger = LoggerFactory.getLogger(DeleteReviewServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(req.getQueryString());
        processRequest(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }


    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String to = request.getParameter("to");
        String date = request.getParameter("date");
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        // only authenticated users can view it and only if parameters aren't null
        if(to==null || date==null || request.getSession()==null ||
                request.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY) == null) {
            response.getWriter().write("Error");
            return;
        }
        ReviewDTO review = new ReviewDTO();
        review.setAuthor(((AuthenticatedUserDTO)request.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY)).getUsername());
        review.setDate(LocalDate.parse(date));
        OtherUserDTO toDTO = new OtherUserDTO();
        toDTO.setUsername(to);
        if(userService.deleteReview(review,toDTO)){
            response.getWriter().write("OK");
        }else{
            response.getWriter().write("Error");
        }
    }
}
