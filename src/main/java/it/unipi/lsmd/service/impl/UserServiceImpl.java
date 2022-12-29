package it.unipi.lsmd.service.impl;

import it.unipi.lsmd.dao.DAOLocator;
import it.unipi.lsmd.dao.RegisteredUserDAO;
import it.unipi.lsmd.dao.UserDAO;
import it.unipi.lsmd.dao.neo4j.exceptions.Neo4jException;
import it.unipi.lsmd.dto.AdminDTO;
import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.dto.OtherUserDTO;
import it.unipi.lsmd.dto.RegisteredUserDTO;
import it.unipi.lsmd.model.Admin;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.User;
import it.unipi.lsmd.service.UserService;
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
    public List<OtherUserDTO> getFollowing(String username) {
        List<RegisteredUser> users = registeredUserDAO.getFollowing(username);
        List<OtherUserDTO> followers = new ArrayList<>();
        for(RegisteredUser r : users){
            OtherUserDTO otherUserDTO = new OtherUserDTO();
            otherUserDTO.setUsername(r.getUsername());
            followers.add(otherUserDTO);
        }
        return followers;
    }

    @Override
    public List<OtherUserDTO> getFollowers(String username) {
        List<RegisteredUser> users = registeredUserDAO.getFollower(username);
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


    // change Parameter to DTO
    @Override
    public boolean addUser(AuthenticatedUserDTO u) {
        if(u instanceof AdminDTO)
            return addAdmin((AdminDTO)u );
        else
            return addRegisteredUser((RegisteredUserDTO) u);
    }

    private boolean addRegisteredUser(RegisteredUserDTO u) {
        RegisteredUser r = UserUtils.registeredUserFromDTO(u);
        boolean flag = userDAO.createUser(r);
        if(flag) {
            try {
                registeredUserDAO.createRegistereduser(r);
                return true;
            } catch (Neo4jException e) {
                if(!userDAO.deleteUser(r))
                    System.err.println("Mongo Error");
                return false;
            }
        }
        return false;
    }

    private boolean addAdmin(AdminDTO u) {
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
            boolean flag = DAOLocator.getUserDAO().updateRegisteredUser(r_new,r_old);
            if(flag){
                try {
                    // always update imgs, we don't know if they are equal or not
                    DAOLocator.getRegisteredUserDAO().updateRegisteredUser(r_new);
                    return true;
                } catch (Neo4jException e) {
                    //logger errore neo4j
                    return false;
                }
            }
            return false;
        }else
            return false;

    }

}
