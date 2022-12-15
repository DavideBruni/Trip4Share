package it.unipi.lsmd.service.impl;

import it.unipi.lsmd.dao.DAOLocator;
import it.unipi.lsmd.dao.UserDAO;
import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.User;

public class UserServiceImpl {

    private UserDAO userDAO;

    public UserServiceImpl(){
        userDAO = DAOLocator.getUserDAO();
    }

    public AuthenticatedUserDTO authenticate(String username, String password){

        User user = userDAO.authenticate(username, password);
        AuthenticatedUserDTO authenticatedUser = new AuthenticatedUserDTO();
        authenticatedUser.setUsername(user.getUsername());
        authenticatedUser.setFirstName(user.getName());
        authenticatedUser.setLastName(user.getSurname());
        authenticatedUser.setEmail(user.getEmail());

        if(user instanceof RegisteredUser){
            RegisteredUser registeredUser = (RegisteredUser) user;
            authenticatedUser.setNationality(registeredUser.getNationality());
            authenticatedUser.setPhone(registeredUser.getPhone());
        }

        return authenticatedUser;
    }

}
