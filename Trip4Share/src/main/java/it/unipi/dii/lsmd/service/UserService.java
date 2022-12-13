package it.unipi.dii.lsmd.service;

import it.unipi.dii.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.dii.lsmd.model.User;
import it.unipi.dii.lsmd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public AuthenticatedUserDTO authenticate(String username, String password) {
        User u = userRepository.findFirstByUsernameAndPassword(username, password);
        AuthenticatedUserDTO a = new AuthenticatedUserDTO();
        a.setUsername(u.getUsername());
        return a;
    }
}
