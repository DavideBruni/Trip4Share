package it.unipi.lsmd.dao;

import it.unipi.lsmd.model.RegisteredUser;

import java.util.List;

public interface RegisteredUserDAO {
    List<RegisteredUser> getSuggestedUser(String username, int nUser);
    List<RegisteredUser> getFollowing(String username);
}
