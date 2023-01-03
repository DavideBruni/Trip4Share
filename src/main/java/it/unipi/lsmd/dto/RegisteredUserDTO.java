package it.unipi.lsmd.dto;

import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.Review;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RegisteredUserDTO extends AuthenticatedUserDTO{

    private String nationality;
    private List<String> spokenLanguages;
    private String phone;
    private List<ReviewDTO> reviews;
    private int n_followers;
    private int n_following;
    private LocalDate birthdate;
    private String bio;
    private double avg_rating;
    private boolean isFriend;

    public RegisteredUserDTO(){
        spokenLanguages = new ArrayList<String>();
        reviews = new ArrayList<ReviewDTO>();
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<String> getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(List<String> spoken_languages) {
        this.spokenLanguages = spoken_languages;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void addReview(ReviewDTO review){
        this.reviews.add(review);
    }

    public void setReviews(List<ReviewDTO> reviews){
        this.reviews = reviews;
    }

    public List<ReviewDTO> getReviews(){
        return this.reviews;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public double getAvg_rating() {
        return avg_rating;
    }

    public void setAvg_rating(double avg_rating) {
        this.avg_rating = avg_rating;
    }

    public int getN_followers() {
        return n_followers;
    }

    public void setN_followers(int n_followers) {
        this.n_followers = n_followers;
    }

    public int getN_following() {
        return n_following;
    }

    public void setN_following(int n_following) {
        this.n_following = n_following;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }

    @Override
    public String toString() {
        return "RegisteredUserDTO{" +
                "id='" + getId() + '\'' +
                ", username='" + getUsername() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", nationality='" + nationality + '\'' +
                ", spokenLanguages=" + spokenLanguages +
                ", phone='" + phone + '\'' +
                ", reviews=" + reviews +
                ", n_followers=" + n_followers +
                ", n_following=" + n_following +
                ", birthdate=" + birthdate +
                ", bio='" + bio + '\'' +
                ", avg_rating=" + avg_rating +
                ", isFriend=" + isFriend +
                '}';
    }
}
