package it.unipi.dii.lsmd.controller;

import it.unipi.dii.lsmd.dto.AuthenticatedUserDTO;
import it.unipi.dii.lsmd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserService ser;

    @GetMapping("/")
    public String viewLogin(){
        return "login";
    }

    @GetMapping("/login")
    public RedirectView login(@RequestParam(value = "username") String username,
                              @RequestParam(value = "password") String password, RedirectAttributes redirectAttributes){

        AuthenticatedUserDTO a = ser.authenticate(username,password);
        final RedirectView redirectView;
        if(a.getUsername().equals("admin"))
            redirectView = new RedirectView("/admin");
        else
            redirectView = new RedirectView("/profile");

        redirectAttributes.addFlashAttribute("user", a);
        return redirectView;
    }
}
