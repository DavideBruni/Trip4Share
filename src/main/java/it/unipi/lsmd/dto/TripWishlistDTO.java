package it.unipi.lsmd.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TripWishlistDTO {

    private String destination;
    private LocalDate departureDate;
    private LocalDate returnDate;
    private String title;
    private LocalDateTime last_modified;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(LocalDateTime last_modified) {
        this.last_modified = last_modified;
    }

    @Override
    public String toString() {
        return "TripWishlistDTO{" +
                "destination='" + destination + '\'' +
                ", departureDate=" + departureDate +
                ", returnDate=" + returnDate +
                ", title='" + title + '\'' +
                ", last_modified=" + last_modified +
                '}';
    }
}
