package it.unipi.lsmd.service;

import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.dto.OtherUserDTO;
import it.unipi.lsmd.model.User;

import java.util.List;

public interface UserService {

    AuthenticatedUserDTO authenticate(String username, String password);

    AuthenticatedUserDTO getUser(String username);

    List<OtherUserDTO> getSuggestedUsers(String username, int nUsers);

    List<OtherUserDTO> getFollowing(String username);

    List<OtherUserDTO> searchUsers(String username, int limit, int page);
    double getRating(String username);

    boolean addUser(User u);

}
