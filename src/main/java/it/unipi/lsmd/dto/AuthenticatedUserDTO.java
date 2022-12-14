package it.unipi.lsmd.dto;

import java.util.ArrayList;
import java.util.Date;

public class AuthenticatedUserDTO {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    //private Date brithdate;
    private String nationality;
    //private ArrayList<String> spoken_languages;
    private String phone;

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
