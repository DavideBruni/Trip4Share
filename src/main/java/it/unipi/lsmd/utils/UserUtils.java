package it.unipi.lsmd.utils;

import it.unipi.lsmd.dto.AdminDTO;
import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.dto.RegisteredUserDTO;
import it.unipi.lsmd.model.Admin;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.User;
import org.bson.Document;

public class UserUtils {

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
            // TODO - add reviews and spoken_languages

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

    public static User userFromDocument(Document result){

        User user;

        if(result.getString("type").equals("admin")){
            Admin admin = new Admin(result.getString("username"));
            user = admin;
        }else{
            RegisteredUser registeredUser = new RegisteredUser(result.getString("username"));
            registeredUser.setNationality(result.getString("nationality"));
            // TODO - add reviews, spoken languages ecc
            user = registeredUser;
        }
        user.setName(result.getString("name"));
        user.setSurname(result.getString("surname"));
        user.setEmail(result.getString("email"));

        return user;
    }

}
