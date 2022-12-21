package it.unipi.lsmd.service.impl;

import it.unipi.lsmd.dao.DAOLocator;
import it.unipi.lsmd.dao.RegisteredUserDAO;
import it.unipi.lsmd.dao.UserDAO;
import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.dto.OtherUserDTO;
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
            ((RegisteredUser) user).setFollowing(registeredUserDAO.getFollowing(username));
        }
        return UserUtils.userModelToDTO(user);
    }


    @Override
    public AuthenticatedUserDTO getUser(String user_id){
        RegisteredUser user = userDAO.getUser(user_id);
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
}
