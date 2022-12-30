package it.unipi.lsmd.dto;

import java.util.List;

public class RegisteredUserDetailsDTO extends UserDetailsDTO{
    private String nationality;
    private List<String> spokenLanguages;

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<String> getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(List<String> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }
}
