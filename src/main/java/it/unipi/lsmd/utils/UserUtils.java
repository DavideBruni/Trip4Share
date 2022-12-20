package it.unipi.lsmd.utils;

import it.unipi.lsmd.dto.AdminDTO;
import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.dto.RegisteredUserDTO;
import it.unipi.lsmd.model.Admin;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.Review;
import it.unipi.lsmd.model.User;
import org.bson.Document;

import java.util.ArrayList;

public class UserUtils {

    public static User userFromDocument(Document result){

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

            try{
                ArrayList<Document> reviews = result.get("reviews", ArrayList.class);
                for(Document r : reviews){
                    registeredUser.addReview(ReviewUtils.reviewFromDocument(r));
                }
            } catch (Exception e) {
                // TODO - add something?
            }

            user = registeredUser;
        }
        user.setId(result.get("_id").toString());
        user.setName(result.getString("name"));
        user.setSurname(result.getString("surname"));
        user.setEmail(result.getString("email"));

        return user;
    }


    public static AuthenticatedUserDTO userModelToDTO(User user_model){

        if(user_model == null){
            return null;
        }

        AuthenticatedUserDTO authenticatedUserDTO;

        if(user_model instanceof RegisteredUser){
            RegisteredUserDTO registeredUserDTO = new RegisteredUserDTO();
            RegisteredUser registeredUser = (RegisteredUser) user_model;

            registeredUserDTO.setNationality(registeredUser.getNationality());
            registeredUserDTO.setPhone(registeredUser.getPhone());

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

        return authenticatedUserDTO;
    }


}
