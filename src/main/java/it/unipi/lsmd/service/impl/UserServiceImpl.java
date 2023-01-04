package it.unipi.lsmd.service.impl;

import it.unipi.lsmd.dao.DAOLocator;
import it.unipi.lsmd.dao.RegisteredUserDAO;
import it.unipi.lsmd.dao.UserDAO;
import it.unipi.lsmd.dao.neo4j.exceptions.Neo4jException;
import it.unipi.lsmd.dto.*;
import it.unipi.lsmd.model.Admin;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.Review;
import it.unipi.lsmd.model.User;
import it.unipi.lsmd.service.UserService;
import it.unipi.lsmd.utils.ReviewUtils;
import it.unipi.lsmd.utils.UserUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private RegisteredUserDAO registeredUserDAO;

    public UserServiceImpl(){
        userDAO = DAOLocator.getUserDAO();
        registeredUserDAO = DAOLocator.getRegisteredUserDAO();
    }

    @Override
    public AuthenticatedUserDTO authenticate(String username, String password){
        if(username==null || password==null)
            return null;
        User user = userDAO.authenticate(username, password);
        AuthenticatedUserDTO authenticatedUserDTO;

        if(user instanceof RegisteredUser){
            RegisteredUserDTO registeredUserDTO = (RegisteredUserDTO) UserUtils.userModelToDTO(user);
            registeredUserDTO.setAvg_rating(userDAO.avgRating(username));
            registeredUserDTO.setN_following(registeredUserDAO.getNumberOfFollowing(username));
            registeredUserDTO.setN_followers(registeredUserDAO.getNumberOfFollower(username));
            authenticatedUserDTO = registeredUserDTO;
        }else{
            AdminDTO adminDTO = (AdminDTO) UserUtils.userModelToDTO(user);
            authenticatedUserDTO = adminDTO;
        }

        return authenticatedUserDTO;
    }


    @Override
    public RegisteredUserDTO getUser(String username, String me){
        RegisteredUser user = userDAO.getUser(username);
        if(user == null)
            return null;

        RegisteredUserDTO registeredUser = (RegisteredUserDTO) UserUtils.userModelToDTO(user);
        registeredUser.setN_following(registeredUserDAO.getNumberOfFollowing(username));
        registeredUser.setN_followers(registeredUserDAO.getNumberOfFollower(username));
        registeredUser.setFriend(isFriend(me, username));
        registeredUser.setAvg_rating(userDAO.avgRating(username));

        return registeredUser;
    }

    @Override
    public List<OtherUserDTO> getSuggestedUsers(String username, int nUser){
        List<RegisteredUser> users = registeredUserDAO.getSuggestedUser(username,nUser);
        List<OtherUserDTO> suggested = new ArrayList<>();
        for(RegisteredUser r : users){
            OtherUserDTO otherUserDTO = new OtherUserDTO();
            otherUserDTO.setUsername(r.getUsername());
            suggested.add(otherUserDTO);
        }
        return suggested;
    }

    @Override
    public List<OtherUserDTO> getFollowing(String username, int size, int page) {
        List<RegisteredUser> users = registeredUserDAO.getFollowing(username, size, page);
        List<OtherUserDTO> followers = new ArrayList<>();
        for(RegisteredUser r : users){
            OtherUserDTO otherUserDTO = new OtherUserDTO();
            otherUserDTO.setUsername(r.getUsername());
            followers.add(otherUserDTO);
        }
        return followers;
    }


    @Override
    public List<OtherUserDTO> getFollowers(String username, int size, int page) {
        List<RegisteredUser> users = registeredUserDAO.getFollower(username, size, page);
        List<OtherUserDTO> followers = new ArrayList<>();
        for(RegisteredUser r : users){
            OtherUserDTO otherUserDTO = new OtherUserDTO();
            otherUserDTO.setUsername(r.getUsername());
            followers.add(otherUserDTO);
        }
        return followers;
    }

    @Override
    public int getFollowingNumber(String username) {
        return registeredUserDAO.getNumberOfFollowing(username);
    }

    @Override
    public int getFollowersNumber(String username) {
        return registeredUserDAO.getNumberOfFollower(username);
    }

    @Override
    public String follow(String user_1, String user_2) {
        try{
            registeredUserDAO.follow(user_1, user_2);
            return "OK";
        }catch (Exception e){
            return "Error during follow";
        }
    }

    @Override
    public String unfollow(String user_1, String user_2) {
        try{
            registeredUserDAO.unfollow(user_1, user_2);
            return "OK";
        }catch (Exception e){
            return "Error during unfollow";
        }
    }

    @Override
    public boolean isFriend(String user_1, String user_2) {
        try{
            return registeredUserDAO.isFriend(user_1, user_2);
        }catch (Neo4jException e){
            return false;
        }
    }

    @Override
    public List<OtherUserDTO> searchUsers(String username, int limit, int page) {
        // TO-DO
        List<RegisteredUser> users = userDAO.searchUser(username,limit,page);
        List<OtherUserDTO> followers = new ArrayList<>();
        for(RegisteredUser r : users){
            OtherUserDTO otherUserDTO = new OtherUserDTO();
            otherUserDTO.setUsername(r.getUsername());
            followers.add(otherUserDTO);
        }
        return followers;
    }

    @Override
    public double getRating(String username) {
        return userDAO.avgRating(username);
    }

    @Override
    public List<ReviewDTO> getReviews(String username, int limit, int page) {
        List<Review> reviews_model = userDAO.getReviews(username, limit, page);
        List<ReviewDTO> reviews = new ArrayList<ReviewDTO>();
        for(Review review : reviews_model){
            reviews.add(ReviewUtils.reviewModelToDTO(review));
        }
        return reviews;
    }

    private String addRegisteredUser(RegisteredUserDTO u) {
        if(u.getBirthdate()==null || u.getBirthdate().isAfter(LocalDate.now())){
            return "Your birthdate is probably wrong";
        }
        RegisteredUser r = UserUtils.registeredUserFromDTO(u);
        String flag = userDAO.createUser(r);
        if(!flag.equals("Something gone worng") && !flag.equals("Duplicate key")) {
            try {
                registeredUserDAO.createRegistereduser(r);
                return flag;
            } catch (Neo4jException e) {
                if(!userDAO.deleteUser(r))
                    System.err.println("Mongo Error");
                return "Something gone wrong";
            }
        }
        return flag;
    }

    private String addAdmin(AdminDTO u) {
        Admin a = UserUtils.adminFromDTO(u);
        return userDAO.createUser(a);
    }



    @Override
    public boolean updateUser(AuthenticatedUserDTO newUser, AuthenticatedUserDTO oldUser){
        // fonte: https://www.mongodb.com/community/forums/t/updateone-vs-replaceone-performance/698
        // better update only few attributes instead of the entire document
        try {
            if (newUser instanceof RegisteredUserDTO && oldUser instanceof RegisteredUserDTO) {
                if (newUser.getUsername().equals(oldUser.getUsername())) {
                    if(((RegisteredUserDTO) newUser).getBirthdate()!=null && ((RegisteredUserDTO) newUser).getBirthdate().isBefore(LocalDate.now())) {
                        RegisteredUser r_new = UserUtils.registeredUserFromDTO((RegisteredUserDTO) newUser);
                        RegisteredUser r_old = UserUtils.registeredUserFromDTO((RegisteredUserDTO) oldUser);
                        return userDAO.updateRegisteredUser(r_new, r_old);
                    }
                    return false;
                } else {
                    return false;
                }
            } else if (newUser instanceof AdminDTO && oldUser instanceof AdminDTO) {
                if (newUser.getUsername().equals(oldUser.getUsername())) {
                    return userDAO.updateAdmin(UserUtils.adminFromDTO((AdminDTO) newUser), UserUtils.adminFromDTO((AdminDTO) oldUser));
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public String signup(AuthenticatedUserDTO user){
        if(user instanceof RegisteredUserDTO){
            return addRegisteredUser((RegisteredUserDTO) user);
        }else{
            return addAdmin((AdminDTO) user);
        }
    }

    @Override
    public boolean deleteUser(String username) {
        RegisteredUser user = new RegisteredUser(username);
        // TODO - remove from redis

        if(userDAO.deleteUser(user)){
            try{
                registeredUserDAO.deleteUser(user);
                return true;
            }catch (Neo4jException ne){
                return false;
            }
        }
        return false;

    }

    @Override
    public boolean setReview(ReviewDTO reviewDTO, OtherUserDTO to) {
        Review review = UserUtils.reviewFromDTO(reviewDTO);
        RegisteredUser r = new RegisteredUser(to.getUsername());
        return userDAO.putReview(review,r);
    }

    @Override
    public boolean deleteReview(ReviewDTO review, OtherUserDTO to) {
        Review r = UserUtils.reviewFromDTO(review);
        RegisteredUser registeredUser = new RegisteredUser(to.getUsername());
        return userDAO.deleteReview(r,registeredUser);
    }
}
