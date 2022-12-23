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

    public String getImgUrl() {
        return imgUrl;
    }
}
