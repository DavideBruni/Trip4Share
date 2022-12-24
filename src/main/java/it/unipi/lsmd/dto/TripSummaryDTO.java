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
    private RegisteredUserDTO organizer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


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

    public RegisteredUserDTO getOrganizer() {
        return organizer;
    }

    public void setOrganizer(RegisteredUserDTO organizer) {
        this.organizer = organizer;
    }
}
