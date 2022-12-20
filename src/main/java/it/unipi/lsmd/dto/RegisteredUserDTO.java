package it.unipi.lsmd.dto;

import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.Review;

import java.util.ArrayList;
import java.util.List;

public class RegisteredUserDTO extends AuthenticatedUserDTO{

    private String nationality;
    private ArrayList<String> spoken_languages;
    private String phone;
    private ArrayList<ReviewDTO> reviews;
    private List<OtherUserDTO> following;


    public RegisteredUserDTO(){
        spoken_languages = new ArrayList<String>();
        reviews = new ArrayList<ReviewDTO>();
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

}
