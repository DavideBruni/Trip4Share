package it.unipi.lsmd.service;

import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.dto.OtherUserDTO;

import java.util.List;

public interface UserService {

    AuthenticatedUserDTO authenticate(String username, String password);

    AuthenticatedUserDTO getUser(String user_id);

    List<OtherUserDTO> getSuggestedUsers(String username, int nUsers);

    List<OtherUserDTO> getFollowing(String username);
}
