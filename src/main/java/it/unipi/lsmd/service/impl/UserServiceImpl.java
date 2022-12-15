package it.unipi.lsmd.service.impl;

import it.unipi.lsmd.dao.DAOLocator;
import it.unipi.lsmd.dao.RegisteredUserDAO;
import it.unipi.lsmd.dao.UserDAO;
import it.unipi.lsmd.dto.AdminDTO;
import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.dto.RegisteredUserDTO;
import it.unipi.lsmd.dto.SuggestedUserDTO;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.User;
import it.unipi.lsmd.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private RegisteredUserDAO registeredUserDAO;

    public UserServiceImpl(){
        userDAO = DAOLocator.getUserDAO();
        registeredUserDAO = DAOLocator.getRegisteredUserDAO();
    }

    public AuthenticatedUserDTO authenticate(String username, String password){

        User user = userDAO.authenticate(username, password);
        AuthenticatedUserDTO authenticatedUserDTO;

        if(user instanceof RegisteredUser){
            RegisteredUserDTO registeredUserDTO = new RegisteredUserDTO();
            RegisteredUser registeredUser = (RegisteredUser) user;
            registeredUserDTO.setNationality(registeredUser.getNationality());
            registeredUserDTO.setPhone(registeredUser.getPhone());

            authenticatedUserDTO = registeredUserDTO;
        }else{
            AdminDTO adminDTO = new AdminDTO();
            authenticatedUserDTO = adminDTO;
        }

        authenticatedUserDTO.setUsername(user.getUsername());
        authenticatedUserDTO.setFirstName(user.getName());
        authenticatedUserDTO.setLastName(user.getSurname());
        authenticatedUserDTO.setEmail(user.getEmail());

        return authenticatedUserDTO;
    }

    public List<SuggestedUserDTO> getSuggestedUsers(String username){
        List<RegisteredUser> users = registeredUserDAO.getSuggestedUser(username);
        List<SuggestedUserDTO> suggested = new ArrayList<>();
        for(RegisteredUser r : users){
            SuggestedUserDTO suggestedUserDTO = new SuggestedUserDTO();
            suggestedUserDTO.setUsername(r.getUsername());
            suggested.add(suggestedUserDTO);
        }
        return suggested;
    }

}
