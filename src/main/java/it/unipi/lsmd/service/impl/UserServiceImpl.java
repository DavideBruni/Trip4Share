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

        User user = userDAO.authenticate(username, password);

        if(user instanceof RegisteredUser){
            ((RegisteredUser) user).setAvg_rating(userDAO.avgRating(username));
            //((RegisteredUser) user).setFollowing(registeredUserDAO.getFollowing(username));
        }

        return UserUtils.userModelToDTO(user);
    }


    @Override
    public AuthenticatedUserDTO getUser(String username){
        RegisteredUser user = userDAO.getUser(username);
        if(user != null)
            user.setAvg_rating(userDAO.avgRating(username));
        return UserUtils.userModelToDTO(user);
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
    public boolean updateUser(RegisteredUserDTO newUser, RegisteredUserDTO oldUser){
        // fonte: https://www.mongodb.com/community/forums/t/updateone-vs-replaceone-performance/698
        // better update only few attributes instead of the entire document
        if(newUser.getUsername()==oldUser.getUsername()){
            RegisteredUser r_new = UserUtils.registeredUserFromDTO(newUser);
            RegisteredUser r_old = UserUtils.registeredUserFromDTO(oldUser);
            return DAOLocator.getUserDAO().updateRegisteredUser(r_new,r_old);

        }else
            return false;

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
    public boolean updateReview(ReviewDTO review, OtherUserDTO toDTO) {
        return false;
    }

    @Override
    public boolean deleteReview(ReviewDTO review, OtherUserDTO to) {
        Review r = UserUtils.reviewFromDTO(review);
        RegisteredUser registeredUser = new RegisteredUser(to.getUsername());
        return userDAO.deleteReview(r,registeredUser);
    }
}
