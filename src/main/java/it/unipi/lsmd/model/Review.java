package it.unipi.lsmd.model;

import java.time.LocalDate;

public class Review {

    private String text;
    private String title;
    private LocalDate date;
    private int rating;
    private RegisteredUser author;

    public RegisteredUser getAuthor() {
        return author;
    }

    public void setAuthor(RegisteredUser author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Review{" +
                "text='" + text + '\'' +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", rating=" + rating +
                ", author=" + author +
                '}';
    }
}
