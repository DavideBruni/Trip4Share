package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.dto.RegisteredUserDTO;
import it.unipi.lsmd.service.ServiceLocator;
import it.unipi.lsmd.service.UserService;
import it.unipi.lsmd.utils.SecurityUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private final UserService userService = ServiceLocator.getUserService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/pages/signup.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        processRequest(httpServletRequest, httpServletResponse);
    }

    private void processRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String name = httpServletRequest.getParameter("name");
        String surname = httpServletRequest.getParameter("surname");
        String username = httpServletRequest.getParameter("username");
        String email = httpServletRequest.getParameter("email");
        String password = httpServletRequest.getParameter("psw");
        if(complete(name,surname,username,email,password)) {
            RegisteredUserDTO user = new RegisteredUserDTO();
            user.setFirstName(name);
            user.setLastName(surname);
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setBirthdate(LocalDate.parse(httpServletRequest.getParameter("birthday")));
            String nationality = httpServletRequest.getParameter("nationality");
            user.setNationality(nationality);
            String listOfSpokenLanguages = httpServletRequest.getParameter("languages");
            List<String> spokenLanguages = Arrays.asList(listOfSpokenLanguages.split(","));
            user.setSpokenLanguages(spokenLanguages);
            String result = userService.signup(user);
            if(!result.equals("Something gone wrong") && !result.equals("Duplicate key")){
                //okay, login
                user.setId(result);
                AuthenticatedUserDTO authenticatedUserDTO = DTOwithoutDetails(user);
                HttpSession session = httpServletRequest.getSession(true);
                session.setAttribute(SecurityUtils.AUTHENTICATED_USER_KEY, authenticatedUserDTO);
                session.setAttribute("itsMe", true);
                LoginServlet.redirectUser(httpServletResponse, authenticatedUserDTO);
            }else{
                if(result.equals("Duplicate key")){
                    httpServletRequest.setAttribute(SecurityUtils.SIGNUP_ERROR,"Username already used");
                }else{
                    httpServletRequest.setAttribute(SecurityUtils.SIGNUP_ERROR,result);
                }
                RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher("/WEB-INF/pages/error.jsp");
                requestDispatcher.forward(httpServletRequest, httpServletResponse);
            }
        }else{
            httpServletResponse.sendRedirect("signup");
        }
    }

    private RegisteredUserDTO DTOwithoutDetails(RegisteredUserDTO user) {
        RegisteredUserDTO r = new RegisteredUserDTO();
        r.setFirstName(user.getFirstName());
        r.setLastName(user.getLastName());
        r.setUsername(user.getUsername());
        r.setNationality(user.getNationality());
        r.setSpokenLanguages(user.getSpokenLanguages());
        try {
            r.setBirthdate(user.getBirthdate());
        }catch(Exception e){
            r.setBirthdate(null);
        }
        r.setId(user.getId());
        return r;
    }

    private boolean complete(String name, String surname, String username, String email, String password) {
        return name != null && surname != null && username != null && email != null && password != null;
    }
}
