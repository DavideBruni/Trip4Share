package it.unipi.lsmd.model;


import java.util.ArrayList;
import java.util.List;
public class RegisteredUser extends User{

    private List<Review> reviews;
    private List<String> spoken_languages;
    private String nationality;
    private String phone;
    private List<RegisteredUser> follower;
    private List<RegisteredUser> following;


    public List<RegisteredUser> getFollower() {
        return follower;
    }

    public void setFollower(List<RegisteredUser> follower) {
        this.follower = follower;
    }

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
        reviews = new ArrayList<Review>();
        spoken_languages = new ArrayList<String>();
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
}
