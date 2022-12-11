package it.unipi.dii.lsmd.model;

import it.unipi.dii.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.dii.lsmd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class Service {
    @Autowired
    private UserRepository userRepository;

    public AuthenticatedUserDTO authenticate(String username, String password) {
        User u = userRepository.findFirstByUsernameAndPassword(username, password);
        AuthenticatedUserDTO a = new AuthenticatedUserDTO();
        a.setUsername(u.getUsername());
        return a;
    }
}
