package it.unipi.lsmd.utils;

import it.unipi.lsmd.dto.*;
import it.unipi.lsmd.model.Admin;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.Review;
import it.unipi.lsmd.model.User;
import org.bson.Document;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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
            registeredUserDTO.setAvg_rating(registeredUser.getAvg_rating());

            try{
                for(Review review : registeredUser.getReviews()){
                    registeredUserDTO.addReview(ReviewUtils.reviewModelToDTO(review));
                }
            }catch (NullPointerException e){}
            /*
            List<OtherUserDTO> follows = new ArrayList<>();
            List<RegisteredUser> followsModel = ((RegisteredUser) user_model).getFollowing();
            for(RegisteredUser r : followsModel){
                OtherUserDTO o = new OtherUserDTO();
                o.setUsername(r.getUsername());
                follows.add(o);
            }
            registeredUserDTO.setFollowing(follows);
            follows.clear();

            followsModel = ((RegisteredUser) user_model).getFollower();
            for(RegisteredUser r : followsModel){
                OtherUserDTO o = new OtherUserDTO();
                o.setUsername(r.getUsername());
                follows.add(o);
            }
            registeredUserDTO.setFollowers(follows);
            */

            authenticatedUserDTO = registeredUserDTO;
        }else{
            AdminDTO adminDTO = new AdminDTO();
            authenticatedUserDTO = adminDTO;
        }

        authenticatedUserDTO.setUsername(user_model.getUsername());
        authenticatedUserDTO.setFirstName(user_model.getName());
        authenticatedUserDTO.setLastName(user_model.getSurname());

        return authenticatedUserDTO;
    }


    static Document documentFromUser(User u) {
        try{
            Document doc = new Document();
            doc.append("username",u.getUsername());
            doc.append("append",u.getName());
            doc.append("surname",u.getSurname());
            doc.append("email",u.getEmail());
            doc.append("password",u.getPassword());
            doc.append("type",u.getRole());

            if(u instanceof RegisteredUser){
                ((RegisteredUser) u).getNationality();
                ((RegisteredUser) u).getSpoken_languages();
                ((RegisteredUser) u).getBirthdate();
                ((RegisteredUser) u).getPhone();
                ((RegisteredUser) u).getBio();
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
            r.setBirthdate(LocalDate.parse(user.getBirthday()));
        }catch(NullPointerException ne){
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
        return a;
    }
}
