package it.unipi.lsmd.service;

import it.unipi.lsmd.dao.neo4j.exceptions.Neo4jException;
import it.unipi.lsmd.dto.*;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.User;

import java.util.List;

public interface UserService {

    AuthenticatedUserDTO authenticate(String username, String password);

    AuthenticatedUserDTO getUser(String username);

    List<OtherUserDTO> getSuggestedUsers(String username, int nUsers);

    List<OtherUserDTO> getFollowers(String username, int size, int page);
    List<OtherUserDTO> getFollowing(String username, int size, int page);

    int getFollowingNumber(String username);

    int getFollowersNumber(String username);

    void follow(String user_1, String user_2);

    void unfollow(String user_1, String user_2);

    boolean isFriend(String user_1, String user_2);

    List<OtherUserDTO> searchUsers(String username, int limit, int page);
    double getRating(String username);

    boolean updateUser(RegisteredUserDTO newUser, RegisteredUserDTO oldUser);

    String signup(AuthenticatedUserDTO user);
}
