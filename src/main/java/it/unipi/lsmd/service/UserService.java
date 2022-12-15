package it.unipi.lsmd.service;

import it.unipi.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.lsmd.dto.RegisteredUserDTO;

public interface UserService {

    public AuthenticatedUserDTO authenticate(String username, String password);

    public AuthenticatedUserDTO getUser(String username);
}
