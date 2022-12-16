package it.unipi.lsmd.model;


import java.util.ArrayList;
import java.util.List;
public class RegisteredUser extends User{

    private List<Review> reviews;
    private List<String> sponken_languages;
    private String nationality;
    private String phone;

    public RegisteredUser(){
        reviews = new ArrayList<Review>();
        sponken_languages = new ArrayList<String>();
    }

    public RegisteredUser(String username){
        super.setUsername(username);
        reviews = new ArrayList<Review>();
        sponken_languages = new ArrayList<String>();
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

    public List<String> getSponken_languages() {
        return sponken_languages;
    }

    public void setSponken_languages(List<String> sponken_languages) {
        this.sponken_languages = sponken_languages;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
