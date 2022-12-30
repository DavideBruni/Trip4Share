package it.unipi.lsmd.service;

import it.unipi.lsmd.dto.*;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.User;

import java.util.List;

public interface UserService {

    AuthenticatedUserDTO authenticate(String username, String password);

    AuthenticatedUserDTO getUser(String username);

    List<OtherUserDTO> getSuggestedUsers(String username, int nUsers);

    List<OtherUserDTO> getFollowing(String username);

    int getFollowingNumber(String username);

    int getFollowersNumber(String username);

    List<OtherUserDTO> searchUsers(String username, int limit, int page);
    double getRating(String username);


    // change Parameter to DTO
    boolean addUser(AuthenticatedUserDTO u);

    boolean updateUser(RegisteredUserDetailsDTO newUser, RegisteredUserDetailsDTO oldUser);

    List<OtherUserDTO> getFollowers(String username);

    String signup(UserDetailsDTO user);
}
