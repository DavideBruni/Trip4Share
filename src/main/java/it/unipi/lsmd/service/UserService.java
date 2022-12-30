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

    List<OtherUserDTO> searchUsers(String username, int limit, int page);
    double getRating(String username);


    boolean updateUser(RegisteredUserDetailsDTO newUser, RegisteredUserDetailsDTO oldUser);


    String signup(UserDetailsDTO user);
}
