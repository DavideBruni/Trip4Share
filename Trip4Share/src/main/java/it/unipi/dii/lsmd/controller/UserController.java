package it.unipi.dii.lsmd.controller;

import it.unipi.dii.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.dii.lsmd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{username}")
    public AuthenticatedUserDTO viewProfileDetails(@PathVariable String username){
        return userService.getUser(username);
    }



}
