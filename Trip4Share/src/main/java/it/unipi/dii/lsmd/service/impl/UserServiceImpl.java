package it.unipi.dii.lsmd.service.impl;

import it.unipi.dii.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.dii.lsmd.model.RegisteredUser;
import it.unipi.dii.lsmd.model.User;
import it.unipi.dii.lsmd.repository.UserRepository;
import it.unipi.dii.lsmd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

 //   @Override
  /*  public AuthenticatedUserDTO authenticate(String username, String password){

        User user = userRepository.login(username,password);
        AuthenticatedUserDTO authenticatedUserDTO = new AuthenticatedUserDTO();
        authenticatedUserDTO.setName(user.getName());
        authenticatedUserDTO.setSurname(user.getSurname());
        authenticatedUserDTO.setUsername(user.getUsername());
        authenticatedUserDTO.setEmail(user.getEmail());
        authenticatedUserDTO.setType(user.getType());

        return authenticatedUserDTO;

    }*/
}
