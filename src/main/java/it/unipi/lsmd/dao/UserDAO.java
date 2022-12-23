package it.unipi.lsmd.dao;

import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.User;

import java.util.List;

public interface UserDAO {

    RegisteredUser register(RegisteredUser user);


    User authenticate(String username, String password);

    RegisteredUser getUser(String username);

    List<RegisteredUser> searchUser(String username, int limit, int page);

    double avgRating(String username);

    boolean createUser(User u);

    boolean deleteUser(User u);
}
