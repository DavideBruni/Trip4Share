package it.unipi.lsmd.service;

import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.dto.SuggestedUserDTO;

import java.util.List;

public interface UserService {
    AuthenticatedUserDTO authenticate(String username, String password);
    List<SuggestedUserDTO> getSuggestedUsers(String username);

}
