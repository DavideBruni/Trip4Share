package it.unipi.dii.lsmd.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class User {

    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;
    private String type;

}
