package it.unipi.lsmd.model;


import org.neo4j.driver.Value;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class RegisteredUser extends User{

    private LocalDate birthdate;
    private List<Review> reviews;
    private List<String> spoken_languages;
    private String nationality;
    private String phone;
    private List<RegisteredUser> following; // TODO - da togliere
    private String bio;


    public List<RegisteredUser> getFollowing() {
        return following;
    }

    public void setFollowing(List<RegisteredUser> following) {
        this.following = following;
    }

    public RegisteredUser(){
        reviews = new ArrayList<Review>();
        spoken_languages = new ArrayList<String>();
    }

    public RegisteredUser(String username){
        super.setUsername(username);
        reviews = new ArrayList<>();
        spoken_languages = new ArrayList<>();
    }

    public RegisteredUser(String username, String profile_pic){
        super(username,profile_pic);
        reviews = new ArrayList<>();
        spoken_languages = new ArrayList<>();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public List<Review> getReviews() {
        return reviews;
    }

    public void addReview(Review review){
        reviews.add(review);
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<String> getSpoken_languages() {
        return spoken_languages;
    }

    public void setSpoken_languages(List<String> spoken_languages) {
        this.spoken_languages = spoken_languages;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

}
