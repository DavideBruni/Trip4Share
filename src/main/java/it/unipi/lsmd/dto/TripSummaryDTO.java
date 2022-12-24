package it.unipi.lsmd.dto;

import java.time.LocalDate;

public class TripSummaryDTO {

    private String id;
    private String destination;
    private LocalDate departureDate;
    private LocalDate returnDate;
    private boolean deleted;
    private String Title;
    private String imgUrl;
    private int like_counter;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String organizer_username;

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public String getTitle() {
        return Title;
    }


    public int getLike_counter() {
        return like_counter;
    }

    public void setLike_counter(int like_counter) {
        this.like_counter = like_counter;
    }

    @Override
    public String toString() {
        return "TripSummaryDTO{" +
                "id='" + id + '\'' +
                ", destination='" + destination + '\'' +
                ", departureDate=" + departureDate +
                ", returnDate=" + returnDate +
                ", deleted=" + deleted +
                ", Title='" + Title + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", like_counter=" + like_counter +
                ", organizer_username='" + organizer_username + '\'' +
                '}';
    }
}
