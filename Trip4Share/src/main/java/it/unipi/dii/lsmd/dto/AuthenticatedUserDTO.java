package it.unipi.dii.lsmd.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticatedUserDTO {
    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;
    private String birthdate;
    private String type;
}
