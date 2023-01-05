package it.unipi.lsmd.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class RegisteredUser extends User{

    private LocalDate birthdate;
    private List<Review> reviews;
    private List<String> spoken_languages;
    private String nationality;
    private List<RegisteredUser> following;
    private List<RegisteredUser> followers;
    private List<Trip> past_trips;
    private List<Trip> organized_trips;
    private Wishlist wishlist;

    public RegisteredUser(){
        reviews = new ArrayList<>();
        spoken_languages = new ArrayList<>();
    }

    public RegisteredUser(String username){
        super.setUsername(username);
        reviews = new ArrayList<>();
        spoken_languages = new ArrayList<>();
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

    public List<RegisteredUser> getFollowing() {
        return following;
    }

    public void setFollowing(List<RegisteredUser> following) {
        this.following = following;
    }

    public List<RegisteredUser> getFollowers() {
        return followers;
    }

    public void setFollowers(List<RegisteredUser> followers) {
        this.followers = followers;
    }

    public List<Trip> getPast_trips() {
        return past_trips;
    }

    public void setPast_trips(List<Trip> past_trips) {
        this.past_trips = past_trips;
    }

    public Wishlist getWishlist() {
        return wishlist;
    }

    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }

    public List<Trip> getOrganized_trips() {
        return organized_trips;
    }

    public void setOrganized_trips(List<Trip> organized_trips) {
        this.organized_trips = organized_trips;
    }
}
