package it.unipi.lsmd.dto;

public class ReviewDTO {

    private String text;
    private String title;
    private String date;    //TODO- cast to date in dataset
    private int rating;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
        return "ReviewDTO{" +
                "text='" + text + '\'' +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", rating=" + rating +
                '}';
    }
}
