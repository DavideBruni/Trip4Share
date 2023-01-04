package it.unipi.lsmd.utils;

import it.unipi.lsmd.dto.*;
import it.unipi.lsmd.model.Admin;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.Review;
import it.unipi.lsmd.model.User;
import org.bson.Document;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public interface UserUtils {

    static User userFromDocument(Document result){

        if(result == null){
            return null;
        }

        User user;

        if(result.getString("type").equals("admin")){
            Admin admin = new Admin(result.getString("username"));
            user = admin;
        }else{
            RegisteredUser registeredUser = new RegisteredUser(result.getString("username"));
            registeredUser.setNationality(result.getString("nationality"));
            ArrayList<String> spokenLanguages = result.get("spoken_languages", ArrayList.class);
            registeredUser.setSpoken_languages(spokenLanguages);
            registeredUser.setBirthdate(LocalDateAdapter.convertToLocalDateViaInstant(result.getDate("birthdate")));

            ArrayList<Document> reviews = result.get("reviews", ArrayList.class);
            if(reviews != null){
                for(Document r : reviews)
                    registeredUser.addReview(ReviewUtils.reviewFromDocument(r));
            }

            user = registeredUser;
        }
        user.setName(result.getString("name"));
        user.setSurname(result.getString("surname"));
        user.setEmail(result.getString("email"));
        user.setPassword(result.getString("password"));

        return user;
    }


    static AuthenticatedUserDTO userModelToDTO(User user_model){

        if(user_model == null){
            return null;
        }

        AuthenticatedUserDTO authenticatedUserDTO;

        if(user_model instanceof RegisteredUser){
            RegisteredUserDTO registeredUserDTO = new RegisteredUserDTO();
            RegisteredUser registeredUser = (RegisteredUser) user_model;

            registeredUserDTO.setNationality(registeredUser.getNationality());
            registeredUserDTO.setSpokenLanguages(registeredUser.getSpoken_languages());
            registeredUserDTO.setPhone(registeredUser.getPhone());
            registeredUserDTO.setBirthdate(registeredUser.getBirthdate());

            try{
                for(Review review : registeredUser.getReviews()){
                    registeredUserDTO.addReview(ReviewUtils.reviewModelToDTO(review));
                }
            }catch (NullPointerException e){}

            authenticatedUserDTO = registeredUserDTO;
        }else{
            AdminDTO adminDTO = new AdminDTO();
            authenticatedUserDTO = adminDTO;
        }

        authenticatedUserDTO.setUsername(user_model.getUsername());
        authenticatedUserDTO.setFirstName(user_model.getName());
        authenticatedUserDTO.setLastName(user_model.getSurname());
        authenticatedUserDTO.setEmail(user_model.getEmail());
        authenticatedUserDTO.setPassword(user_model.getPassword());

        return authenticatedUserDTO;
    }


    static Document documentFromUser(User u) {
        try{
            Document doc = new Document();
            doc.append("username",u.getUsername());
            doc.append("name",u.getName());
            doc.append("surname",u.getSurname());
            doc.append("email",u.getEmail());
            doc.append("password",u.getPassword());
            doc.append("type",u.getRole());

            if(u instanceof RegisteredUser){
                doc.append("nationality", ((RegisteredUser) u).getNationality());
                doc.append("spoken_languages", ((RegisteredUser) u).getSpoken_languages());
                doc.append("birthdate", ((RegisteredUser) u).getBirthdate());
            }
            return doc;
        }catch(NullPointerException ne){
            return null;
        }
    }

    static RegisteredUser registeredUserFromDTO(RegisteredUserDTO user) {
        RegisteredUser r = new RegisteredUser();
        r.setName(user.getFirstName());
        r.setSurname(user.getLastName());
        r.setEmail(user.getEmail());
        r.setUsername(user.getUsername());
        r.setPassword(user.getPassword());
        r.setNationality(user.getNationality());
        r.setSpoken_languages(user.getSpokenLanguages());
        try {
            r.setBirthdate(user.getBirthdate());
        }catch(Exception e){
            System.out.println(e);
            r.setBirthdate(null);
        }
        return r;
    }

    static Admin adminFromDTO(AdminDTO u) {
        Admin a = new Admin();
        a.setUsername(u.getUsername());
        a.setName(u.getFirstName());
        a.setSurname(u.getLastName());
        a.setEmail(u.getEmail());
        a.setPassword(u.getPassword());
        a.setRole("admin");
        return a;
    }

    static Review reviewFromDTO(ReviewDTO reviewDTO) {
        Review r = new Review();
        r.setAuthor(reviewDTO.getAuthor());
        r.setText(reviewDTO.getText());
        r.setTitle(reviewDTO.getTitle());
        r.setDate(reviewDTO.getDate());
        r.setRating(reviewDTO.getRating());
        return r;
    }

    static RegisteredUserDTO registeredUserDTOFromRequest(HttpServletRequest httpServletRequest) {
        RegisteredUserDTO user = new RegisteredUserDTO();
        user.setFirstName(httpServletRequest.getParameter("firstName"));
        user.setLastName(httpServletRequest.getParameter("lsatName"));
        user.setUsername(httpServletRequest.getParameter("username"));
        user.setEmail(httpServletRequest.getParameter("email"));
        user.setPassword(httpServletRequest.getParameter("psw"));
        try {
            user.setBirthdate(LocalDate.parse(httpServletRequest.getParameter("birthDate")));
        }catch(Exception e){
            user.setBirthdate(null);
        }
        String nationality = httpServletRequest.getParameter("nationality");
        user.setNationality(nationality);
        String listOfSpokenLanguages = httpServletRequest.getParameter("languages");
        try {
            List<String> spokenLanguages = Arrays.asList(listOfSpokenLanguages.split(","));
            user.setSpokenLanguages(spokenLanguages);
        }catch (Exception e){
            user.setSpokenLanguages(null);
        }
        return user;
    }

    static RegisteredUserDTO DTOwithoutDetails(RegisteredUserDTO user) {
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

    static boolean complete(String name, String surname, String username, String email, String password) {
        return name != null && surname != null && username != null && email != null && password != null;
    }

    static AdminDTO admitDTOFromRequest(HttpServletRequest req) {
        AdminDTO new_authenticated_user = new AdminDTO();
        new_authenticated_user.setFirstName(req.getParameter("firstName"));
        new_authenticated_user.setLastName(req.getParameter("lastName"));
        new_authenticated_user.setPassword(req.getParameter("password"));
        new_authenticated_user.setEmail(req.getParameter("email"));
        return new_authenticated_user;
    }
}
