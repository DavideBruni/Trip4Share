package it.unipi.lsmd.dao;

import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.User;

public interface UserDAO {

    RegisteredUser register(RegisteredUser user);


    User authenticate(String username, String password);

}
