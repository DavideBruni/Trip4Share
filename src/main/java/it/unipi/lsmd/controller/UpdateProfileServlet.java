package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.AdminDTO;
import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.dto.RegisteredUserDTO;
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
import java.util.Arrays;
import java.util.List;

@WebServlet("/updateProfile")
public class UpdateProfileServlet extends HttpServlet {
    UserService userService = ServiceLocator.getUserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

        AuthenticatedUserDTO user = ((AuthenticatedUserDTO)(req.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY)));

        if(req.getSession()==null || user == null) {
            resp.sendRedirect(req.getContextPath());
            return;
        }

        String targetURL;
        String username = user.getUsername();
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String password = req.getParameter("password");
        if(password.equals("password"))
            password = null;
        String email = req.getParameter("email");

        AuthenticatedUserDTO new_authenticated_user;

        if(user instanceof RegisteredUserDTO){

            String nationality = req.getParameter("nationality");
            LocalDate birthDate = LocalDate.parse(req.getParameter("birthDate"));
            List<ReviewDTO> reviews = ((RegisteredUserDTO)(req.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY))).getReviews();
            List<String> spokenLanguages;
            try{
                spokenLanguages = Arrays.asList(req.getParameter("languages").split(","));
            }catch(NullPointerException ne){
                spokenLanguages = null;
            }

            RegisteredUserDTO new_registered_user = new RegisteredUserDTO();
            new_registered_user.setNationality(nationality);
            new_registered_user.setBirthdate(birthDate);
            new_registered_user.setSpokenLanguages(spokenLanguages);
            new_registered_user.setReviews(reviews);

            new_authenticated_user = new_registered_user;
            targetURL = "user?username="+username;

        }else{
            new_authenticated_user = new AdminDTO();
            targetURL = "admin";
        }

        new_authenticated_user.setUsername(username);
        new_authenticated_user.setFirstName(firstName);
        new_authenticated_user.setLastName(lastName);
        new_authenticated_user.setPassword(password);
        new_authenticated_user.setEmail(email);

        System.out.println(new_authenticated_user);

        if(userService.updateUser(new_authenticated_user, user)){
            req.getSession().setAttribute(SecurityUtils.AUTHENTICATED_USER_KEY,new_authenticated_user);
            System.out.println("Modifica effettuata con successo");
        }else{
            System.out.println("errore nella modifica");
        }

        resp.sendRedirect(targetURL);
        //RequestDispatcher requestDispatcher = req.getRequestDispatcher(targetJSP);
        //requestDispatcher.forward(req, resp);
    }

}
