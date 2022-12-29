package it.unipi.lsmd.dao;

import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.User;
import it.unipi.lsmd.model.Admin;

import java.util.List;

public interface UserDAO {

    RegisteredUser register(RegisteredUser user);

    User authenticate(String username, String password);

    User getUser(String username);

    List<RegisteredUser> searchUser(String username, int limit, int page);

    double avgRating(String username);

    boolean createUser(User u);

    boolean deleteUser(User u);

    boolean updateRegisteredUser(RegisteredUser new_user, RegisteredUser old_user);

    boolean updateAdmin(Admin new_user, Admin old_user);

    User getUserInformation(String username);
}
