package it.unipi.lsmd.dto;

import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.Review;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RegisteredUserDTO extends AuthenticatedUserDTO{

    private String nationality;
    private List<String> spoken_languages;
    private String phone;
    private ArrayList<ReviewDTO> reviews;
    private List<OtherUserDTO> following;
    private LocalDate birthdate;
    private String bio;
    private double avg_rating;

    public RegisteredUserDTO(){
        spoken_languages = new ArrayList<String>();
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

    public List<String> getSpoken_languages() {
        return spoken_languages;
    }

    public void setSpoken_languages(List<String> spoken_languages) {
        this.spoken_languages = spoken_languages;
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

    public void setReviews(ArrayList<ReviewDTO> reviews){
        this.reviews = reviews;
    }

    public ArrayList<ReviewDTO> getReviews(){
        return this.reviews;
    }

    public List<OtherUserDTO> getFollowing() {
        return following;
    }

    public void setFollowing(List<OtherUserDTO> following) {
        this.following = following;
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
}
