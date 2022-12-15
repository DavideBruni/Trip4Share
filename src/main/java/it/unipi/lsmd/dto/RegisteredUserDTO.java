package it.unipi.lsmd.dto;

import it.unipi.lsmd.model.RegisteredUser;

import java.util.ArrayList;

public class RegisteredUserDTO extends AuthenticatedUserDTO{

    private String nationality;
    private ArrayList<String> spoken_languages;
    private String phone;

    public RegisteredUserDTO(){
        spoken_languages = new ArrayList<String>();
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public ArrayList<String> getSpoken_languages() {
        return spoken_languages;
    }

    public void setSpoken_languages(ArrayList<String> spoken_languages) {
        this.spoken_languages = spoken_languages;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
